package com.tripco.t09.planner;

import com.tripco.t09.server.MicroServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/*
  This class contains tests for the Trip class.
 */
@RunWith(JUnit4.class)
public class TestTrip {
  Trip trip;

  // Setup to be done before every test in TestPlan
  @Before
  public void initialize() {
    trip = new Trip();
  }

  @Test
  public void testTrue() {
    // assertTrue checks if a statement is true
    assertTrue(true == true);
  }

  @Test
  public void testDistances() {

    ArrayList<Place> testPlaces = new ArrayList<Place>();

    Place ptA = new Place();
    ptA.id = "dnvr";
    ptA.name = "Denver";
    ptA.latitude = "-104.9903";
    ptA.longitude = "39.7392";

    Place ptB = new Place();
    ptB.id = "bldr";
    ptB.name = "Boulder";
    ptB.latitude = "-105.2705";
    ptB.longitude = "40.0150";

    testPlaces.add(ptA);
    testPlaces.add(ptB);

    trip.places = testPlaces;

    trip.plan();

    ArrayList<Integer> expectedDistances = new ArrayList<Integer>();
    Collections.addAll(expectedDistances, 20, 20);
    // Call the equals() method of the first object on the second object.
    assertEquals(expectedDistances, trip.distances);
  }

  @Test
  public void testDistanceHelper() {
    assertEquals(7304, trip.distanceHelper(49.246292, -123.116226, -41.31666667, 174.76666667),
        2.0);
    assertEquals(10700, trip.distanceHelper(-33.4, -70.666667, 35.652832, 139.839478), 2.0);
  }

  @Test
  public void testConvertCoordinate() {
    assertEquals(49.246292, trip.convertCoordinate("49° 14' 46.6512\" N"), .001);
    assertEquals(-123.116226, trip.convertCoordinate("123° 6' 58.4136\" W"), .001);
    assertEquals(-41.31666667, trip.convertCoordinate("41°19'S"), .001);
    assertEquals(174.76666667, trip.convertCoordinate("174°46'E"), .001);
    assertEquals(-33.4, trip.convertCoordinate("33°24'S"), .001);
    assertEquals(-70.66666667, trip.convertCoordinate("70°40'W"), .001);
    assertEquals(35.652832, trip.convertCoordinate("35° 39' 10.1952\" N"), .001);
    assertEquals(139.839478, trip.convertCoordinate("139° 50' 22.1208\" E"), .001);
    assertEquals(10.8333, trip.convertCoordinate("10.8333"), .001);
    assertEquals(-5, trip.convertCoordinate("-5"), .001);
    assertEquals(1509.1589, trip.convertCoordinate("1509.1589"), .001);
  }

  @Test
  public void testLongCoordinateVerification(){
    assert(trip.verifyLongitudeCoordinates(38) == true);
    assert(trip.verifyLongitudeCoordinates(36) == false);
    assert(trip.verifyLongitudeCoordinates(42) == false);
  }

  @Test
  public void testLatCoordinateVerification(){
    assert(trip.verifyLatitudeCoordinates(-105) == true);
    assert(trip.verifyLatitudeCoordinates(-100) == false);
    assert(trip.verifyLatitudeCoordinates(-110) == false);
  }



}
