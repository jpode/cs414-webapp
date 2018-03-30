package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t09.server.HTTP;
import java.util.LinkedList;
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
  public int version;
  public String type;
  public String title;
  public Option options;
  public ArrayList<Place> places;
  public ArrayList<Integer> distances;
  public String map;

  //public int[][] distArr;
  // notes for memoization: how should indexes be handled? We could do it by index in places,
  // but we would have to account for optimizations changing order of places (which is when we
  // would utilize it)... Could try to implement it by latitude / longitude, but then it would
  // likely have to be a string to prevent false matches, which would create problems of its own...

  /** The top level method that does planning.
   * At this point it just adds the map and distances for the places in order.
   * It might need to reorder the places in the future.
   */
  public void plan() {
    verifyPlaces();
    try {
      if (places.size() > 1) {
        this.map = svg();
        System.out.println("calculating distances...");
        this.distances = legDistances();
      }
    } catch (NullPointerException e) {
      return;
    }

  }

  /**
   * Optimize can be called directly through changing slider on UI, or indirectly through planTrip()
   * above. optimize() is the entry function for nearest neighbor, 2opt, and 3opt optimizations
   * methods, all defined in Trip.java.
   */

  public void optimize() {

    this.plan();
    Optimization opt = new Optimization(this);

    System.out.println("Optimizing trip with level " + this.options.optimization);

    verifyPlaces();

    Double optLevel;
    try {      // in case this.options.optimization is "none" (version 1)
      optLevel = Double.parseDouble(this.options.optimization);
    } catch (NumberFormatException e) {
      this.plan();
      return;
    }  // if "none", just plan trip
    double optPartition = 1.0 / 2 + .01;
    if (optLevel < (optPartition) && optLevel != 0) {
      this.places = opt.planNearestNeighbor();
      System.out.println("NN Optimized Round Trip Distance: " + sumDistances(places));
    } else if (optLevel < (2 * optPartition)) {
      this.places = opt.plan2Opt();
        System.out.println("2OPT Optimized Round Trip Distance: " + sumDistances(places));
    } else if (optLevel < (3 * optPartition)) {
      opt.plan3Opt();
    }
    this.plan();
  }



  /**
   * This method calculates the sum of distances between consecutive points in an ArrayList
   * containing type Place objects (Round-Trip Distance).
   *
   * @return totalDist
   */

  public int sumDistances(ArrayList<Place> newPlaces) {
    int totalDist = 0;
    Place aa;
    Place bb;
    for (int i = 0; i < newPlaces.size(); i++) {
      aa = newPlaces.get(i);
      if (i != newPlaces.size() - 1) {
        bb = newPlaces.get(i + 1);
      } else {
        bb = newPlaces.get(0);
      }
      totalDist += distBetweenTwoPlaces(aa, bb);
    }
    return totalDist;
  }



  /**
   * Returns an SVG containing the background and the legs of the trip.
   * @return SVG
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
   * coordinates do not match the acceptable boundaries. If
   * all the locations in the places array are removed, the
   * return array will have no values.
   * @return
   */
  private ArrayList<Integer> legDistances() {

    ArrayList<Integer> dist = new ArrayList<Integer>();
    Place aa;
    Place bb;
    for (int i = 0; i < places.size(); i++) {
      aa = places.get(i);
      if(i != places.size() - 1) {
        bb = places.get(i + 1);
      } else {
        bb = places.get(0);
      }
      dist.add(distBetweenTwoPlaces(aa, bb));
    }
    return dist;
  }

  protected int distBetweenTwoPlaces(Place aa, Place bb) {
    double ptALat = convertCoordinate(aa.latitude);
    double ptALong = convertCoordinate(aa.longitude);
    double ptBLat = convertCoordinate(bb.latitude);
    double ptBLong = convertCoordinate(bb.longitude);
    return distanceHelper(ptALat, ptALong, ptBLat, ptBLong);
  }

  /*
   * Verifies that all places currently in the places arraylist are
   * valid and are within acceptable coordinate boundaries. Any
   * locations that are invalid are removed from the places arraylist.
   */
  public void verifyPlaces(){
    try {
      for (int i = 0; i < places.size(); i++) {
        if (!verifyLatitudeCoordinates(convertCoordinate(places.get(i).latitude))
            || !verifyLongitudeCoordinates(convertCoordinate(places.get(i).longitude))) {
          System.out.println("Coordinates for location " + places.get(i).name
              + " are outside of acceptable boundaries");
          places.remove(i);
          i--;
        }
      }
    } catch (NullPointerException e) {
      System.out.println("Places is empty / has not been initialized (verifyPlaces())");
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
      if (this.options.distance.compareTo("user defined") == 0) {
        double rad;
        try {
          rad = Double.parseDouble(this.options.userRadius);
        } catch (NumberFormatException e) {
          rad = 3959;
          this.options.userUnit = "miles";
          System.out.println("Unable to parse User-Defined radius");
        }
        return (int) Math.round(rad * central_angle);
      } else if (this.options.distance.compareTo("kilometers") == 0) {
        return (int) Math.round(6371 * central_angle);
      } else if (this.options.distance.compareTo("nautical miles") == 0) {
        return (int) Math.round(3440.0695 * central_angle);
      } else {
        return (int) Math.round(3959 * central_angle);
      }
    } catch (NullPointerException e) {
      return (int) Math.round(3959 * central_angle);
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
   * Takes a single decimal longitudinal coordinate and checks to see if it is within acceptable
   * coordinate boundaries
   * for Google Maps
   * @params Double containing the coordinate
   * @return boolean indicating if the coordinate is within the boundaries.
   *  true = within boundaries, false = outside of boundaries
   */
  public boolean verifyLongitudeCoordinates(double coordinate){
    return coordinate > -180 && coordinate < 180;
  }

  /**
   * Takes a single decimal latitudinal coordinate and checks to see if it is within acceptable
   * coordinate boundaries
   * for Google Maps
   * @params Double containing the coordinate
   * @return boolean indicating if the coordinate is within the boundaries.
   *  true = within boundaries, false = outside of boundaries
   */
  public boolean verifyLatitudeCoordinates(double coordinate){
    return coordinate > -85 && coordinate < 85;
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
