package com.tripco.t09.planner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to represent an editor object to allow user to reconfigure
 * the trip.
 */
public class Editor {
  public String editType;
  public int targetIndex = -1; // index of place to change (if specified) i.e. for remove
  public int destIndex = -1;   // where place should be put in places array (index)
  public String optimization;
  public Place newPlace;
  public ArrayList<Place> places;
  public ArrayList<Integer> distances;

  /**
   * Insert() inserts new_place into the places array at index destIndex. Requires replanning
   * @return 1,0
   */
  public int insert(){
    System.out.println("Inserting new place...");
    if(verifyInit() == -1) {
      System.out.println("Failed: Incorrect values");
      return -1;
    }
    if(destIndex > places.size()) {
      places.add(newPlace);
    }
    else {
      places.add(destIndex, newPlace);
    }
    System.out.println("Inserted");
    return 0;
  }

  /**
   * Reverse() simply reverses the trip and, by extension, appropriately modifies
   * the distances arraylist. Does not require replanning.
   * @return 1,0
   */

  public int reverse(){
    System.out.println("Reversing trip...");
    if(verifyInit() == -1) {
      System.out.println("Failed: Incorrect values");
      return -1;
    }
    try {
      Place temp = places.get(0);
      places.remove(0);
      System.out.println("Storing " + temp.name);
      Collections.reverse(places);
      places.add(0, temp);
      if(distances != null) {
        ArrayList<Integer> newDistances = new ArrayList<>();
        //newDistances.add(0);
        for (int i = distances.size() - 1; i >= 0; --i) {
          newDistances.add(distances.get(i));
        }
        distances = newDistances;
      }
    } catch(UnsupportedOperationException e) {
      return -1;
    }
    System.out.println("Reversed");
    return 0;
  }

  /**
   * Remove() removes the place specified by targetIndex; requires replanning trip to recalculate
   * optimal routes (if optimized), or at minimum distances (if not optimized).
   * @return 1,0
   */
  public int remove(){
    if(verifyInit() == 1) {
      return -1;
    }
    places.remove(targetIndex);
    return 0;
  }

  /**
   * changeStartPos() simply shifts the array until places begins with the element specified at
   * index targetIndex. Requires replanning, as some distances may have not been previously
   * calculated in the distances array.
   * @return 1,0
   */

  public int changeStartPos(){
    System.out.println("Changing start position...");
    if(verifyInit() == -1) {
      System.out.println("Failed: Incorrect values");
      return -1;
    }

    ArrayList<Place> newPlaces = new ArrayList<>();
    newPlaces.add(places.get(targetIndex));

    for(int i = targetIndex+1; i < places.size(); ++i){
      newPlaces.add(places.get(i));
    }
    for(int i = 0; i < targetIndex; ++i){
      newPlaces.add(places.get(i));
    }
    places = newPlaces;
    /*
    Place temp = places.get(targetIndex);
    places.remove(targetIndex);
    places.add(0,temp);
    System.out.println("Changed start position");
    */
    return 0;
  }

  public int movePlace(){
    System.out.println("Moving place...");
    if(verifyInit() == -1) {
      System.out.println("Failed: Incorrect values");
      return -1;
    }

    Place temp = places.get(targetIndex);
    places.remove(targetIndex);
    places.add(destIndex, temp);

    System.out.println("Moved");
    return 0;
  }
  /**
   * verifyInit() checks the necessary fields are appropriately assigned values, depending on
   * the specific request.
   * @return 1,0
   */
  public int verifyInit(){
    if(editType.equals("remove") || editType.equals("newStart")){
      if(places == null || targetIndex < 0 || targetIndex > places.size()) {
        return -1;
      }
    }
    else if(editType.equals("reverse")){
      if(places == null) {
        return -1;
      }
    }
    else{   // "insert"
      if(destIndex < 0 || newPlace == null) {
        return -1;
      }
    }
    return 0;
  }

}
