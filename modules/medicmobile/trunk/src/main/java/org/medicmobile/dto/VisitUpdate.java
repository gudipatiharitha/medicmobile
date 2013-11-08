package org.medicmobile.dto;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// : Auto-generated Javadoc
/**
 * The Class VisitUpdate.
 */
public final class VisitUpdate {

      /** The vaccine id. */
      private String vaccineID;

      /** The visited date. */
      private DateTime visitedDate;

      /** The visit date. */
      private String visitDate;

      /** The person name. */
      private String personName;

      /**
       * Gets the vaccine id.
       * 
       * @return the vaccine id
       */
      public String getVaccineID() {
            return vaccineID;
      }

      /**
       * Sets the vaccine id.
       * 
       * @param vaccineID
       *              the new vaccine id
       */
      public void setVaccineID(final String vaccineID) {
            this.vaccineID = vaccineID;
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
            if (visitDate != "") {
                  DateTimeFormatter formatter = DateTimeFormat
                              .forPattern("dd MMM yyyy");
                  visitedDate = formatter.parseDateTime(visitDate);
            }
      }

      /**
       * Gets the person name.
       * 
       * @return the person name
       */
      public String getPersonName() {
            return personName;
      }

      /**
       * Sets the person name.
       * 
       * @param personName
       *              the new person name
       */
      public void setPersonName(final String personName) {
            this.personName = personName;
      }
}
