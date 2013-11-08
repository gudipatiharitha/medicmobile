package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum Caste.
 */
public enum Caste {

      /** The sheduled caste. */
      SHEDULED_CASTE("SC"),
      /** The scheduled tribe. */
      SCHEDULED_TRIBE("ST"),
      /** The other backward class. */
      OTHER_BACKWARD_CLASS("OBC"),
      /** The other caste. */
      OTHER_CASTE("Others");

      /**
       * Instantiates a new caste.
       * 
       * @param value
       *              the value
       */
      Caste(String value) {
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
