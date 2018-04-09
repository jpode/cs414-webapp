package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
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
  private boolean jsonParsed = false;

  /** Handles trip planning request, creating a new trip object from the trip request.
   * Does the conversion from Json to a Java class before planning the trip.
   * @param request
   */
  public Plan(Request request) {
    // first print the request
    System.out.println(HTTP.echoRequest(request));

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());
    System.out.println(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    trip = gson.fromJson(requestBody, Trip.class);
    //trip.distArr = new int[trip.places.size()][trip.places.size()];

    // log something.
    System.out.println("Title = " + trip.title);
  }

  public Plan(Trip newTrip){
    trip = newTrip;
  }

  /*
   * Returns the optimization level if one is given, otherwise returns 0 indicating no optimization
   */
  public Double optimizationLevel(){
    Double optLevel;
    if (trip.options.optimization == null)
      return 0.0;
    try {
      optLevel = Double.parseDouble(trip.options.optimization);
    } catch (NumberFormatException e) {
      optLevel = 0.0;
    }
    return optLevel;
  }


  /**
   * The planTrip method is the entry point / method to planning any trip. Called from
   * Plan Constructor, and passes control to Trip.java's plan method.
   */
  
  public void planTrip(){
    trip.plan();
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
    if (trip.places.size() < 2) {
      System.out.println("Cannot Optimize for a single location trip");
      trip.plan();
    } else {
      trip.optimize();
    }

  }

  /** Handles the response for a Trip object.
   * Does the conversion from a Java class to a Json string.
   */
  public String getTrip() {
    try {
      Gson gson = new Gson();
      jsonParsed = true;
      return gson.toJson(trip);
    } catch (JsonParseException e) {
      System.err.println(e);
      jsonParsed = false;
    }
    return null;
  }

  /** Handles the response for a Trip object with no optimization.
   * Does the conversion from a Java class to a Json string.
   * A shitty hack from jake and drew
   */
  public String getTripNoOpt() {
    try {
      String optValue = trip.options.optimization;
      trip.options.optimization = "0";

      Gson gson = new Gson();
      jsonParsed = true;
      String result = gson.toJson(trip);
      trip.options.optimization = optValue;

      return result;
    } catch (JsonParseException e) {
      System.err.println(e);
      jsonParsed = false;
    }
    return null;
  }

  /*
   * Returns a status code back to Microserver. Basic error checking for the supplied JSON.
   * Currently returns a 400: Bad Request if any essential JSON information is missing or JSON
   * is not in a parseable format, otherwise returns 200: OK
   */
  public int getStatus() {
    //If there are no places or distance units
    if (trip.type == "trip") {
      if (trip.places.size() == 0 || trip.options.distance == null) {
        return 400;
      }
      //If the title or type are not defined
      if (trip.title == null || trip.type == null) {
        return 400;
      }
      //If the json was not correctly parsed
      if (!jsonParsed) {
        return 400;
      }
    }
    return 200;
  }
}
