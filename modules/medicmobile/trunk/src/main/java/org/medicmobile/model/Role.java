package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Enum Role.
 */
public enum Role {

      /** The auxiliary nursing mother. */
      AUXILIARY_NURSING_MOTHER("ANM"), ANGANWADI_WORKER("AWW"), LOCAL_VOLUNTEER(
                  "Local Volunteer");

      /**
       * Instantiates a new role.
       * 
       * @param value
       *              the value
       */
      Role(String value) {
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
