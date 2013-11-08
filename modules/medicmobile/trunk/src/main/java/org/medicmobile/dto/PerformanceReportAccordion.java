package org.medicmobile.dto;

import java.util.List;

// : Auto-generated Javadoc
/**
 * The Class PerformanceReportAccordion.
 */
public final class PerformanceReportAccordion {

      /** The name. */
      private String name;

      /** The vaccines. */
      private List<PerformanceReportVaccine> vaccines;

      /** The nos updates. */
      private int nosUpdates;

      /** The nos updates successful. */
      private int nosUpdatesSuccessful;

      /** The nos updates unsuccessful. */
      private int nosUpdatesUnsuccessful;

      /** The nos sms received. */
      private int nosSMSReceived;

      /**
       * Gets the name.
       * 
       * @return the name
       */
      public String getName() {
            return name;
      }

      /**
       * Sets the name.
       * 
       * @param name
       *              the new name
       */
      public void setName(final String name) {
            this.name = name;
      }

      /**
       * Gets the nos updates.
       * 
       * @return the nos updates
       */
      public int getNosUpdates() {
            return nosUpdates;
      }

      /**
       * Sets the nos updates.
       * 
       * @param nosUpdates
       *              the new nos updates
       */
      public void setNosUpdates(final int nosUpdates) {
            this.nosUpdates = nosUpdates;
      }

      /**
       * Gets the nos updates successful.
       * 
       * @return the nos updates successful
       */
      public int getNosUpdatesSuccessful() {
            return nosUpdatesSuccessful;
      }

      /**
       * Sets the nos updates successful.
       * 
       * @param nosUpdatesSuccessful
       *              the new nos updates successful
       */
      public void setNosUpdatesSuccessful(final int nosUpdatesSuccessful) {
            this.nosUpdatesSuccessful = nosUpdatesSuccessful;
      }

      /**
       * Gets the nos updates unsuccessful.
       * 
       * @return the nos updates unsuccessful
       */
      public int getNosUpdatesUnsuccessful() {
            return nosUpdatesUnsuccessful;
      }

      /**
       * Sets the nos updates unsuccessful.
       * 
       * @param nosUpdatesUnsuccessful
       *              the new nos updates unsuccessful
       */
      public void setNosUpdatesUnsuccessful(final int nosUpdatesUnsuccessful) {
            this.nosUpdatesUnsuccessful = nosUpdatesUnsuccessful;
      }

      /**
       * Gets the nos sms received.
       * 
       * @return the nos sms received
       */
      public int getNosSMSReceived() {
            return nosSMSReceived;
      }

      /**
       * Sets the nos sms received.
       * 
       * @param nosSMSReceived
       *              the new nos sms received
       */
      public void setNosSMSReceived(final int nosSMSReceived) {
            this.nosSMSReceived = nosSMSReceived;
      }

      /**
       * Gets the vaccines.
       * 
       * @return the vaccines
       */
      public List<PerformanceReportVaccine> getVaccines() {
            return vaccines;
      }

      /**
       * Sets the vaccines.
       * 
       * @param vaccines
       *              the new vaccines
       */
      public void setVaccines(final List<PerformanceReportVaccine> vaccines) {
            this.vaccines = vaccines;
      }
}
