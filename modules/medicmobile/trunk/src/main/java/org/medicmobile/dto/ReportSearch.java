package org.medicmobile.dto;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// : Auto-generated Javadoc
/**
 * The Class ReportSearch.
 */
public final class ReportSearch {

      /** The report date. */
      private DateTime reportDate;

      /** The anganwadi id. */
      private String anganwadiID;

      /** The local volunteer id. */
      private String localVolunteerID;

      /** The reports date. */
      private String reportsDate;

      /**
       * Gets the report date.
       * 
       * @return the report date
       */
      public DateTime getReportDate() {
            return reportDate;
      }

      /**
       * Sets the report date.
       * 
       * @param reportDate
       *              the new report date
       */
      public void setReportDate(final DateTime reportDate) {
            this.reportDate = reportDate.toDateTime(DateTimeZone.UTC);
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
       * Gets the local volunteer id.
       * 
       * @return the local volunteer id
       */
      public String getLocalVolunteerID() {
            return localVolunteerID;
      }

      /**
       * Sets the local volunteer id.
       * 
       * @param localVolunteerID
       *              the new local volunteer id
       */
      public void setLocalVolunteerID(final String localVolunteerID) {
            this.localVolunteerID = localVolunteerID;
      }

      /**
       * Gets the reports date.
       * 
       * @return the reports date
       */
      public String getReportsDate() {
            return reportsDate;
      }

      /**
       * Sets the reports date.
       * 
       * @param reportsDate
       *              the new reports date
       */
      public void setReportsDate(final String reportsDate) {
            this.reportsDate = reportsDate;
            DateTimeFormatter formatter = DateTimeFormat
                        .forPattern("dd MMM yyyy");
            reportDate = formatter.parseDateTime(reportsDate);
            reportDate = reportDate.toDateTime(DateTimeZone.UTC);
      }

}
