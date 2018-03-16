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

public class Optimization extends Trip {

  public Trip trip;

  public Optimization(Trip trip) {
    this.trip = trip;
  }

  /**
   * Top level method that does planning for nearest neighbor optimization. Currently will override
   * any previously calculated SVG map or distances array.
   */

  public ArrayList<Place> planNearestNeighbor() {
    ArrayList<Place> tempMinRoute = new ArrayList<Place>();
    ArrayList<Place> finalMinRoute = new ArrayList<Place>(trip.places);
    // set initial minimum distance to unoptimized sum of distances
    int finalMinDist = sumDistances(finalMinRoute);
    // calculate nearestNeighbor for each starting city
    for (int i = 0; i < trip.places.size(); ++i) {
      tempMinRoute.clear();
      Place current = trip.places.get(i); // starting city
      for (int j = 0; j < trip.places.size(); ++j) {
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
   * This method finds the next closest city to the parameter "start" city that is not already
   * included in the route (minRoute).
   *
   * @return next
   */

  public Place nearestNeighborHelper(Place start, ArrayList<Place> minRoute) {
    Place next = new Place();
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < trip.places.size(); ++i) {
      Place temp = trip.places.get(i);
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
    if (trip.places.size() < 4) {
      System.out.println("2Opt Optimization requires a minimum of 4 places. Using NearestNeighbor");
      trip.places = this.planNearestNeighbor();   // perform lesser optimization if can't do 2Opt
    }
    LinkedList<Place> route = new LinkedList<>(trip.places);
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

  /**
   * This method calculates and returns Delta for 2Opt optimization. Mostly here to reduce cognitive
   * complexity for Plan2Opt method and provide some abstraction. Follows typical 2Opt algorithm.
   */
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
}
