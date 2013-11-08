package org.medicmobile.service;

import org.joda.time.DateTime;
import org.medicmobile.dto.ReportSearch;
import org.medicmobile.dto.ReportUpdates;
import org.medicmobile.dto.VisitUpdate;
import org.medicmobile.dto.WeeklyReport;
import org.motechproject.event.MotechEvent;

/**
 * The Interface WeeklyReportService.
 */
public interface WeeklyReportService {

      /**
       * Gets the weekly report.
       * 
       * @param reportSearch
       *              the report search
       * @return the weekly report
       */
      WeeklyReport getWeeklyReport(ReportSearch reportSearch);

      /**
       * Update.
       * 
       * @param visitUpdate
       *              the visit update
       * @return true, if successful
       */
      boolean update(VisitUpdate visitUpdate);

      /**
       * Update from report.
       * 
       * @param reportUpdates
       *              the report updates
       * @return true, if successful
       */
      boolean updateFromReport(ReportUpdates reportUpdates);

      /**
       * Send reminders.
       * 
       * @param event
       *              the event
       */
      void sendReminders(MotechEvent event);

      void scheduleSMSToAnganwadi(String anganwadiID, DateTime date);

      void rescheduleAppointmentDailyJob();
}
