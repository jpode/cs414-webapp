package com.tripco.t09.planner;

import java.util.ArrayList;
import spark.Request;

/**
 *
 */
public class Query {

  // The variables in this class shold reflect a query TFFI.
  public int version;
  public String type;
  public String query;
  public ArrayList<Place> places = new ArrayList<Place>();

}