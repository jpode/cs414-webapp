package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t09.server.HTTP;
import spark.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.Math;

/**
 * The Trip class supports TFFI so it can easily be converted to/from Json by Gson.
 *
 */
public class Trip {
  // The variables in this class should reflect TFFI.
  public String type;
  public String title;
  public Option options;
  public ArrayList<Place> places;
  public ArrayList<Integer> distances;
  public String map;

  /** The top level method that does planning.
   * At this point it just adds the map and distances for the places in order.
   * It might need to reorder the places in the future.
   */
  public void plan() {

    this.map = svg();
    this.distances = legDistances();

  }

  /**
   * Returns an SVG containing the background and the legs of the trip.
   * @return
   */
  private String svg() {

    InputStream is = getClass().getResourceAsStream("/colorado.svg");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    StringBuffer stringBuffer = new StringBuffer();
    String line;
    try {
    // calculates and fomats the coordinates of the leg of the trip
    String leg = "";
    for(int i=0;i<=places.size();++i) {
      double A= convertCoordinate(places.get(i).latitude);
      double B = convertCoordinate(places.get(i).longitude);
      leg = A +"," + B + " ";
    }
      while ( (line = br.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append("\n");
      }
      is.close();
      return stringBuffer.toString();
    } catch (IOException ioe) {
      System.out.println("ERROR: colorado.svg failed to be read by BufferedReader in Trip.java.");
      return null;
    }

  }

  /**
   * Returns the distances between consecutive places,
   * including the return to the starting point to make a
   * round trip.This method may remove locations if their
   * coordinates do not match the Colorado boundaries. If
   * all the locations in the places array are removed, the
   * return array will have no values.
   * @return
   */
  private ArrayList<Integer> legDistances() {

    ArrayList<Integer> dist = new ArrayList<Integer>();
    int singleDist = 0;
    int i;
    double ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG;
    boolean removed_ptA = false;
    boolean removed_ptB = false;

    for(i = 0; i < places.size() - 1; i++){
     //If any places have been removed for incorrect coordinates, the index will need to be adjusted as the array will shrink
      if(removed_ptA){
        i--;
      }
      if(removed_ptB){
        i--;
      }
      removed_ptA = false;
      removed_ptB = false;

      ptA_LAT = convertCoordinate(places.get(i).latitude);
      ptA_LONG = convertCoordinate(places.get(i).longitude);
      ptB_LAT = convertCoordinate(places.get(i+1).latitude);
      ptB_LONG = convertCoordinate(places.get(i+1).longitude);

      /*
       * Verify if two current places are located inside Colorado
       * If they are not, then remove the place from the places array.
       */

      if(!verifyLatitudeCoordinates(ptA_LAT) || !verifyLongitudeCoordinates(ptA_LONG)) {
        System.out.println("Coordinates for location " + places.get(i).name + " are outside of Colorado boundaries");
        places.remove(i);
        removed_ptA = true;
      }

      if(!verifyLatitudeCoordinates(ptB_LAT) || !verifyLongitudeCoordinates(ptB_LONG)) {
        //If place ptA has been removed, then the array shrinks so that ptB is now where ptA was
        if(removed_ptA){
          System.out.println("Coordinates for location " + places.get(i).name + " are outside of Colorado boundaries");
          places.remove(i);
        } else {
          System.out.println("Coordinates for location " + places.get(i+1).name + " are outside of Colorado boundaries");
          places.remove(i + 1);
        }
        removed_ptB = true;
      }

      //Add distance to dist array
      if(!removed_ptA && !removed_ptB) {
        singleDist = distanceHelper(ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG);
        dist.add(singleDist);
      }
    }

    /*
     * Calculate a distance for the return; from the last location back to the first
     * If places have been removed and there is one or zero places remaining, there
     * will be no values;
     */
    if(places.size() > 1) {
      ptA_LAT = convertCoordinate(places.get(i).latitude);
      ptA_LONG = convertCoordinate(places.get(i).longitude);
      ptB_LAT = convertCoordinate(places.get(0).latitude);
      ptB_LONG = convertCoordinate(places.get(0).longitude);

      singleDist = distanceHelper(ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG);
      dist.add(singleDist);
    } else {
      dist.clear();
    }
    return dist;
  }

  //follows chord length formula given here:
  // https://en.wikipedia.org/wiki/Great-circle_distance#From_chord_length
  public int distanceHelper(double ptA_LAT, double ptA_LONG, double ptB_LAT, double ptB_LONG) {
    //converting to radians:
    ptA_LAT = Math.toRadians(ptA_LAT);
    ptA_LONG = Math.toRadians(ptA_LONG);
    ptB_LAT = Math.toRadians(ptB_LAT);
    ptB_LONG = Math.toRadians(ptB_LONG);

    double x = (Math.cos(ptB_LAT) * Math.cos(ptB_LONG)) - (Math.cos(ptA_LAT) * Math.cos(ptA_LONG));
    double y = (Math.cos(ptB_LAT) * Math.sin(ptB_LONG)) - (Math.cos(ptA_LAT) * Math.sin(ptA_LONG));
    double z = Math.sin(ptB_LAT) - Math.sin(ptA_LAT);
    double c = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    double central_angle = 2 * Math.asin(c / 2);

    try {
      if (this.options.distance.compareTo("miles") == 0) {
        return (int)Math.round(3959 * central_angle);
      } else {
        return (int)Math.round(6371 * central_angle);
      }
    } catch(NullPointerException e){
      return (int)Math.round(3959 * central_angle);
    }
  }

  /**
   * Takes a single string coordinate of various types, if the coordinate is already in
   *  proper decimal format it will simply return. Otherwise the method will call
   *   stringToCoordinate to convert the coordinate.
   * @params String containing the coordinate
   * @return double containing the string coordinate converted to decimal coordinate
   */
  public double convertCoordinate(String value) {
    double result;
    try {
      result = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      /*Coordinate is not already in number format*/
      result = stringToCoordinate(value);
    }

    return result;

  }

  /**
   * Takes a single string coordinate of various types and converts it to a decimal value.
   *  Assumes that the string is of a correct coordinate input type.
   * @params String containing the coordinate
   * @return double containing the string coordinate converted to decimal coordinate
   */
  public double stringToCoordinate(String value){
    String coordinate;
    double degrees = 0;
    double minutes = 0;
    double seconds = 0;
    double result;


    //Replaces all degrees, minutes, and seconds symbols with empty spaces to allow the
    // string to be parsed into double values.

    coordinate = value.replaceAll("[°\'\"′″]", " " );

    int counter = 0;
    int last = 0;

    // Parses the new string and reads in the values of the coordinates
    for(int i = 0; i < coordinate.length(); i++){
      if(coordinate.charAt(i) == ' ' && last != i){
        if(counter == 0) {
          degrees = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        } else if(counter == 1){
          minutes = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        } else if(counter == 2){
          seconds = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        }
      }
    }

    // Checks to see if the value should be negated
    if(coordinate.indexOf('S') != -1 || coordinate.indexOf('W') != -1){
      return -1 * (double)Math.round((degrees + (minutes / 60) + (seconds / 3600)) * 100000d) / 100000d;
    }

    return (double)Math.round((degrees + (minutes / 60) + (seconds / 3600)) * 100000d) / 100000d;
  }

  /**
   * Takes a single decimal coordinate and checks to see if it is within the 102.05W and 109.05W boundaries
   *  of the Colorado eastern and western border
   * @params Double containing the coordinate
   * @return boolean indicating if the coordinate is within the boundaries.
   *  true = within boundaries, false = outside of boundaries
   */
  public boolean verifyLatitudeCoordinates(double coordinate){
    if(coordinate < -102.05 && coordinate > -109.05){
      return true;
    }
    return false;
  }

  // Same as verifyLatitudeCoordinates, but takes longitudinal coordinates and tests
  //  them against the south and north Colorado borders at 37N and 41N
  public boolean verifyLongitudeCoordinates(double coordinate){
    if(coordinate > 37 && coordinate < 41){
      return true;
    }
    return false;
  }

}
