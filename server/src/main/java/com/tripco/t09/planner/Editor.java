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
   * @return
   */
  public int insert(){
    if(verifyInit() == -1)
      return -1;
    if(destIndex < 0 || newPlace == null){
      return -1;
    }
    if(destIndex > places.size())
      places.add(newPlace);
    else
      places.add(destIndex, newPlace);
    return 0;
  }

  /**
   * Reverse() simply reverses the trip and, by extension, appropriately modifies
   * the distances arraylist. Does not require replanning.
   * @return
   */

  public int reverse(){
    if(verifyInit() == -1)
      return -1;
    try {
      Collections.reverse(places);
      ArrayList<Integer> newDistances = new ArrayList<>();
      newDistances.add(0);
      for(int i = distances.size()-1; i > 0; --i){
        newDistances.add(distances.get(i));
      }
      distances = newDistances;
    } catch(UnsupportedOperationException e) {
      return -1;
    } catch(NullPointerException e){
      return -1;
    }
    return 0;
  }

  /**
   * Remove() removes the place specified by targetIndex; requires replanning trip to recalculate
   * optimal routes (if optimized), or at minimum distances (if not optimized).
   * @return
   */
  public int remove(){
    if(verifyInit() == -1)
      return -1;
    places.remove(targetIndex);
    return 0;
  }

  /**
   * changeStartPos() simply shifts the array until places begins with the element specified at
   * index targetIndex. Requires replanning, as some distances may have not been previously
   * calculated in the distances array.
   * @return
   */

  public int changeStartPos(){
    if(verifyInit() == -1)
      return -1;
    ArrayList<Place> newPlaces = new ArrayList<>();
    newPlaces.add(places.get(targetIndex));

    for(int i = targetIndex+1; i < places.size(); ++i){
      newPlaces.add(places.get(i));
    }
    for(int i = 0; i < targetIndex; ++i){
      newPlaces.add(places.get(i));
    }
    places = newPlaces;
    return 0;
  }

  /**
   * verifyInit() checks the necessary fields are appropriately assigned values, depending on
   * the specific request.
   * @return
   */
  protected int verifyInit(){
    if(editType.equals("remove") || editType.equals("changeStartPos")){
      if(places == null || distances == null || targetIndex < 0 || targetIndex > places.size())
        return -1;
      return 0;
    }
    else if(editType.equals("reverse")){
      if(places == null || distances == null)
        return -1;
      return 0;
    }
    else{   // "insert"
      if(destIndex < 0 || newPlace == null)
        return -1;
      return 0;
    }
  }

}
