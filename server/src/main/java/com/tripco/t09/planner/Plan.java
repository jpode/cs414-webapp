package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.tripco.t09.server.HTTP;
import spark.Request;

import java.util.ArrayList;

/**
 * This class handles to the conversions of the trip request/resopnse,
 * converting from the Json string in the request body to a Trip object,
 * planning the Trip, and
 * converting the resulting Trip object back to a Json string
 * so it may returned as the response.
 */
public class Plan {

  private Trip trip;

  /** Handles trip planning request, creating a new trip object from the trip request.
   * Does the conversion from Json to a Java class before planning the trip.
   * @param request
   */
  public Plan (Request request) {
    // first print the request
    System.out.println(HTTP.echoRequest(request));

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    trip = gson.fromJson(requestBody, Trip.class);

    // log something.
    System.out.println("Title = " + trip.title);
  }

  public Double optimizationLevel(){
    if(trip.options.optimization != null){
      return trip.options.optimization;
    }
    return (double)0;
  }

  /**
   * The planTrip method is the entry point / method to planning any trip. Called from 
   * Plan Constructor, and passes control to Trip.java's plan method.
   */
  
  public void planTrip(){
    trip.plan();
    if (trip.options.optimization != null && trip.options.optimization != 0) {
      optimize();
    }
  }

  /**
   * Plan.optimize() exists only to redirect to trip.optimize(). It works as a go-between for
   * MicroServer.java, which contains objects of type Plan, and Trip.java, which contains the
   * actual data to be optimized (places etc.). As the MicroServer call to Plan.optimize() will
   * always first call the Plan.java constructor, which initializes a new trip object, only minor
   * and likely unnecessary error checking was added to Trip's optimize method.
   */

  public void optimize() {
    System.out.println("Optimizing trip with level " + trip.options.optimization);
    trip.optimize();
  }
  /** Handles the response for a Trip object.
   * Does the conversion from a Java class to a Json string.*
   */


  public String getTrip () {
    Gson gson = new Gson();
    return gson.toJson(trip);
  }

  /*
   * Returns a status code back to Microserver. Basic error checking for the supplied JSON.
   * Currently returns a 400: Bad Request if any essential JSON information is missing, otherwise
   * returns 200: OK
   */

  public int getStatus(){
    if(trip.places.size() == 0 || trip.options.distance == null || trip.title == null || trip.type == null){
      return 400;
    }
    return 200;
  }
}
