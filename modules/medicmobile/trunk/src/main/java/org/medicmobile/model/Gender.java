package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum Gender.
 */
public enum Gender {

      /** The male. */
      MALE("Male"), FEMALE("Female");

      /**
       * Instantiates a new gender.
       * 
       * @param value
       *              the value
       */
      Gender(String value) {
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
