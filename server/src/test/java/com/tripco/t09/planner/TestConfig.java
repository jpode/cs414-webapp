package com.tripco.t09.planner;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestConfig {
  //TODO: Get this to build with Travis CI. It gives a communication link failure in Database currently.
  //Config config;

  // Setup to be done before every test in TestConfig
  @Before
  public void initialize() {
    //config = new Config();
  }

  @Test
  public void placeholder(){
    assertTrue(true);
  }
  /*
  @Test
  public void testMembers(){
    String[] maps = {"svg"};
    String[] units = {"miles", "kilometers", "nautical miles", "user defined"};
    OptType[] optimizations = {new OptType("NN", "Basic Nearest Neighbor algorithm"),
        new OptType("2-opt", "Expansion on NN using 2-opt algorithm")};

    assertEquals(config.type, "config");
    assertEquals(config.version, 3);
    assertArrayEquals(config.maps, maps);
    assertEquals(config.optimization, 2);
    assertEquals(config.optimizations[0].label, optimizations[0].label);
    assertEquals(config.optimizations[1].label, optimizations[1].label);
    assertArrayEquals(config.units, units);
  }

  @Test
  public void testFilters(){
    List<String> types = Arrays.asList("heliport", "small_airport", "seaplane_base", "balloonport", "medium_airport", "large_airport");
    Filter type = new Filter();
    type.attribute = "type";
    type.values = new ArrayList<>(types);

    List<String> continents = Arrays.asList("Africa", "Antarctica", "Asia", "Europe", "North America", "Oceania", "South America");
    Filter continent = new Filter();
    continent.attribute = "continents";
    continent.values = new ArrayList<>(continents);

    assertEquals(config.filters.get(0).attribute, type.attribute);
    assertArrayEquals(config.filters.get(0).values.toArray(), type.values.toArray());

    assertEquals(config.filters.get(3).attribute, continent.attribute);
    assertArrayEquals(continent.values.toArray(), config.filters.get(3).values.toArray());
  }

  @Test
  public void testGson(){
    Gson gson = new Gson();
    Config testConfig = gson.fromJson(config.getConfig(), Config.class);

    assertEquals(config.type, testConfig.type);
    assertEquals(config.version, testConfig.version);
    assertArrayEquals(config.maps, testConfig.maps);
    assertEquals(config.optimization, testConfig.optimization);
    assertEquals(config.optimizations[0].label, testConfig.optimizations[0].label);
    assertEquals(config.optimizations[1].label, testConfig.optimizations[1].label);
    assertArrayEquals(config.units, testConfig.units);
  }
  */

}
