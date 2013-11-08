package org.medicmobile.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

// : Auto-generated Javadoc
/**
 * The Class SideEffects.
 */
public class SideEffects {

      /** The side effects. */
      @JsonProperty
      private Map<Integer, String> sideEffects;

      /**
       * Gets the side effects.
       * 
       * @return the side effects
       */
      public Map<Integer, String> getSideEffects() {
            return sideEffects;
      }

      /**
       * Sets the side effects.
       * 
       * @param sideEffects
       *              the side effects
       */
      public void setSideEffects(Map<Integer, String> sideEffects) {
            this.sideEffects = sideEffects;
      }

}
