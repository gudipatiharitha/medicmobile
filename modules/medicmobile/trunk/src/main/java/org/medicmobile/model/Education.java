package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum Education.
 */
public enum Education {

      /** The primary education. */
      PRIMARY_EDUCATION("Primary Education"),
      /** The secondary education. */
      SECONDARY_EDUCATION("Secondary Education"),
      /** The diploma. */
      DIPLOMA("Diploma"),
      /** The bachelors. */
      BACHELORS("Bachelors"),
      /** The masters. */
      MASTERS("Masters"),
      /** The none. */
      NONE("None");

      /**
       * Instantiates a new education.
       * 
       * @param value
       *              the value
       */
      Education(String value) {
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
