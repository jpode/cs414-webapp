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
   * including the return to the starting point to make a round trip.
   * @return
   */
  private ArrayList<Integer> legDistances() {

    ArrayList<Integer> dist = new ArrayList<Integer>();

    // hardcoded example
    dist.add(12);
    dist.add(23);
    dist.add(34);
    dist.add(45);
    dist.add(65);
    dist.add(19);

    return dist;
  }

  //follows chord length formula given here:
  // https://en.wikipedia.org/wiki/Great-circle_distance#From_chord_length
  public double distanceHelper(double ptA_LAT, double ptA_LONG, double ptB_LAT, double ptB_LONG) {
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
    double distance = 3959 * central_angle;
    return distance;

  }

}