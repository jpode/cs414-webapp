package com.tripco.t09.planner;

import java.util.ArrayList;

public class Route {
  Integer dist;
  ArrayList<Place> places;

  public Route(Integer dist, ArrayList<Place> places){
    this.dist = dist;
    this.places = places;
  }
}
