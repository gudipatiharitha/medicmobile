package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum BloodGroup.
 */
public enum BloodGroup {

      /** The O_ positive. */
      O_Positive("O+"),

      /** The O_ negative. */
      O_Negative("O-"),

      /** The A_ positive. */
      A_Positive("A+"),

      /** The A_ negative. */
      A_Negative("A-"),

      /** The A b_ positive. */
      AB_Positive("AB+"),

      /** The A b_ negative. */
      AB_Negative("AB-"),

      /** The B_ positive. */
      B_Positive("B+"),

      /** The B_ negative. */
      B_Negative("B-");

      /**
       * Instantiates a new blood group.
       * 
       * @param value
       *              the value
       */
      BloodGroup(String value) {
            this.value = value;
      }

      /** The value. */
      private final String value;

      /**
       * Gets the value.
       * 
       * @return the value
       */
      public String getValue() {
            return value;
      }
}
