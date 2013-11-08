package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

/**
 * The Class Child.
 */
@TypeDiscriminator("doc.type=='Child'")
public class Child extends Person {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = -7065500994604207628L;

      /** The care giver id. */
      @JsonProperty
      private String careGiverID;

      /** The mcts id. */
      @JsonProperty
      private String mctsID;

      /** The child id. */
      @JsonProperty
      private String childID;

      /** The place of delivery. */
      @JsonProperty
      private String placeOfDelivery;

      /** The child calendar id. */
      @JsonProperty
      private String childCalendarID;

      @JsonProperty
      private String childSystemId;

      /**
       * Gets the child calendar id.
       * 
       * @return the child calendar id
       */
      public String getChildCalendarID() {
            return childCalendarID;
      }

      /**
       * Sets the child calendar id.
       * 
       * @param childCalenderID
       *              the new child calendar id
       */
      public void setChildCalendarID(String childCalenderID) {
            this.childCalendarID = childCalenderID;
      }

      /** The weight. */
      @JsonProperty
      private float weight;

      /** The height. */
      @JsonProperty
      private float height;

      /** The blood group. */
      @JsonProperty
      private BloodGroup bloodGroup;

      /**
       * Gets the care giver id.
       * 
       * @return the care giver id
       */
      public String getCareGiverID() {
            return careGiverID;
      }

      /**
       * Sets the care giver id.
       * 
       * @param careGiverID
       *              the new care giver id
       */

      public void setCareGiverID(String careGiverID) {
            this.careGiverID = careGiverID;
      }

      /**
       * Gets the child id.
       * 
       * @return the child id
       */
      public String getChildID() {
            return childID;
      }

      /**
       * Sets the child id.
       * 
       * @param childID
       *              the new child id
       */
      public void setChildID(String childID) {
            this.childID = childID;
      }

      /**
       * Gets the place of delivery.
       * 
       * @return the place of delivery
       */
      public String getPlaceOfDelivery() {
            return placeOfDelivery;
      }

      /**
       * Sets the place of delivery.
       * 
       * @param placeOfDelivery
       *              the new place of delivery
       */
      public void setPlaceOfDelivery(String placeOfDelivery) {
            this.placeOfDelivery = placeOfDelivery;
      }

      /**
       * Gets the weight.
       * 
       * @return the weight
       */
      public float getWeight() {
            return weight;
      }

      /**
       * Sets the weight.
       * 
       * @param weight
       *              the new weight
       */
      public void setWeight(float weight) {
            this.weight = weight;
      }

      /**
       * Gets the height.
       * 
       * @return the height
       */
      public float getHeight() {
            return height;
      }

      /**
       * Sets the height.
       * 
       * @param height
       *              the new height
       */
      public void setHeight(float height) {
            this.height = height;
      }

      /**
       * Gets the mcts id.
       * 
       * @return the mcts id
       */
      public String getMctsID() {
            return mctsID;
      }

      /**
       * Sets the mcts id.
       * 
       * @param mctsID
       *              the new mcts id
       */
      public void setMctsID(String mctsID) {
            this.mctsID = mctsID;
      }

      /**
       * Gets the blood group.
       * 
       * @return the blood group
       */
      public BloodGroup getBloodGroup() {
            return bloodGroup;
      }

      /**
       * Sets the blood group.
       * 
       * @param bloodGroup
       *              the new blood group
       */
      public void setBloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
      }

      public String getChildSystemId() {
            return childSystemId;
      }

      public void setChildSystemId(String childSystemId) {
            this.childSystemId = childSystemId;
      }

}
