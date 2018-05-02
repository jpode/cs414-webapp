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
public class TestOptType {
  OptType optType;

  // Setup to be done before every test in TestFilter
  @Before
  public void initialize(){
    optType = new OptType("test label", "test description");
  }

  @Test
  public void testMembers(){
    assertEquals("test label", optType.label);
    assertEquals("test description", optType.description);
  }
}
