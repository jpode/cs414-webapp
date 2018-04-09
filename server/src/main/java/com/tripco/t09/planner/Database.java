package com.tripco.t09.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import spark.Request;

/**
 * A class that allows connection to and querying of a remote database. Relies on HTTP
 * request objects to create SQL search statements. On construction a database will
 * have a set of filters available to be used.
 * The Database class supports TFFI so it can easily be converted to/from Json by Gson.
 */

public class Database {
  private static Query query;
  // db configuration information
  private static String myDriver;
  private static String myUrl;
  private static String myUser;
  private static String myPassword;
  private static ArrayList<Filter> filters;
  private static Connection conn;

  /**
   * Constructor. Establishes connection with database and queries it for available filters.
   */
  public Database(){
    myDriver = "com.mysql.jdbc.Driver";
    myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
    myUser = "cs314-db";
    myPassword = "eiK5liet1uej";
    filters = new ArrayList<Filter>();
    try {

      // connect to the database
      Class.forName(myDriver);
      conn = DriverManager.getConnection(myUrl, myUser, myPassword);

      //queries to load available filters
      ResultSet rs = sendQuery("SELECT DISTINCT(type) FROM airports WHERE type != 'closed'");
      if(rs != null){
        addFilters(rs);
        rs.close();
      }
      rs = sendQuery("SELECT DISTINCT(country.name) FROM country INNER JOIN airports ON "
          + "country.id=airports.iso_country ORDER BY country.name;");
      if(rs != null) {
        addFilters(rs);
        rs.close();
      }
    } catch(Exception e){
      System.err.println("Exception in DB Construction: " + e.getMessage());
    }
  }

  /**
   * Process requests and generate SQL search statement from request.
   * @param request object to be processed.
   */
  public void processRequest(Request request){
    // extract the information from the body of the request and log it.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());
    System.out.println(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    query = gson.fromJson(requestBody, Query.class);

    // SQL query to be sent to database
    String search = "SELECT * FROM airports WHERE id LIKE '%" + query.query + "%' OR name LIKE '%" + query.query + "%' "
        + "OR municipality LIKE '%" + query.query + "%' OR keywords LIKE '%" + query.query + "%'";

    try {
      ResultSet rs = sendQuery(search);
      if(rs != null) {
        addPlaces(rs);
        rs.close();
      }
    } catch (Exception e){
      System.err.println("Exception in request processing: " + e.getMessage());
    }
  }

  /**
   * Searches the database with the given search.
   * @param SQL search statement.
   * @return SQL ResultSet object containing results from the query.
   */
  public ResultSet sendQuery(String search){
    System.out.println(search);
    try {
      Statement stQuery = conn.createStatement();
      ResultSet rsQuery = stQuery.executeQuery(search);
      {
        return rsQuery;
      }
    } catch (Exception e) {
      System.err.println("Exception in sending query: " + e.getMessage());
    }
    return null;
  }

  /**
   * iterate through query results and create a new place object for each row,
   * stop when all results are added or until the limit (20) is reached.
   * @param ResultSet object containing results of the SQL query.
   */
  private static void addPlaces(ResultSet queryResult) throws SQLException {

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

  /**
   * Creates a new filter object for each column of the ResultSet and adds it to the
   * global filters object.
   * @param ResultSet object containing results of the SQL query.
   */
  private static void addFilters(ResultSet queryResult) throws SQLException{

    ResultSetMetaData rsmd = queryResult.getMetaData();

    for(int i = 1; i <= rsmd.getColumnCount(); i++) {
      Filter filter = new Filter();
      filter.attribute = rsmd.getColumnName(i);
      while (queryResult.next()) {
        filter.values.add(queryResult.getString(filter.attribute));
      }
      filters.add(filter);
    }
  }

  /**
   * Get the query.
   * @return stringified JSON version of a query object.
   */
  public String getString() {
    Gson gson = new Gson();
    return gson.toJson(query);
  }

  /**
   * Get the available filters.
   * @return ArrayList of filter objects.
   */
  public ArrayList<Filter> getFilters(){
    return filters;
  }
}
