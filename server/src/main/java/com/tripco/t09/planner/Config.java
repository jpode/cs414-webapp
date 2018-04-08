package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.tripco.t09.planner.OptType;

/**
 * This class is used solely to consolidate the specific information required for config requests.
 */
public class Config {

  public String type;
  public int version;
  public String[] maps;
  public int optimization;
  public OptType[] optimizations;
  public String[] distances;

  /**
   * Returns the configuration trip object consisting of type, version, # of optimizations,
   * and distance unit types.
   */
  public Config() {
    this.type = "config";
    this.version = 3;
    this.maps = new String[]{"svg"};
    this.optimization = 2;
    this.optimizations = new OptType[]{new OptType("NN", "Basic Nearest Neighbor algorithm"),
                                       new OptType("2-opt", "Expansion on NN using 2-opt algorithm")
                                      };
    this.distances = new String[]{"miles", "kilometers", "nautical miles", "user defined"};
  }

  public String getConfig(){
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
