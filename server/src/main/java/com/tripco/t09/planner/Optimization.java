package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t09.server.HTTP;
import java.util.LinkedList;
import spark.Request;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
import sun.awt.image.ImageWatched.Link;

public class Optimization extends Trip {

  public Optimization(ArrayList<Place> places, int[][] memoDists) {
    this.places = places;
    this.memoDists = memoDists;
  }

  /**
   * Top level method that does planning for nearest neighbor optimization. Currently will override
   * any previously calculated SVG map or distances array.
   */

  public ArrayList<Place> planNearestNeighbor() {

    ArrayList<Place> finalMinRoute = new ArrayList<Place>(places);
    // set initial minimum distance to unoptimized sum of distances
    int finalMinDist = sumDistances(finalMinRoute);
    // calculate nearestNeighbor for each starting city
    for (int i = 0; i < places.size(); ++i) {
      System.out.println("places iteration " + i + " is: ");
      ArrayList<Place> tempMinRoute = nearestNeighborRoute(places.get(i)); // starting city
      int tempMinDist = sumDistances(tempMinRoute);   // find tot. round-trip distance of new route
      if (tempMinDist < finalMinDist) {     // if less than current, set as new min (dist & route)
        finalMinRoute = new ArrayList<>(tempMinRoute);
        finalMinDist = tempMinDist;
      }
    }
    return finalMinRoute;
  }

  /**
   * This method calculates the nearest neighbor route for a given starting city (may not be
   * optimal)
   *
   * @return tempMinRoute
   */
  public ArrayList<Place> nearestNeighborRoute(Place startingCity) {
    ArrayList<Place> tempMinRoute = new ArrayList<Place>();
    Place current = startingCity;
    for (int j = 0; j < places.size(); ++j) {
      tempMinRoute.add(current);  // add city to potential minimum route
      Place next = nearestNeighborHelper(current, tempMinRoute);  // get next city
      current = next;     // repeat with this new city
    }
    return tempMinRoute;
  }


  /**
   * This method finds the next closest city to the parameter "start" / current city that is not
   * already included in the route (minRoute).
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
    if (places.size() < 4) {
      System.out.println("2Opt Optimization requires a minimum of 4 places. Using NearestNeighbor");
      return places = this.planNearestNeighbor();   // perform lesser opt. if can't do 2Opt
    }

    ArrayList<Place> minRoute = new ArrayList<>(places);
    int minDist = sumDistances(places);
    for (int i = 0; i < places.size(); i++) {
      Place current = places.get(i);
      ArrayList<Place> temp = nearestNeighborRoute(current);
      temp = nextRoute2Opt(temp);
      int tempDist = sumDistances(temp);
      if (tempDist < minDist) {
        minRoute = new ArrayList<>(temp);
        minDist = tempDist;
      }
    }
    return minRoute;
  }

  /**
   * This function finds the next 2-0pt trip for the given route (as 2-Opt runs on each iteration of
   * nearest neighbor, this function gives a new 2-Opt route for each nearest neighbor solution it
   * is given as an argument).
   */


  public ArrayList<Place> nextRoute2Opt(ArrayList<Place> nextRoute) {
    LinkedList<Place> route = new LinkedList<>(nextRoute);
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

  public ArrayList<Place> plan3Opt() {
    if (places.size() < 6) {
      System.out.println("3Opt Optimization requires a minimum of 6 places. Trying 2Opt:");
      return places = this.plan2Opt();   // perform lesser opt. if can't do 2Opt
    }
    ArrayList<Place> minRoute = new ArrayList<>(places);
    int minDist = sumDistances(places);

    for (int i = 0; i < places.size(); i++) {
      Place current = places.get(i);
      ArrayList<Place> temp = nearestNeighborRoute(current);
      temp = nextRoute3Opt(temp);
      int tempDist = sumDistances(temp);
      if (tempDist < minDist) {
        minRoute = new ArrayList<>(temp);
        minDist = tempDist;
      }
    }
    return minRoute;
  }

  private ArrayList<Place> nextRoute3Opt(ArrayList<Place> nextRoute){
    LinkedList<Place> route = new LinkedList<>(nextRoute);
    LinkedList<Place> temp = new LinkedList<>();
    route.add(route.get(0));  // for round trip algorithm
    boolean improvement = true;
    while (improvement) {
      int size = route.size();
      improvement = false;
      for(int i = 0; i < size-3; ++i){
        for(int j = i+1; j < size-2; ++j){
          for(int k = j+1; k < size-1; ++k){
            temp.clear();
            int currentDist = distanceHelper3Opt(route, i, i+1, j, j+1, k, k+1);
            //case 1:
            int case1 = distanceHelper3Opt(route, i, k, j+1, j, i+1, k+1);
            if(case1 < currentDist) {
              addUntil(temp, route, i);
              for(int w = k; w >= i+1; --w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 2:
            int case2 = distanceHelper3Opt(route, i, j, i+1, j+1, k, k+1);
            if(case2 < currentDist){
              addUntil(temp, route, i);
              for(int w = j; w >= i+1; --w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, j+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 3:
            int case3 = distanceHelper3Opt(route, i, i+1, j, k, j+1, k+1);
            if(case3 < currentDist){
              addUntil(temp, route, j);
              for(int w = k; w >= j+1; --w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 4:
            int case4 = distanceHelper3Opt(route, i, j, i+1, k, j+1, k+1);
            if(case4 < currentDist){
              addUntil(temp, route, i);
              for(int w = j; w >= i+1; --w){
                temp.add(route.get(w));
              }
              for(int w = k; w >= j+1; --w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 5:
            int case5 = distanceHelper3Opt(route, i, k, j+1, i+1, j, k+1);
            if(case5 < currentDist){
              addUntil(temp, route, i);
              for(int w = k; w >= j+1; --w){
                temp.add(route.get(w));
              }
              for(int w = i+1; w <= j; ++w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 6:
            int case6 = distanceHelper3Opt(route, i, j+1, k, j, i+1, k+1);
            if(case6 < currentDist){
              addUntil(temp, route, i);
              for(int w = j+1; w <= k; ++w){
                temp.add(route.get(w));
              }
              for(int w = j; w >= i+1; --w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
            //case 7:
            int case7 = distanceHelper3Opt(route, i, j+1, k, i+1, j, k+1);
            if(case7 < currentDist){
              addUntil(temp, route, i);
              for(int w = j+1; w <= k; ++w){
                temp.add(route.get(w));
              }
              for(int w = i+1; w <= j; ++w){
                temp.add(route.get(w));
              }
              addRemaining(temp, route, k+1);
              route = new LinkedList<>(temp);
              improvement = true;
              continue;
            }
          }
        }
      }

    }
    route.pop();
    return new ArrayList<>(route);
  }

  private void addUntil(LinkedList<Place> temp, LinkedList<Place> route, int i1){
    for(int i = 0; i <= i1; ++i){
      temp.add(route.get(i));
    }
  }

  private void addRemaining(LinkedList<Place> temp, LinkedList<Place> route, int k1){
    for(int i = k1; i < route.size(); ++i){
      temp.add(route.get(i));
    }
  }

  private int distanceHelper3Opt(LinkedList<Place> route, int a1, int a2, int b1, int b2, int c1, int c2){
    Place place1 = route.get(a1);
    Place place2 = route.get(a2);
    Place place3 = route.get(b1);
    Place place4 = route.get(b2);
    Place place5 = route.get(c1);
    Place place6 = route.get(c2);
    int dist1 = distBetweenTwoPlaces(place1, place2);
    int dist2 = distBetweenTwoPlaces(place3, place4);
    int dist3 = distBetweenTwoPlaces(place5, place6);
    return dist1 + dist2 + dist3;
  }


}
