package com.paradigma.recommender.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.servlet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.paradigma.recommender.GeneralRecommender;
import com.paradigma.recommender.db.MongoDBDataModel;
import com.paradigma.recommender.rest.Preference;


/**
 * 
 * @author Alvaro Martin Fraguas - Fernando Tapia Rico - Julio Manuel Gonzalez Rodriguez
 *
 */
@Path(value="/preferences")
public class PreferenceResource {

  // Logger
  private static final Logger log = LoggerFactory.getLogger(MongoDBDataModel.class);

  static @Context ServletContext servletContext;
  static String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  
  //Create a preference of a user
  @POST
  @Path(value="/{userID}/{itemID}/{weight}.xml")
  @Produces(value="application/xml")
  public String createPreferenceXML(@PathParam(value="userID") String userID, @PathParam(value="itemID") String itemID, @PathParam(value="weight") String weight) {
    return refreshPreference(userID, itemID, weight, true, true, servletContext);
  }
  
  @POST
  @Path(value="/{userID}/{itemID}/{weight}.json")
  @Produces(value="application/json")
  public String createPreferenceJSON(@PathParam(value="userID") String userID, @PathParam(value="itemID") String itemID, @PathParam(value="weight") String weight) {
    return refreshPreference(userID, itemID, weight, false, true, servletContext);
  }
  
  // Refresh preferences
  @PUT
  public void refreshPreferences() {
    log.info("Refresh REST");
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    try {
      recommender.refresh(null);
    } catch (Exception e) {
      log.info("Bad refresh");
    }
  }
  
  // Delete a preference of a user
  @DELETE
  @Path(value="/{userID}/{itemID}.xml")
  @Produces(value="application/xml")
  public String deletePreferenceXML(@PathParam(value="userID") String userID, @PathParam(value="itemID") String itemID) {
    return refreshPreference(userID, itemID, "", true, false, servletContext);
  }
  
  //Delete a preference of a user
  @DELETE
  @Path(value="/{userID}/{itemID}.json")
  @Produces(value="application/json")
  public String deletePreference(@PathParam(value="userID") String userID, @PathParam(value="itemID") String itemID) {
    return refreshPreference(userID, itemID, "", false, false, servletContext);
  }

  // Need to pass the servletContext as an argument to be able to call this method from other resource classes
  private static String refreshPreference (String userID, String itemID, String weight, boolean isXML, boolean add, ServletContext servletContext) {
    GeneralRecommender recommender = (GeneralRecommender) servletContext.getAttribute("recommender");
    ArrayList<List<String>> items = new ArrayList<List<String>>();
    ArrayList<String> tuple = new ArrayList<String>();
    tuple.add(itemID);
    tuple.add(weight);
    items.add(tuple);
    Preference userPref = new Preference(userID, itemID, weight);
    boolean error = false;
    try {
      recommender.refreshData(userID, items, add);
    } catch (Exception e) {
      log.info(e.getMessage());
      error = true;
    }
    if (isXML) {
      return XMLDeclaration + "<root><response>" + (error ? "" : userPref.toString(true)) + "</response><status>" + (error ? "false" : "true") + "</status></root>";
    } else {
      return "{\"response\" : {" + (error ? "" : userPref.toString(false)) + "}, \"status\":" + (error ? "false" : "true")  + "}";
    }
  }
  
}
