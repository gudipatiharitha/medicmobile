package org.medicmobile.model;

/**
 * The Enum Language.
 */
public enum Language {

      /** The halbi. */
      HALBI("Halbi"), BHATRI("Bhatri"), HINDI("Hindi"), OTHER("Other"), ENGLISH(
                  "English");

      /**
       * Instantiates a new language.
       * 
       * @param value
       *              the value
       */
      Language(String value) {

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
