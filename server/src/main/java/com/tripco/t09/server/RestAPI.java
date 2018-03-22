package com.tripco.t09.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import spark.Request;

/**
 *
 */
public abstract class RestAPI {

  protected Object object;

  /**
   *
   * @param request
   * @param objectClass
   */
  protected RestAPI(Request request, Class objectClass) {

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    object = gson.fromJson(requestBody, objectClass);
  }

  /**
   *
   */
  public String toString() {

    // convert the object to a Json string.
    Gson gson = new Gson();
    return gson.toJson(object);
  }

}
