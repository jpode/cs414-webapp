package com.tripco.t09.planner;

/**
 * Describes the places to visit in a trip in TFFI format.
 * There may be other attributes of a place, but these are required to plan a trip.
 */
public class Place {

  public String id;
  public String name;
  public String latitude;
  public String longitude;

  public double numLatitude;
  public double numLongitude;

  /**
   * Takes a single string coordinate of various types, if the coordinate is already in
   *  proper decimal format it will simply return. Otherwise the method will call
   *   stringToCoordinate to convert the coordinate.
   * @params String containing the coordinate
   * @return double containing the string coordinate converted to decimal coordinate
   */
  public double convertCoordinate(String value) {
    double result;

    try {
      result = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      /*Coordinate is not already in number format*/
      result = stringToCoordinate(value);
    }

  return result;

  }

  /**
   * Takes a single string coordinate of various types and converts it to a decimal value.
   *  Assumes that the string is of a correct coordinate input type.
   * @params String containing the coordinate
   * @return double containing the string coordinate converted to decimal coordinate
   */
  public double stringToCoordinate(String value){
    String coordinate;
    double degrees = 0;
    double minutes = 0;
    double seconds = 0;
    double result;


    //Replaces all degrees, minutes, and seconds symbols with empty spaces to allow the
    // string to be parsed into double values.

    coordinate = value.replaceAll("[°\'\"′″]", " " );

    int counter = 0;
    int last = 0;

    // Parses the new string and reads in the values of the coordinates
    for(int i = 0; i < coordinate.length(); i++){
      if(coordinate.charAt(i) == ' ' && last != i){
        if(counter == 0) {
          degrees = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        } else if(counter == 1){
          minutes = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        } else if(counter == 2){
          seconds = Double.parseDouble(coordinate.substring(last, i));
          last = i+1;
          counter++;
        }
      }
    }

    // Checks to see if the value should be negated
    if(coordinate.indexOf('S') != -1 || coordinate.indexOf('W') != -1){
      return -1 * (double)Math.round((degrees + (minutes / 60) + (seconds / 3600)) * 100000d) / 100000d;
    }

    return (double)Math.round((degrees + (minutes / 60) + (seconds / 3600)) * 100000d) / 100000d;
  }

  /**
   * Takes a single decimal coordinate and checks to see if it is within the 102.05W and 109.05W boundaries
   *  of the Colorado eastern and western border
   * @params Double containing the coordinate
   * @return boolean indicating if the coordinate is within the boundaries.
   *  true = within boundaries, false = outside of boundaries
   */
  public boolean verifyLatitudeCoordinates(double coordinate){
    if(coordinate < -102.05 && coordinate > -109.05){
      return true;
    }
    return false;
  }

  // Same as verifyLatitudeCoordinates, but takes longitudinal coordinates and tests
  //  them against the south and north Colorado borders at 37N and 41N
  public boolean verifyLongitudeCoordinates(double coordinate){
    if(coordinate > 37 && coordinate < 41){
      return true;
    }
    return false;
  }

}

