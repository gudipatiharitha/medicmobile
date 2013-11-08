package org.medicmobile.dto;

import java.util.List;

// : Auto-generated Javadoc
/**
 * The Class WeeklyReportAccordian.
 */
public final class WeeklyReportAccordian {

      /** The local volunteer id. */
      private String localVolunteerID;

      /** The local volunteer name. */
      private String localVolunteerName;

      /** The rows. */
      private List<WeeklyReportRow> rows;

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
       * Gets the rows.
       * 
       * @return the rows
       */
      public List<WeeklyReportRow> getRows() {
            return rows;
      }

      /**
       * Sets the rows.
       * 
       * @param rows
       *              the new rows
       */
      public void setRows(final List<WeeklyReportRow> rows) {
            this.rows = rows;
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
      public void setLocalVolunteerName(final String localVolunteerName) {
            this.localVolunteerName = localVolunteerName;
      }

}
