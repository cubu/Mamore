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
	
  static @Context ServletContext servletContext;
  static String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  
  
  // Get the recommended users of a user
  @GET
  @Path(value="/{userID}/users.xml")
  @Produces(value="application/xml")
  public String getRecommendedUsersXML(@PathParam(value="userID") String userID) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    String response = XMLDeclaration + "<response></response><status>false</status>";
    try {
      response = buildRecommendedResourceResponseXML("user", recommender.recommend(userID, null, true));
    } catch (Exception e) {}
    return response;
  }
  
  @GET
  @Path(value="/{userID}/users.json")
  @Produces(value="application/json")
  public String getRecommendedUsersJSON(@PathParam(value="userID") String userID) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    String response = "{\"response\" : [], \"status\": false}";
    try {
      response = buildRecommendedResourceResponseJSON("user", recommender.recommend(userID, null, true));
    } catch (Exception e) {}
 
    return response;
  }

  
  // Get the recommended items of a user
  @GET
  @Path(value="/{userID}/items.xml")
  @Produces(value="application/xml")
  public String getRecommendedItemsXML(@PathParam(value="userID") String userID) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    String response = XMLDeclaration + "<response></response><status>false</status>";
    try {
      response = buildRecommendedResourceResponseXML("item", recommender.recommend(userID, null, false));
    } catch (Exception e) {}
    return response;
  }
  
  @GET
  @Path(value="/{userID}/items.json")
  @Produces(value="application/json")
  public String getRecommendedItemsJSON(@PathParam(value="userID") String userID) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    String response = "{\"response\" : [], \"status\": false}";
    try {
      response = buildRecommendedResourceResponseJSON("item", recommender.recommend(userID, null, false));
    } catch (Exception e) {}
    return response;
  }


  
  /*
   * ====================================================================================
   *
   *
   *                               PRIVATE METHODS
   *
   *
   * ====================================================================================
   */
  
  
  private static String buildRecommendedResourceResponseXML (String resourceName, ArrayList<ArrayList<String>> recommendations) {
    Iterator<ArrayList<String>> it = recommendations.iterator();
    String result = "";
    
    while (it.hasNext()) {
      ArrayList<String> recommendation = ((ArrayList<String>) (it.next()));
      result += "<" + resourceName + "><id>"+ recommendation.get(0) + "</id>";
      if (recommendation.size() > 1) {
    	  result += "<weight>"+ recommendation.get(1) + "</weight>";
      }
      result += "</" + resourceName + ">";
    }
    result = "<" + resourceName + "s type=\"array\">" + result + "</" + resourceName + "s>";
    return XMLDeclaration + "<root><response>" + result + "</response><status>true</status></root>";
  }
  
  private static String buildRecommendedResourceResponseJSON (String resourceName, ArrayList<ArrayList<String>> recommendations) {
    Iterator<ArrayList<String>> it = recommendations.iterator();
    String result = "";
    boolean first = true;

    while (it.hasNext()) {
      ArrayList<String> recommendation = ((ArrayList<String>) (it.next()));
      if (first) first = false;
      else result += ", ";
      result += "{\"id\" : \"" + recommendation.get(0) + "\"";
      if (recommendation.size() > 1) {
    	  result += ", \"weight\" : \""+ recommendation.get(1) + "\"";
      }
      result += '}';
    }
    return "{\"response\":{ \"" + resourceName + "s\": [" + result + "]}, \"status\" : true}";
  
  }

}
