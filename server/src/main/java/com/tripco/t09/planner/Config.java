package com.tripco.t09.planner;

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
