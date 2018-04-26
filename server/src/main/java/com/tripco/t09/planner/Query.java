package com.tripco.t09.planner;

import com.tripco.t09.planner.Filter;
import java.util.ArrayList;
import spark.Request;

/**
 * SQL query object that reflects tffi
 */
public class Query {
  // The variables in this class should reflect a query TFFI.
  public int version;
  public int limit;
  public String type;
  public String query;
  public ArrayList<Place> places = new ArrayList<Place>();
  public ArrayList<Filter> filters = new ArrayList<Filter>();

}