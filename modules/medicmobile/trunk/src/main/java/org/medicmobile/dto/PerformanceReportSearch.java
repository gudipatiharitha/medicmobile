package org.medicmobile.dto;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// : Auto-generated Javadoc
/**
 * The Class PerformanceReportSearch.
 */
public final class PerformanceReportSearch {

      /** The start date. */
      private String startDate;

      /** The end date. */
      private String endDate;

      /** The anganwadi id. */
      private String anganwadiID;

      /** The starts date. */
      private DateTime startsDate;

      /** The ends date. */
      private DateTime endsDate;

      /**
       * Gets the start date.
       * 
       * @return the start date
       */
      public String getStartDate() {
            return startDate;
      }

      /**
       * Sets the start date.
       * 
       * @param startDate
       *              the new start date
       */
      public void setStartDate(final String startDate) {
            this.startDate = startDate;
            DateTimeFormatter formatter = DateTimeFormat
                        .forPattern("dd MMM yyyy");
            setStartsDate(formatter.parseDateTime(startDate));
      }

      /**
       * Gets the end date.
       * 
       * @return the end date
       */
      public String getEndDate() {
            return endDate;
      }

      /**
       * Sets the end date.
       * 
       * @param endDate
       *              the new end date
       */
      public void setEndDate(final String endDate) {
            this.endDate = endDate;
            DateTimeFormatter formatter = DateTimeFormat
                        .forPattern("dd MMM yyyy");
            setEndsDate(formatter.parseDateTime(endDate));
      }

      /**
       * Gets the anganwadi id.
       * 
       * @return the anganwadi id
       */
      public String getAnganwadiID() {
            return anganwadiID;
      }

      /**
       * Sets the anganwadi id.
       * 
       * @param anganwadiID
       *              the new anganwadi id
       */
      public void setAnganwadiID(final String anganwadiID) {
            this.anganwadiID = anganwadiID;
      }

      /**
       * Gets the starts date.
       * 
       * @return the starts date
       */
      public DateTime getStartsDate() {
            return startsDate;
      }

      /**
       * Sets the starts date.
       * 
       * @param startsDate
       *              the new starts date
       */
      public void setStartsDate(final DateTime startsDate) {
            this.startsDate = startsDate;
            this.startsDate = startsDate.minusDays(1);
      }

      /**
       * Gets the ends date.
       * 
       * @return the ends date
       */
      public DateTime getEndsDate() {
            return endsDate;
      }

      /**
       * Sets the ends date.
       * 
       * @param endsDate
       *              the new ends date
       */
      public void setEndsDate(final DateTime endsDate) {
            this.endsDate = endsDate;
      }
}
