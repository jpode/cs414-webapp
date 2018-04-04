package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import spark.Request;

/**
 * The Database class supports TFFI so it can easily be converted to/from Json by Gson.
 */

public class Database {
  // The variables in this class should reflect TFFI.
  private static Query query;
  // db configuration information
  private static final String myDriver = "com.mysql.jdbc.Driver";
  private static final String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
  // SQL queries to retrieve the data
  private static String search = "";


  /**
   * Arguments contain the username and password for the database.
   */
  public Database(Request request){
    try {
      // extract the information from the body of the request and log it.
      JsonParser jsonParser = new JsonParser();
      JsonElement requestBody = jsonParser.parse(request.body());
      System.out.println(request.body());

      // convert the body of the request to a Java class.
      Gson gson = new Gson();
      query = gson.fromJson(requestBody, Query.class);

      System.out.println("DB Received: " + gson.toJson(query));

      search = "SELECT * FROM airports WHERE name LIKE '%" + query.query + "%'";
      System.out.println(search);

      Class.forName(myDriver);
      // connect to the database and query
      try (Connection conn = DriverManager.getConnection(myUrl, "cs314-db", "eiK5liet1uej");
          Statement stQuery = conn.createStatement();
          ResultSet rsQuery = stQuery.executeQuery(search)
      ) {
        addPlaces(rsQuery);
      }
    } catch (Exception e) {
      System.err.println("Exception: " + e.getMessage());
    }
  }


  
  private static void addPlaces(ResultSet queryResult) throws SQLException {
    // iterate through query results, stop when all results are added or until the limit (20) is reached
    int counter = 0;

    while (queryResult.next() && counter < 20) {
      Place place = new Place();
      place.name = queryResult.getString("name");
      place.id = queryResult.getString("id");
      place.municipality = queryResult.getString("municipality");
      place.longitude = queryResult.getString("longitude");
      place.latitude = queryResult.getString("latitude");
      query.places.add(place);
      counter++;
    }
  }

  public String getString() {
      Gson gson = new Gson();
      return gson.toJson(query);
  }
}
