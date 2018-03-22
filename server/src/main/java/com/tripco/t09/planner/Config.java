package com.tripco.t09.planner;

/**
 * This class is used solely to consolidate the specific information required for Trip.type =
 * "config" requests.
 */
public class Config {

  public String type;
  public int version;
  public int optimization;

  public Config(Trip trip) {
    this.type = trip.type;
    this.version = trip.version;
    this.optimization = trip.options.numOfOptimizations;
  }
}
