/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medicmobile.dto;

import java.util.List;
import org.joda.time.DateTime;

// : Auto-generated Javadoc
/**
 * The Class ReportUpdates.
 * 
 * @author root
 */
public final class ReportUpdates {

      /** The updates. */
      private List<String> updates;

      /** The visit date. */
      private String visitDate;

      /** The visited date. */
      private DateTime visitedDate;

      /**
       * Gets the visit date.
       * 
       * @return the visit date
       */
      public String getVisitDate() {
            return visitDate;
      }

      /**
       * Sets the visit date.
       * 
       * @param visitDate
       *              the new visit date
       */
      public void setVisitDate(final String visitDate) {
            this.visitDate = visitDate;
      }

      /**
       * Gets the visited date.
       * 
       * @return the visited date
       */
      public DateTime getVisitedDate() {
            return visitedDate;
      }

      /**
       * Sets the visited date.
       * 
       * @param visitedDate
       *              the new visited date
       */
      public void setVisitedDate(final DateTime visitedDate) {
            this.visitedDate = visitedDate;
      }

      /**
       * Gets the updates.
       * 
       * @return the updates
       */
      public List<String> getUpdates() {
            return updates;
      }

      /**
       * Sets the updates.
       * 
       * @param updates
       *              the new updates
       */
      public void setUpdates(final List<String> updates) {
            this.updates = updates;
      }

}
