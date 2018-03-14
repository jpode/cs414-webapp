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
    return trip.options.optimization;
  }

  // Plan the trip
  public void planTrip(){
      trip.plan();
  }

  // Optimize the trip Will not work if trip has no distances. this is intended to prevent optimization
  // on a file that has not been planned yet.
  public void optimize(){
    System.out.println("Optimizing trip with level " + trip.options.optimization);
    /*
    if(trip.distances.size() > 0){
      trip.optimize();
    }
    */
  }
  /** Handles the response for a Trip object.
   * Does the conversion from a Java class to a Json string.*
   */
  public String getTrip () {
    Gson gson = new Gson();
    return gson.toJson(trip);
  }

}
