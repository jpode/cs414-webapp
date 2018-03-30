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

  // Setup to be done before every test in TestTrip
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
    ptA.longitude = "-104.9903";
    ptA.latitude = "39.7392";

    Place ptB = new Place();
    ptB.id = "bldr";
    ptB.name = "Boulder";
    ptB.longitude = "-105.2705";
    ptB.latitude = "40.0150";

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


    testPlaces.add(ptA);
    testPlaces.add(ptB);
    testPlaces.add(ptC);
    testPlaces.add(ptD);
    testPlaces.add(ptE);

    trip.places = testPlaces;

    trip.plan();

    ArrayList<Integer> expectedDistances = new ArrayList<Integer>();
    Collections.addAll(expectedDistances, 24, 89, 211, 139, 134);
    // Call the equals() method of the first object on the second object.
    assertEquals(expectedDistances, trip.distances);
  }

  @Test
  public void testDistanceHelper() {
    Option option = new Option();
    assertEquals(7304, trip.distanceHelper(49.246292, -123.116226, -41.31666667, 174.76666667),
        2.0);
    assertEquals(10700, trip.distanceHelper(-33.4, -70.666667, 35.652832, 139.839478), 2.0);

    //checking nautical miles
    option.distance = "nautical miles";
    trip.options = option;
    assertEquals(6347, trip.distanceHelper(49.246292, -123.116226, -41.31666667, 174.76666667),
        2.0);
    assertEquals(9298, trip.distanceHelper(-33.4, -70.666667, 35.652832, 139.839478), 2.0);

    //checking user-defined distance units (meters)
    option.distance = "user defined";
    option.userRadius = "6371393";
    option.userUnit = "meters";
    trip.options = option;
    assertEquals(11755560, trip.distanceHelper(49.246292, -123.116226, -41.31666667, 174.76666667),
        2.0);
    assertEquals(17221481, trip.distanceHelper(-33.4, -70.666667, 35.652832, 139.839478), 2.0);

    // if cannot parse userRadius, should return distance in miles
    option.distance = "user defined";
    option.userRadius = "error";
    option.userUnit = "error_test";
    trip.options = option;
    assertEquals(7304, trip.distanceHelper(49.246292, -123.116226, -41.31666667, 174.76666667),
        2.0);
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
    assert(trip.verifyLongitudeCoordinates(-179) == true);
    assert(trip.verifyLongitudeCoordinates(179) == true);
    assert(trip.verifyLongitudeCoordinates(181) == false);
    assert(trip.verifyLongitudeCoordinates(-181) == false);
    assert(trip.verifyLongitudeCoordinates(0) == true);
    assert(trip.verifyLongitudeCoordinates(-1100474) == false);
  }

  @Test
  public void testLatCoordinateVerification(){

    assert(trip.verifyLatitudeCoordinates(-84) == true);
    assert(trip.verifyLatitudeCoordinates(84) == true);
    assert(trip.verifyLatitudeCoordinates(86) == false);
    assert(trip.verifyLatitudeCoordinates(-86) == false);
    assert(trip.verifyLatitudeCoordinates(0) == true);
    assert(trip.verifyLatitudeCoordinates(-1100474) == false);
  }

  @Test
  public void TestVerifyPlaces() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();

    Place ptA = new Place();
    ptA.id = "aust";
    ptA.name = "Austin";
    ptA.longitude = "-104.9903";
    ptA.latitude = "39.7392";

    Place ptF = new Place();
    ptF.id = "moon";
    ptF.name = "The Moon";
    ptF.longitude = "-181.523";
    ptF.latitude = "39.7392";

    Place ptB = new Place();
    ptB.id = "nyny";
    ptB.name = "New York";
    ptB.longitude = "-105.2705";
    ptB.latitude = "40.0150";

    Place ptC = new Place();
    ptC.id = "bost";
    ptC.name = "Boston";
    ptC.longitude = "-106.445";
    ptC.latitude = "40.95";

    Place ptD = new Place();
    ptD.id = "atla";
    ptD.name = "Atlanta";
    ptD.longitude = "-103.6";
    ptD.latitude = "38.824";

    Place ptG = new Place();
    ptG.id = "sun";
    ptG.name = "The Sun";
    ptG.longitude = "120.9903";
    ptG.latitude = "391898.7392";

    Place ptE = new Place();
    ptE.id = "chic";
    ptE.name = "Chicago";
    ptE.longitude = "-105.89";
    ptE.latitude = "37.924";

    Place ptH = new Place();
    ptH.id = "mars";
    ptH.name = "Mars";
    ptH.longitude = "8592.713";
    ptH.latitude = "-34656.98";


    testPlaces.add(ptA);
    testPlaces.add(ptF);
    testPlaces.add(ptB);
    testPlaces.add(ptC);
    testPlaces.add(ptD);
    testPlaces.add(ptG);
    testPlaces.add(ptE);
    testPlaces.add(ptH);

    trip.places = testPlaces;

    trip.verifyPlaces();

    ArrayList<Place> expectedPlaces = new ArrayList<Place>();
    Collections.addAll(expectedPlaces, ptA, ptB, ptC, ptD, ptE);

    for(int i = 0; i < expectedPlaces.size(); i++){
      assertEquals(expectedPlaces.get(i).id, trip.places.get(i).id);
    }

  }

  @Test
  public void testNearestNeighbor() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();
    Option option = new Option();
    option.distance = "miles";
    option.optimization = "0.5";
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

    testPlaces.add(placeE); // Littleton
    testPlaces.add(placeD); // Aurora
    testPlaces.add(placeA); // Fort Collins
    testPlaces.add(placeC); // Greeley
    testPlaces.add(placeB); // Longmont

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

  @Test
  public void test2Opt() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();
    Option option = new Option();
    option.distance = "miles";
    option.optimization = "1.0";
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
    expectedOrder.add(placeA);    // fort collins
    expectedOrder.add(placeC);    // then greeley
    expectedOrder.add(placeD);    // then aurora
    expectedOrder.add(placeE);    // then littleton
    expectedOrder.add(placeB);    // then longmont
    assertEquals(expectedOrder, trip.places);
  }

  @Test
  public void test2OptPart2() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();
    Option option = new Option();
    option.distance = "miles";
    option.optimization = "1.0";
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

    testPlaces.add(placeE); // Littleton
    testPlaces.add(placeC); // Greeley
    testPlaces.add(placeA); // Fort Collins

    trip.places = testPlaces;
    trip.plan();
    trip.optimize();

    ArrayList<Place> expectedOrder = new ArrayList<Place>();
    expectedOrder.add(placeE);    // littleton
    expectedOrder.add(placeC);    // then greeley
    expectedOrder.add(placeA);    // then fort collins

    assertEquals(expectedOrder, trip.places);

  }

  @Test
  public void version1Tests() {
    ArrayList<Place> testPlaces = new ArrayList<Place>();
    Option option = new Option();
    option.optimization = "none";
    trip.options = option;
    trip.version = 1;

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

    testPlaces.add(placeA);
    testPlaces.add(placeE);
    testPlaces.add(placeC);
    trip.places = new ArrayList<>(testPlaces);
    trip.plan();

    assertEquals(testPlaces, trip.places);
    trip.optimize();
    assertEquals(testPlaces, trip.places);
  }

  @Test
  public void testUninitializedTrip() {
    try {
      trip.plan();
    } catch (Exception e) {
      String expectedMessage = "Places is empty / has not been initialized (verifyPlaces())";
      assertEquals(expectedMessage, e.getMessage());
    }
  }

}
