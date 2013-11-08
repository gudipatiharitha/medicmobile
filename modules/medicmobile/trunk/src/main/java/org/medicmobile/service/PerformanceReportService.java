package org.medicmobile.service;

import org.medicmobile.dto.PerformanceReport;
import org.medicmobile.dto.PerformanceReportSearch;

// : Auto-generated Javadoc
/**
 * The Interface PerformanceReportService.
 */
public interface PerformanceReportService {

      /**
       * Gets the performance report.
       * 
       * @param performanceReportSearch
       *              the performance report search
       * @return the performance report
       */
      PerformanceReport getPerformanceReport(
                  PerformanceReportSearch performanceReportSearch);
}
