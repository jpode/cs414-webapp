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

    Place ptH = new Place();
    ptH.id = "nyci";
    ptH.name = "New York City";
    ptH.longitude = "-68.163";
    ptH.latitude = "30.5788";

    Place ptA = new Place();
    ptA.id = "dnvr";
    ptA.name = "Denver";
    ptA.longitude = "-104.9903";
    ptA.latitude = "39.7392";

    Place ptB = new Place();
    ptB.id = "bldr";
    ptB.name = "Boulder";
    ptB.longitude = "-105.2705";
    ptB.latitude = "40.0150";

    Place ptG = new Place();
    ptG.id = "atla";
    ptG.name = "Atlanta";
    ptG.longitude = "-76.97";
    ptG.latitude = "26.298";

    Place ptC = new Place();
    ptC.id = "foco";
    ptC.name = "Fort Collins";
    ptC.longitude = "-106.445";
    ptC.latitude = "40.95";

    Place ptD = new Place();
    ptD.id = "lgmt";
    ptD.name = "Longmont";
    ptD.longitude = "-103.6";
    ptD.latitude = "38.824";

    Place ptE = new Place();
    ptE.id = "grly";
    ptE.name = "Greeley";
    ptE.longitude = "-105.89";
    ptE.latitude = "37.924";

    Place ptF = new Place();
    ptF.id = "chic";
    ptF.name = "Chicago";
    ptF.longitude = "-94.27";
    ptF.latitude = "39.311";

    testPlaces.add(ptH);
    testPlaces.add(ptA);
    testPlaces.add(ptB);
    testPlaces.add(ptG);
    testPlaces.add(ptC);
    testPlaces.add(ptD);
    testPlaces.add(ptE);
    testPlaces.add(ptF);

    trip.places = testPlaces;

    trip.plan();

    ArrayList<Integer> expectedDistances = new ArrayList<Integer>();
    Collections.addAll(expectedDistances, 24, 89, 211, 139, 134);
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
    assert(trip.verifyLongitudeCoordinates(-105) == true);
    assert(trip.verifyLongitudeCoordinates(-100) == false);
    assert(trip.verifyLongitudeCoordinates(-110) == false);
  }

  @Test
  public void testLatCoordinateVerification(){
    assert(trip.verifyLatitudeCoordinates(38) == true);
    assert(trip.verifyLatitudeCoordinates(36) == false);
    assert(trip.verifyLatitudeCoordinates(41) == false);
  }

  @Test
  public void testNearestNeighbor() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();
    Option option = new Option();
    option.distance = "miles";
    option.optimization = 0.33;
    trip.options = option;

    Place placeA = new Place();
    placeA.id = "foco";
    placeA.name = "Fort Collins";
    placeA.latitude = "40° 35' 06\" N";
    placeA.longitude = "105° 05' 03\" W";

    Place placeE = new Place();
    placeE.id = "litl";
    placeE.name = "Littleton";
    placeE.latitude = "39° 36' 49\" N";
    placeE.longitude = "105° 01' 00\" W";

    Place placeC = new Place();
    placeC.id = "grly";
    placeC.name = "Greeley";
    placeC.latitude = "40° 25' 23\" N";
    placeC.longitude = "104° 42' 32\" W";

    Place placeD = new Place();
    placeD.id = "auro";
    placeD.name = "Aurora";
    placeD.latitude = "39° 43' 32\" N";
    placeD.longitude = "104° 49' 32\" W";

    Place placeB = new Place();
    placeB.id = "lgmt";
    placeB.name = "Longmont";
    placeB.latitude = "40° 10' 01\" N";
    placeB.longitude = "105° 06' 06\" W";

    testPlaces.add(placeA);
    testPlaces.add(placeE);
    testPlaces.add(placeC);
    testPlaces.add(placeD);
    testPlaces.add(placeB);

    trip.places = testPlaces;
    trip.plan();
    trip.optimize();

    ArrayList<Place> expectedOrder = new ArrayList<Place>();
    expectedOrder.add(placeC);    // greeley
    expectedOrder.add(placeA);    // then fort collins
    expectedOrder.add(placeB);    // then longmont
    expectedOrder.add(placeD);    // then aurora
    expectedOrder.add(placeE);    // then littleton

    assertEquals(expectedOrder, trip.places);


  }



}
