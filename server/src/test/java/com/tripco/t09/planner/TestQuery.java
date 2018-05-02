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
public class TestQuery {
  Query query;

  // Setup to be done before every test in TestFilter
  @Before
  public void initialize(){
    query = new Query();
  }

  @Test
  public void testMembers(){
    query.type = "test type";
    query.version = 2;
    query.limit = 20;
    query.query = "test query";

    List<String> types = Arrays.asList("heliport", "small_airport", "seaplane_base");
    List<String> types2 = Arrays.asList("balloonport", "medium_airport", "large_airport");

    Filter f1 = new Filter();
    Filter f2 = new Filter();
    f1.attribute = "test1";
    f2.attribute = "test2";
    f1.values = new ArrayList<>(types);
    f2.values = new ArrayList<>(types2);

    List<Filter> filters = Arrays.asList(f1, f2);
    query.filters = new ArrayList<>(filters);

    assertEquals("test type", query.type);
    assertEquals(2, query.version);
    assertEquals(20, query.limit);
    assertEquals("test query", query.query);
    assertEquals("test1", query.filters.get(0).attribute);
    assertEquals("test2", query.filters.get(1).attribute);
    assertArrayEquals(filters.get(0).values.toArray(), query.filters.get(0).values.toArray());
    assertArrayEquals(filters.get(1).values.toArray(), query.filters.get(1).values.toArray());
  }
}
