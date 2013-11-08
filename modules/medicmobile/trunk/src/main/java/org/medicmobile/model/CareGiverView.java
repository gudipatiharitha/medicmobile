package org.medicmobile.model;

// : Auto-generated Javadoc
/**
 * The Class CareGiverView.
 */
public class CareGiverView {

      /** The care giver. */
      private CareGiver careGiver;

      /** The local volunteer name. */
      private String localVolunteerName;

      /** The local volunteer phone number. */
      private String localVolunteerPhoneNumber;

      /**
       * Instantiates a new care giver view.
       * 
       * @param careGiver
       *              the care giver
       * @param localVolunteerName
       *              the local volunteer name
       * @param localVolunteerPhoneNumber
       *              the local volunteer phone number
       */
      public CareGiverView(CareGiver careGiver, String localVolunteerName,
                  String localVolunteerPhoneNumber) {
            this.careGiver = careGiver;
            this.localVolunteerName = localVolunteerName;
            this.localVolunteerPhoneNumber = localVolunteerPhoneNumber;
      }

      /**
       * Gets the care giver.
       * 
       * @return the care giver
       */
      public CareGiver getCareGiver() {
            return careGiver;
      }

      /**
       * Sets the care giver.
       * 
       * @param careGiver
       *              the new care giver
       */
      public void setCareGiver(CareGiver careGiver) {
            this.careGiver = careGiver;
      }

      /**
       * Gets the local volunteer name.
       * 
       * @return the local volunteer name
       */
      public String getLocalVolunteerName() {
            return localVolunteerName;
      }

      /**
       * Sets the local volunteer name.
       * 
       * @param localVolunteerName
       *              the new local volunteer name
       */
      public void setLocalVolunteerName(String localVolunteerName) {
            this.localVolunteerName = localVolunteerName;
      }

      /**
       * Gets the local volunteer phone number.
       * 
       * @return the local volunteer phone number
       */
      public String getLocalVolunteerPhoneNumber() {
            return localVolunteerPhoneNumber;
      }

      /**
       * Sets the local volunteer phone number.
       * 
       * @param localVolunteerPhoneNumber
       *              the new local volunteer phone number
       */
      public void setLocalVolunteerPhoneNumber(String localVolunteerPhoneNumber) {
            this.localVolunteerPhoneNumber = localVolunteerPhoneNumber;
      }

}
