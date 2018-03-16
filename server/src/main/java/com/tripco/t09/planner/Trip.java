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
    verifyPlaces();
    if (this.options.optimization < 0.35 && this.options.optimization != 0) {
      this.places = this.planNearestNeighbor();
      System.out.println("Optimized Round Trip Distance: " + sumDistances(places));
    } else if (this.options.optimization < 0.7) {
      if (places.size() < 4) {
        System.out.println("2Opt Optimization requires a minimum of 4 places. Please add places "
            + "or consider another optimization method");
        this.places = this.planNearestNeighbor();   // perform lesser optimization if can't do 2Opt
      } else {
        this.places = this.plan2Opt();
        System.out.println("Optimized Round Trip Distance: " + sumDistances(places));
      }
    } else {
      this.plan3Opt();
    }
    this.plan();
  }

  /**
   * Top level method that does planning for nearest neighbor optimization. Currently will override
   * any previously calculated SVG map or distances array.
   */

  public ArrayList<Place> planNearestNeighbor() {
    ArrayList<Place> tempMinRoute = new ArrayList<Place>();
    ArrayList<Place> finalMinRoute = new ArrayList<Place>(places);
    // set initial minimum distance to unoptimized sum of distances
    int finalMinDist = sumDistances(finalMinRoute);
    // calculate nearestNeighbor for each starting city
    for (int i = 0; i < places.size(); ++i) {
      tempMinRoute.clear();
      Place current = places.get(i); // starting city
      for (int j = 0; j < places.size(); ++j) {
        tempMinRoute.add(current);  // add city to potential minimum route
        Place next = nearestNeighborHelper(current, tempMinRoute);  // get next city
        current = next;     // repeat with this new city
      }
      int tempMinDist = sumDistances(tempMinRoute);   // find tot. round-trip distance of new route
      if (tempMinDist < finalMinDist) {     // if less than current, set as new min (dist & route)
        finalMinRoute = new ArrayList<Place>(tempMinRoute);
        finalMinDist = tempMinDist;
      }
    }
    return finalMinRoute;
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
   * This method finds the next closest city to the parameter "start" city that is not already
   * included in the route (minRoute).
   *
   * @return next
   */

  public Place nearestNeighborHelper(Place start, ArrayList<Place> minRoute) {
    Place next = new Place();
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < places.size(); ++i) {
      Place temp = places.get(i);
      if (minRoute.contains(temp)) {
        continue;
      }
      int tempDist = distBetweenTwoPlaces(start, temp);
      if (tempDist < minDist) {
        next = temp;
        minDist = tempDist;
      }
    }
    return next;
  }

  /**
   * Top level method that does planning for 2-Opt optimization. Currently will override any
   * previously calculated SVG map or distances array.
   */

  public ArrayList<Place> plan2Opt() {
    LinkedList<Place> route = new LinkedList<>(places);
    route.add(route.get(0));  // for round trip algorithm
    boolean improvement = true;
    while (improvement) {
      improvement = false;
      for (int i = 0; i < route.size() - 3; ++i) {
        for (int k = i + 2; k < route.size() - 1; ++k) {
          int delta = get2OptDelta(route, i, k);
          if (delta < 0) {
            reverse2Opt(route, i + 1, k);
            improvement = true;
          }
        }
      }
    }
    route.remove(route.size() - 1); // remove first element again
    return new ArrayList<>(route);
  }

  public int get2OptDelta(LinkedList<Place> route, int i1, int k1) {
    int delta = -distBetweenTwoPlaces(route.get(i1), route.get(i1 + 1))
        - distBetweenTwoPlaces(route.get(k1), route.get(k1 + 1))
        + distBetweenTwoPlaces(route.get(i1), route.get(k1))
        + distBetweenTwoPlaces(route.get(i1 + 1), route.get(k1 + 1));
    return delta;
  }

  /**
   * This function is a helper function for the 2Opt optimization.
   */
  public void reverse2Opt(LinkedList<Place> route, int i1, int k1) {
    while (i1 < k1) {
      Place temp = route.get(i1);
      route.remove(i1);
      route.add(i1, route.get(k1 - 1));
      route.remove(k1);
      route.add(k1, temp);
      i1++;
      k1--;
    }
  }

  /**
   * Top level method that does planning for 3-Opt optimization. Currently will override any
   * previously calculated SVG map or distances array.
   */

  public void plan3Opt() {

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
   * coordinates do not match the Colorado boundaries. If
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

  private int distBetweenTwoPlaces(Place aa, Place bb) {
    double ptALat = convertCoordinate(aa.latitude);
    double ptALong = convertCoordinate(aa.longitude);
    double ptBLat = convertCoordinate(bb.latitude);
    double ptBLong = convertCoordinate(bb.longitude);
    return distanceHelper(ptALat, ptALong, ptBLat, ptBLong);
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