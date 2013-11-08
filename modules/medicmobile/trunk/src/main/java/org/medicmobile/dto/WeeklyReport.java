package org.medicmobile.dto;

import java.util.List;

// : Auto-generated Javadoc
/**
 * The Class WeeklyReport.
 */
public final class WeeklyReport {

      /** The anganwadi name. */
      private String anganwadiName;

      /** The report accordians. */
      private List<WeeklyReportAccordian> reportAccordians;

      /**
       * Gets the report accordians.
       * 
       * @return the report accordians
       */
      public List<WeeklyReportAccordian> getReportAccordians() {
            return reportAccordians;
      }

      /**
       * Sets the report rows.
       * 
       * @param reportRows
       *              the new report rows
       */
      public void setReportRows(final List<WeeklyReportAccordian> reportRows) {
            this.reportAccordians = reportRows;
      }

      /**
       * Gets the anganwadi name.
       * 
       * @return the anganwadi name
       */
      public String getAnganwadiName() {
            return anganwadiName;
      }

      /**
       * Sets the anganwadi name.
       * 
       * @param anganwadiName
       *              the new anganwadi name
       */
      public void setAnganwadiName(final String anganwadiName) {
            this.anganwadiName = anganwadiName;
      }

}
