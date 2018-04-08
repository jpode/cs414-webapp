package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.util.ArrayList;

/**
 * This class is used solely to consolidate the specific information required for config requests.
 */
public class Config {

  public String type;
  public int version;
  public int optimization;
  public String[] distances;
  public ArrayList<Filter> filters;

  /**
   * Returns the configuration trip object consisting of type, version, # of optimizations,
   * and distance unit types.
   */
  public Config() {
    this.type = "config";
    this.version = 2;
    this.optimization = 2;
    this.distances = new String[]{"miles", "kilometers", "nautical miles", "user defined"};
    Database db = new Database();
    this.filters = db.getFilters();
  }

  public String getConfig(){
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
