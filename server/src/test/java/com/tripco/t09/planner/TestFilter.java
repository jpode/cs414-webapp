package com.tripco.t09.planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestFilter {
  Filter filter;

  // Setup to be done before every test in TestFilter
  @Before
  public void initialize(){
    filter = new Filter();
  }

  @Test
  public void testMembers(){
    List<String> types = Arrays.asList("heliport", "small_airport", "seaplane_base", "balloonport", "medium_airport", "large_airport");

    filter.attribute = "type";
    filter.values = new ArrayList<>(types);

    assertEquals("type", filter.attribute);
    assertArrayEquals(types.toArray(), filter.values.toArray());
  }
}
