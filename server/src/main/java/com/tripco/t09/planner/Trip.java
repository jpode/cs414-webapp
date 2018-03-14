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

    verifyPlaces();
    if(places.size() > 1) {
      this.map = svg();
      this.distances = legDistances();
    }

  }

  /**
   * Optimize can be called directly through changing slider on UI, or indirectly through planTrip()
   * above. optimize() is the entry function for nearest neighbor, 2opt, and 3opt optimizations
   * methods, all defined in Trip.java.
   * NOTE: IF ADDITIONAL OPTIMIZATIONS ARE ADDED OR REMOVED, THIS METHOD WILL HAVE TO BE ALTERED TO
   * REFLECT DIFFERENT VALUES ON UI SLIDER (CURRENTLY 3 OPTIONS, MAX VALUE = 1, SO .333 IS VALUE BY
   * WHICH SLIDER STEPS CURRENTLY. MAY NOT ALWAYS BE THE CASE).
   */

  public void optimize() {
    System.out.println("Optimizing trip with level " + this.options.optimization);
    if (this.options.optimization == 0) {
      this.plan();
    } else if (this.options.optimization < 0.35) {
      this.planNearestNeighbor();
    } else if (this.options.optimization < 0.7) {
      this.plan2Opt();
    } else {
      this.plan3Opt();
    }

  }

  /**
   * Top level method that does planning for nearest neighbor optimization. Currently will override
   * any previously calculated SVG map or distances array.
   */

  public void planNearestNeighbor() {

  }

  /**
   * Top level method that does planning for 2-Opt optimization. Currently will override any
   * previously calculated SVG map or distances array.
   */

  public void plan2Opt() {

  }

  /**
   * Top level method that does planning for 3-Opt optimization. Currently will override any
   * previously calculated SVG map or distances array.
   */

  public void plan3Opt() {

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
      String path = "\n<svg width=\"1066.6073\" height=\"783.0824\" xmlns=\"http://www.w3.org/2000/svg\">\n<g>\n";
      path += "<polyline points=\"";
      String points = "";
      double start_A = 0;
      double start_B = 0;
      for(int i = 0; i < places.size(); i++) {
        String newPoint = "";
        double A = convertLatSVG(places.get(i).latitude);
        double B = convertLongSVG(places.get(i).longitude);
        if (i == 0){
          start_A = A;
          start_B = B;
        }
        if (i == places.size()-1) {
          path += B + "," + A + " ";
          //round trip implementation:
          start_A = convertLatSVG(places.get(0).latitude);
          start_B = convertLongSVG(places.get(0).longitude);
          path += start_B + "," + start_A;
          newPoint += "<circle cx=\"" + B + "\" cy=\"" + A + "\" r=\"5\" stroke=\"black\" stroke-width=\"3\" fill=\"blue\" />";
        }else {
          path += B + "," + A + " ";
          newPoint += "<circle cx=\"" + B + "\" cy=\"" + A + "\" ";
          if(i == 0)
            newPoint += "r=\"8\" stroke=\"black\" stroke-width=\"3\" fill=\"red\" />";
          else
            newPoint += "r=\"5\" stroke=\"black\" stroke-width=\"3\" fill=\"blue\" />";
        }
        points += newPoint + "\n";
      }
      path += "\" fill=\"none\" stroke-width=\"3\" stroke=\"black\" />\n";
      path += points + "</g>\n</svg>\n";


      while ( (line = br.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append("\n");
      }


      stringBuffer.insert(stringBuffer.length()-8, path);
      //stringBuffer.append(points);
      //System.out.println(stringBuffer.toString());
      is.close();
      //System.out.println(stringBuffer.toString());

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

    for(i = 0; i < places.size(); i++){

      ptA_LAT = convertCoordinate(places.get(i).latitude);
      ptA_LONG = convertCoordinate(places.get(i).longitude);

      if(i != places.size() - 1) {
        ptB_LAT = convertCoordinate(places.get(i + 1).latitude);
        ptB_LONG = convertCoordinate(places.get(i + 1).longitude);
      } else {
        ptB_LAT = convertCoordinate(places.get(0).latitude);
        ptB_LONG = convertCoordinate(places.get(0).longitude);
      }

      singleDist = distanceHelper(ptA_LAT, ptA_LONG, ptB_LAT, ptB_LONG);
      dist.add(singleDist);

    }
    return dist;

  }

  private void verifyPlaces(){
    for(int i = 0; i < places.size(); i++){
      if(!verifyLatitudeCoordinates(convertCoordinate(places.get(i).latitude)) || !verifyLongitudeCoordinates(convertCoordinate(places.get(i).longitude))){
        System.out.println("Coordinates for location " + places.get(i).name + " are outside of Colorado boundaries");
        places.remove(i);
        i--;
      }
    }
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
   * Takes a single decimal longitudinal coordinate and checks to see if it is within the 102.05W and 109.05W boundaries
   *  of the Colorado eastern and western border
   * @params Double containing the coordinate
   * @return boolean indicating if the coordinate is within the boundaries.
   *  true = within boundaries, false = outside of boundaries
   */
  public boolean verifyLongitudeCoordinates(double coordinate){
    return coordinate < -102.05 && coordinate > -109.05;
  }

  // Same as verifyLongitudeCoordinates, but takes a latitudinal coordinate and tests
  //  it against the south and north Colorado borders at 37N and 41N
  public boolean verifyLatitudeCoordinates(double coordinate){
    return coordinate > 37 && coordinate < 41;
  }

  // Converting our Latitude to SVG values for Polyline on Map
  public double convertLatSVG(String value) {
    double result;
    double x = convertCoordinate(value);
    //System.out.println("     LAT IS: " + x);


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
