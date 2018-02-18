package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t09.server.HTTP;
import spark.Request;
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

    // hardcoded example
    return "<svg width=\"1920\" height=\"960\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><!-- Created with SVG-edit - http://svg-edit.googlecode.com/ --> <g> <g id=\"svg_4\"> <svg id=\"svg_1\" height=\"960\" width=\"1920\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"> <g id=\"svg_2\"> <title>Layer 1</title> <rect fill=\"rgb(119, 204, 119)\" stroke=\"black\" x=\"0\" y=\"0\" width=\"1920\" height=\"960\" id=\"svg_3\"/> </g> </svg> </g> <g id=\"svg_9\"> <svg id=\"svg_5\" height=\"480\" width=\"960\" y=\"240\" x=\"480\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"> <g id=\"svg_6\"> <title>Layer 2</title> <polygon points=\"0,0 960,0 960,480 0,480\" stroke-width=\"12\" stroke=\"brown\" fill=\"none\" id=\"svg_8\"/> <polyline points=\"0,0 960,480 480,0 0,480 960,0 480,480 0,0\" fill=\"none\" stroke-width=\"4\" stroke=\"blue\" id=\"svg_7\"/> </g> </svg> </g> </g> </svg>";
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

    try {
      if (this.options.distance.compareTo("miles") == 0) {
        return Math.round(3959 * central_angle);
      } else {
        return Math.round(6371 * central_angle);
      }
    } catch(NullPointerException e){
      return Math.round(3959 * central_angle);
    }
  }

}