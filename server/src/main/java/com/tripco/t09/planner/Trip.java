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
import java.util.Arrays;


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
    // calculates and formats the coordinates of the leg of the trip (Polyline)
    String points = "\n<svg width=\"1066.6073\" height=\"783.0824\" xmlns=\"http://www.w3.org/2000/svg\">\n<g>\n";
    points += "<polyline points=\"";
    double start_A = 0;
    double start_B = 0;
    for(int i = 0; i < places.size(); i++) {
      double A = convertLatSVG(places.get(i).latitude);
      double B = convertLongSVG(places.get(i).longitude);
      if (i == 0){
          start_A = A;
          start_B = B;
      }
      if (i == places.size()-1) {
          points += B + "," + A + " ";
          //round trip implementation:
          start_A = convertLatSVG(places.get(0).latitude);
          start_B = convertLongSVG(places.get(0).longitude);
          points += start_B + "," + start_A;
      }else {
          points += B + "," + A + " ";
      }
    }
    points += "\" fill=\"none\" stroke-width=\"3\" stroke=\"black\" />\n</g>\n</svg>\n";


      while ( (line = br.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append("\n");
      }

      stringBuffer.insert(stringBuffer.length()-8, points);
      //stringBuffer.append(points);

      is.close();
      System.out.println(stringBuffer.toString());

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

    if(places.size() > 0) {
      for (i = 0; i < places.size() - 1; i++) {

        ptA_LAT = convertCoordinate(places.get(i).latitude);
        ptA_LONG = convertCoordinate(places.get(i).longitude);
        ptB_LAT = convertCoordinate(places.get(i + 1).latitude);
        ptB_LONG = convertCoordinate(places.get(i + 1).longitude);

        //Add distance to dist array
        singleDist = distanceHelper(ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG);
        dist.add(singleDist);
      }

      //Add the return leg distance
      ptA_LAT = convertCoordinate(places.get(i).latitude);
      ptA_LONG = convertCoordinate(places.get(i).longitude);
      ptB_LAT = convertCoordinate(places.get(0).latitude);
      ptB_LONG = convertCoordinate(places.get(0).longitude);


      singleDist = distanceHelper(ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG);
      dist.add(singleDist);
    }
    return dist;

    //BELOW: implementation with coordinate error checking. To be used in Sprint 3
    /*
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
    /*

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

    /*
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
    */
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
   * Takes a single string coordinate of various types and converts it to a decimal value.
   * @params String containing the coordinate
   * @return double containing the string coordinate converted to decimal coordinate
   */
  public double convertCoordinate(String value){
    ArrayList<String> coordinate;
    double degrees = 0;
    double minutes = 0;
    double seconds = 0;
    int isNegative = 1;

    //Replaces given string coordinate values into an arraylist

    coordinate = new ArrayList<String>(Arrays.asList(value.split("[°\'\"′″\\s+]")));

    if(coordinate.contains("S") || coordinate.contains("W")) {
      isNegative = -1;
    }

    coordinate.removeAll(Arrays.asList("N", "S", "E", "W", "", " ", null));

    if(coordinate.size() > 0){
      degrees = Double.parseDouble(coordinate.get(0));
    }
    if(coordinate.size() > 1){
      minutes = Double.parseDouble(coordinate.get(1));
    }
    if(coordinate.size() > 2){
      seconds = Double.parseDouble(coordinate.get(2));
    }

    return isNegative * (double)Math.round((degrees + (minutes / 60) + (seconds / 3600)) * 100000d) / 100000d;

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

  // Converting our Latitude to SVG values for Polyline on Map
  public double convertLatSVG(String value) {
      double result;
      double x = convertCoordinate(value);
      System.out.println("     LAT IS: " + x);


      // Latitude Formula = 747 - (178 * (value - 37))
      result = 747 - (178 * (x - 37));

      return result;
  }

  // Converting our Longitude to SVG values for Polyline on Map
  public double convertLongSVG(String value) {
      double result;
      double y = convertCoordinate(value);

      // Longitude Formula = 1029 + (142 * (value + 102.05))
      result = 1029 + (142 * (y + 102.05));

      return result;
  }

}
