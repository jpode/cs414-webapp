package com.tripco.t09.planner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/*
  This class contains tests for the Place class.
 */
@RunWith(JUnit4.class)
public class TestPlace {
  Place place;

  // Setup to be done before every test in TestPlace
  @Before
  public void initialize() {
    place = new Place();
  }

  @Test
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue(true == true);
  }

  @Test
  public void testConvertCoordinate() {
    assert(10.8333 == place.convertCoordinate("10.8333"));
  }

  @Test
  public void testStringToCoordinate() {
    assert(15.03417 == place.stringToCoordinate("15°2\'3\"N"));
  }

  @Test
  public void testLongCoordinateVerification(){
    assert(place.verifyLongitudeCoordinates(38) == true);
    assert(place.verifyLongitudeCoordinates(36) == false);
    assert(place.verifyLongitudeCoordinates(42) == false);
  }

  @Test
  public void testLatCoordinateVerification(){
    assert(place.verifyLatitudeCoordinates(-105) == true);
    assert(place.verifyLatitudeCoordinates(-100) == false);
    assert(place.verifyLatitudeCoordinates(-110) == false);
  }

}
