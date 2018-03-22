package com.tripco.t09.planner;

import com.tripco.t09.server.RestAPI;
import spark.Request;

/**
 * this is the search class that searches the database
 */
public class Search extends RestAPI {

  /**
   *
   * @param request
   */
  public Search(Request request) {

    super(request, Query.class);

    ((Query) object).database();
  }

}