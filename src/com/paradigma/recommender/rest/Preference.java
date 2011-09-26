package com.paradigma.recommender.rest;

import java.util.List;
import java.util.ArrayList;

public class Preference {

  private String userID;
  private String itemID;
  private String weight;
  static String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  
  public Preference (String userID, String itemID, String weight) {
    this.userID = userID;
    this.itemID = itemID;
    this.weight = weight;
  }
  
  public String toString(boolean isXML) {
    String output = "";
    if (isXML) {
      output += "<user_id>" + this.userID + "</user_id><items type=\"array\">";
      output += "<item><id>" + this.itemID + "</id><weight>" + this.weight + "</weight></item>";
      output += "</items>";
    } else {
      output += "\"user_id\":\"" + this.userID + "\",\"items\" : [\n";
      output += "{\"id\":\"" + this.itemID + "\",\"weight\":\"" + this.weight + "\"}";
      output += "  ]";
    }
    return output;
  }

}
