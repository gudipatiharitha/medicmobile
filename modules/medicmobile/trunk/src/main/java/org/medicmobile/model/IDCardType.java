package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum IDCardType.
 */
public enum IDCardType {

      /** The aadhar card. */
      AADHAR_CARD("Aadhar Card"), RATION_CARD("Ration Card"), VOTER_ID(
                  "Voter ID");

      /**
       * Instantiates a new iD card type.
       * 
       * @param value
       *              the value
       */
      IDCardType(String value) {
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
