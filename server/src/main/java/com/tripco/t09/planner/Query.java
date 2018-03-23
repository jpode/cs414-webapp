package com.tripco.t09.planner;

import java.util.ArrayList;

/**
 *
 */
public class Query {
  // The variables in this class shold reflect a query TFFI.
  public String type;
  public String query;
  public ArrayList<Place> places;

  /**
   *
   */
  public void database() {

    this.places = new ArrayList<Place>();
  }
}