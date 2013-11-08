package org.medicmobile.dto;

import java.util.List;

import org.medicmobile.model.AnganwadiCenter;

/**
 * The Class PerformanceReport.
 */
public final class PerformanceReport {

      /** The report accordions. */
      private List<PerformanceReportAccordion> reportAccordions;

      /** The anganwadi center. */
      private AnganwadiCenter anganwadiCenter;

      private PerformanceReportAccordion anganwadiCenterReportAccodion;

      /**
       * Gets the report accordions.
       * 
       * @return the report accordions
       */
      public List<PerformanceReportAccordion> getReportAccordions() {
            return reportAccordions;
      }

      /**
       * Sets the report accordions.
       * 
       * @param reportAccordions
       *              the new report accordions
       */
      public void setReportAccordions(
                  final List<PerformanceReportAccordion> reportAccordions) {
            this.reportAccordions = reportAccordions;
      }

      /**
       * Gets the anganwadi center.
       * 
       * @return the anganwadi center
       */
      public AnganwadiCenter getAnganwadiCenter() {
            return anganwadiCenter;
      }

      /**
       * Sets the anganwadi center.
       * 
       * @param anganwadiCenter
       *              the new anganwadi center
       */
      public void setAnganwadiCenter(final AnganwadiCenter anganwadiCenter) {
            this.anganwadiCenter = anganwadiCenter;
      }

      /**
       * gets the performance data for anganwadi center
       * 
       * @return the anganwadiCenterReportAccodion
       */
      public PerformanceReportAccordion getAnganwadiCenterReportAccodion() {
            return anganwadiCenterReportAccodion;
      }

      /**
       * sets the performance data for anganwadi cenetr
       * 
       * @param anganwadiCenterReportAccodion
       *              the anganwadiCenterReportAccodion to set
       */
      public void setAnganwadiCenterReportAccodion(
                  PerformanceReportAccordion anganwadiCenterReportAccodion) {
            this.anganwadiCenterReportAccodion = anganwadiCenterReportAccodion;
      }

}
