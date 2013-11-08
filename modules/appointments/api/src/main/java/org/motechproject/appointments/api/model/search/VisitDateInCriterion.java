package org.motechproject.appointments.api.model.search;

import static org.motechproject.commons.date.util.DateUtil.inRange;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.motechproject.appointments.api.repository.AllAppointmentCalendars;
import org.motechproject.appointments.api.service.contract.VisitResponse;

public class VisitDateInCriterion implements Criterion {

        private DateTime start;
        private DateTime end;

        public VisitDateInCriterion(DateTime start, DateTime end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public List<VisitResponse> filter(List<VisitResponse> visits) {
        List<VisitResponse> filteredVisits = new ArrayList<VisitResponse>();
        for (VisitResponse visitResponse : visits) {
            DateTime visitDate = visitResponse.getVisitDate();
            if (inRange(visitDate, start, end)) {
               filteredVisits.add(visitResponse);
            }
        }
          return filteredVisits;
        }

        @Override
        public List<VisitResponse> fetch(AllAppointmentCalendars allAppointmentCalendars) {
            return allAppointmentCalendars.findVisitsWithVisitDateInRange(start, end);
        }

}
