package com.tripco.t09.server;

import com.tripco.t09.planner.Plan;

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
    // client is sending data, so a HTTP POST is used instead of a GET
    post("/plan", this::plan);

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
    plan.planTrip();

    return (plan.getTrip());
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

  /** A REST API that returns an optimized order of trips
   *  The result of the optimization is stored until the
   *  file is changed. This allows quick switching between
   *  optimizations.
   * @param request
   * @param response
   * @return
   */
  private String optimize(Request request, Response response) {
    response.type("application/json");
    String result = "";

    Plan plan = new Plan(request);
    double optDouble;
    int optLvl;

    optDouble = plan.optimizationLevel();

    if(optDouble == 0){
      optLvl = 0;
    } else if(optDouble < .5){
      optLvl = 1;
    } else {
      optLvl = 2;
    }

    if(opts[optLvl] != null){
      result = opts[optLvl];
    } else {
      plan.planTrip();
      result = plan.getTrip();
      opts[optLvl] = result;
    }

    return result;
  }
}