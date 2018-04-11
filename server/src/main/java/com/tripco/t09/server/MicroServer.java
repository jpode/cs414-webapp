package com.tripco.t09.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.tripco.t09.planner.Config;
import com.tripco.t09.planner.Database;
import com.tripco.t09.planner.Editor;
import com.tripco.t09.planner.Plan;

import com.tripco.t09.planner.Trip;
import spark.Request;
import spark.Response;
import spark.Spark;
import static spark.Spark.*;

import java.util.Arrays;


/** A simple micro-server for the web.  Just what we need, nothing more.
 *
 */
public class MicroServer {

  private int    port;
  private String name;
  private String path = "/public";

  private String[] opts = new String[3];

  /** Creates a micro-server to load static files and provide REST APIs.
   *
   * @param port
   * @param name
   */
  MicroServer(int port, String name) {
    this.port = port;
    this.name = name;

    port(this.port);

    // serve the static files: index.html and bundle.js
    Spark.staticFileLocation(this.path);
    get("/", (req, res) -> {res.redirect("index.html"); return null;});

    // register all micro-services and the function that services them.
    // start with HTTP GET
    get("/about", this::about);
    get("/echo", this::echo);
    get("/hello/:name", this::hello);
    get("/team", this::team);
    get("/config", this::config);
    // client is sending data, so a HTTP POST is used instead of a GET
    post("/plan", this::plan);
    post("/optimize", this::optimize);
    post("/query", this::query);
    post("/edit", this::edit);

    System.out.println("\n\nServer running on port: " + this.port + "\n\n");
  }

  /** A REST API that describes the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String about(Request request, Response response) {

    response.type("text/html");

    return "<html><head></head><body><h1>"+name+" Micro-server on port "+port+"</h1></body></html>";
  }

  /** A REST API that echos the client request.
   *
   * @param request
   * @param response
   * @return
   */
  private String echo(Request request, Response response) {

    response.type("application/json");

    return HTTP.echoRequest(request);
  }

  /** A REST API demonstrating the use of a parameter.
   *
   * @param request
   * @param response
   * @return
   */
  private String hello(Request request, Response response) {

    response.type("text/html");

    return Greeting.html(request.params(":name"));
  }

  /** A REST API that describes the server capabilities.
   *
   * @param request
   * @param response
   * @return
   */
  private String config(Request request, Response response) {

    response.type("application/json");

    Config cfg = new Config();
    return cfg.getConfig();
  }

  /** A REST API to support trip planning.
   *
   * @param request
   * @param response
   * @return
   */
  private String plan(Request request, Response response) {

    response.type("application/json");
    //Clear the stored optimizations
    Arrays.fill(opts, null);

    Plan plan = new Plan(request);
    System.out.println("Planning trip");
    plan.planTrip();
    System.out.println("Trip planned");
    opts[0] = plan.getTripNoOpt();

    if(getOptLvl(plan) > 0){
      plan.optimize();
      opts[getOptLvl(plan)] = plan.getTrip();
    }

    response.body(plan.getTrip());
    response.status(plan.getStatus());

    return plan.getTrip();
  }

  /** A REST API that returns the team information associated with the server.
   *
   * @param request
   * @param response
   * @return
   */
  private String team(Request request, Response response) {

    response.type("text/plain");

    return name;
  }

  /** A REST API that returns an optimized order of the trip
   *  The result of the optimization is cached until the
   *  file is changed. This allows quick switching between
   *  optimizations.
   * @param request
   * @param response
   * @return
   */

  private String optimize(Request request, Response response) {
    response.type("application/json");
    String result = "";
    int optLvl;
    Plan plan = new Plan(request);
    optLvl = getOptLvl(plan);

    System.out.println("Optimizing with optLvl " + optLvl);
    if(opts[optLvl] != null){
      System.out.println("Retrieving optimization from cache");
      result = opts[optLvl];
    } else {
      System.out.println("No cached optimization.");
      plan.optimize();
      result = plan.getTrip();
      opts[optLvl] = result;
    }

    return result;
  }

  /**
   * A REST API to query the database.
   * @param request
   * @param response
   * @return
   */
  private String query(Request request, Response response) {
    System.out.println("Query: " + request.body());
    Database db = new Database();
    db.processRequest(request);
    response.type("application/json");
    return db.getString();
  }

  /** A REST API to support user editing of trip.
   *
   * @param request
   * @param response
   * @return
   */
  private String edit(Request request, Response response) {

    response.type("application/json");

    //Print the request
    System.out.println(HTTP.echoRequest(request));

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());
    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    Editor editor = gson.fromJson(requestBody, Editor.class);

    boolean isInsert = editor.editType.equals("insert");
    boolean isRemove = editor.editType.equals("remove");
    boolean isChangeStartPos = editor.editType.equals("newStart");
    boolean isMovePlace = editor.editType.equals("movePlace");

    if(editMethod(editor, isInsert, isRemove, isChangeStartPos, isMovePlace) == -1){
      response.status(400);
      return gson.toJson(editor);
    }

    //Edit the stored optimizations, if there are any
    if(editOpts(editor, isInsert, isRemove, isChangeStartPos, isMovePlace)) {
      //Return the optimization that the client was currently displaying
      return opts[getOptLvl(editor.optimization)];
    } else {
      //No stored optimizations: return the trip as is
      Trip trip = new Trip();
      trip.places = editor.places;
      trip.distances = editor.distances;
      Plan plan = new Plan(trip);
      if(editor.editType.equals("insert") || editor.editType.equals("remove")) {  // only two that needs to be replanned
        plan.planTrip();
      }
      return plan.getTrip();
    }
  }

  private int editMethod(Editor editor, boolean isInsert, boolean isRemove,
      boolean isChangeStartPos, boolean isMovePlace){
    int code = 0;
    if(isInsert){
      code = editor.insert();
    } else if(isRemove){
      code = editor.remove();
    } else if(isChangeStartPos){
      code = editor.changeStartPos();
    } else if(isMovePlace){
      code = editor.movePlace();
    } else {
      code = editor.reverse();
    }
    return code;
  }

  private boolean editOpts(Editor editor, boolean isInsert, boolean isRemove,
      boolean isChangeStartPos, boolean isMovePlace){

    Gson gson = new Gson();
    boolean hasOpt = false;
    for(int i = 0; i < 3; i++){
      if(opts[i] != null && !opts[i].equals("")){
        System.out.println("Has " + i + "-opt");
        hasOpt = true;
        Trip trip = gson.fromJson(opts[i], Trip.class);
        System.out.println("OptLevel: " + trip.options.optimization);
        Plan plan;
        if(isInsert || isRemove || isChangeStartPos || isMovePlace){
          trip.places = editor.places;
          trip.distances.clear();
          plan = new Plan(trip);
          plan.planTrip();
          if(i > 0) {
            plan.optimize();
          }
          opts[i] = plan.getTrip();
        } else {
          trip.places = editor.places;
          trip.distances = editor.distances;
          plan = new Plan(trip);
          opts[i] = plan.getTrip();
        }
      }
    }
    return hasOpt;
  }

  /**
   * Converts the Double optimization value from the slider to a more usable integer
   * this will need to be edited to account for third optimization level, if original (unoptimized)
   * is to be saved as well.
   */
  private int getOptLvl(Plan plan){
    return getOptHelper(plan.optimizationLevel());
  }

  private int getOptLvl(String optimizationLevel){
    double optDouble;
    try {
      optDouble = Double.parseDouble(optimizationLevel);
    } catch (NumberFormatException e) {
      optDouble = 0.0;
    }
    return getOptHelper(optDouble);
  }

  private int getOptHelper(double optDouble){
    int optLvl;
    if(optDouble == 0){
      optLvl = 0;
    } else if(optDouble < 1){
      optLvl = 1;
    } else {
      optLvl = 2;
    }
    return optLvl;
  }
}