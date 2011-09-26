package com.paradigma.recommender.rest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import javax.servlet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paradigma.recommender.GeneralRecommender;
import com.paradigma.recommender.db.MongoDBDataModel;


/**
 * 
 * @author Alvaro Martin Fraguas - Fernando Tapia Rico - Julio Manuel Gonzalez Rodriguez 
 *
 */
@Path(value="/users")
public class UserResource {

  private static final Logger log = LoggerFactory.getLogger(MongoDBDataModel.class);

  static @Context ServletContext servletContext;
  static String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  // Get the recommended users of a user
  @GET
  @Path(value="/{userID}/users.xml")
  @Produces(value="application/xml")
  public String getRecommendedUsersXML(@PathParam(value="userID") String userID) {
    return buildRecommendedResourceResponse(userID, true, true, servletContext);
  }
  
  @GET
  @Path(value="/{userID}/users.json")
  @Produces(value="application/json")
  public String getRecommendedUsersJSON(@PathParam(value="userID") String userID) {
    return buildRecommendedResourceResponse(userID, false, true, servletContext);
  }
  
  // Get the recommended items of a user
  @GET
  @Path(value="/{userID}/items.xml")
  @Produces(value="application/xml")
  public String getRecommendedItemsXML(@PathParam(value="userID") String userID) {
    return buildRecommendedResourceResponse(userID, true, false, servletContext);
  }
  
  @GET
  @Path(value="/{userID}/items.json")
  @Produces(value="application/json")
  public String getRecommendedItemsJSON(@PathParam(value="userID") String userID) {
    return buildRecommendedResourceResponse(userID, false, false, servletContext);
  }
  
  private static String buildRecommendedResourceResponse (String userID, boolean isXML, boolean isUsers, ServletContext servletContext) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    boolean error = false;
    String result = "";
    try {
      Iterator<ArrayList<String>> it = recommender.recommend(userID, null, isUsers).iterator();
      boolean first = true;
      while (it.hasNext()) {
        if (first) first = false;
        else if (!isXML) result += ",";
        ArrayList<String> recommendation = ((ArrayList<String>) (it.next()));
        if (isXML) result += "<" + (isUsers ? "user" : "item") + "><id>" + recommendation.get(0) + "</id>";
        else result += "{\"id\":\"" + recommendation.get(0) + "\"";
        if (recommendation.size() > 1) {
          if (isXML) result += "<weight>"+ recommendation.get(1) + "</weight>";
          else result += ",\"weight\":\""+ recommendation.get(1) + "\"";
        }
        if (isXML) result += "</" + (isUsers ? "user" : "item") + ">";
        else result += "}";
      }
      if (isXML) result = "<user_id>" + userID + "</user_id><" + (isUsers ? "users" : "items") + " type=\"array\">" + result + "</" + (isUsers ? "users" : "items") + ">";
      else result = "\"user_id\":\"" + userID +"\",\"" + (isUsers ? "users" : "items") + "\": [" + result + "]";
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    if (isXML) {
      return XMLDeclaration + "<root><response>" + result + "</response><status>" + (error ? "false" : "true") + "</status></root>";
    } else {
      return "{\"response\" : {" + result + "}, \"status\":" + (error ? "false" : "true")  + "}";
    }
  }

}
