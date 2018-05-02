package com.tripco.t09.planner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;

public class ThreeOpt extends Trip implements Callable<Route> {
  private int startIndex;
  private int endIndex;

  public ThreeOpt(ArrayList<Place> places, int startIndex, int endIndex) {
    this.places = places;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  /**
   * Top level method that does planning for 3-Opt optimization. Currently will override any
   * previously calculated SVG map or distances array.
   */

  public Route call(){
    Optimization opt = new Optimization(places, memoDists);
    Route result;
    if (places.size() < 6) {   // perform lesser opt. if can't do 2Opt
      System.out.println("3Opt Optimization requires a minimum of 6 places. Trying 2Opt:");
      places = opt.plan2Opt();
      result = new Route(sumDistances(places), places);
      return result;
    }
    ArrayList<Place> minRoute = new ArrayList<>(places);
    int minDist = sumDistances(places);

    for (int i = startIndex; i < endIndex; i++) {
      Place current = places.get(i);
      ArrayList<Place> temp = opt.nearestNeighborRoute(current);
      temp = nextRoute3Opt(temp);
      int tempDist = sumDistances(temp);
      if (tempDist < minDist) {
        minRoute = new ArrayList<>(temp);
        minDist = tempDist;
      }
    }
    result = new Route(minDist, minRoute);
    return result;
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
