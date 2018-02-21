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
    assertEquals(49.246292, place.stringToCoordinate("49° 14' 46.6512\" N"), .001);
    assertEquals(-123.116226, place.stringToCoordinate("123° 6' 58.4136\" W"), .001);
    assertEquals(-41.31666667, place.stringToCoordinate("41°19'S"), .001);
    assertEquals(174.76666667, place.stringToCoordinate("174°46'E"), .001);
    assertEquals(-33.4, place.stringToCoordinate("33°24'S"), .001);
    assertEquals(-70.66666667, place.stringToCoordinate("70°40'W"), .001);
    assertEquals(35.652832, place.stringToCoordinate("35° 39' 10.1952\" N"), .001);
    assertEquals(139.839478, place.stringToCoordinate("139° 50' 22.1208\" E"), .001);


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
