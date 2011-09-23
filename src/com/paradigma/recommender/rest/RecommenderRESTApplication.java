package com.paradigma.recommender.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


/**
 * 
 * @author Alvaro Martin Fraguas - Fernando Tapia Rico
 *
 */
public class RecommenderRESTApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(UserResource.class);
    classes.add(PreferenceResource.class);
    return classes;
  }
  
}
