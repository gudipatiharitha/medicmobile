package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medicmobile.dto.PerformanceReport;
import org.medicmobile.dto.PerformanceReportAccordion;
import org.medicmobile.dto.PerformanceReportSearch;
import org.medicmobile.dto.PerformanceReportVaccine;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.InboundSMSAdditionalRepository;
import org.medicmobile.service.AnganwadiCenterService;
import org.medicmobile.service.PerformanceReportService;
import org.medicmobile.service.StaffService;
import org.medicmobile.util.MedicMobileConstants;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.motechproject.sms.http.repository.AllOutboundSMSInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class PerformanceReportServiceImpl.
 */
@Service
public class PerformanceReportServiceImpl implements PerformanceReportService {

      /** The appointment service. */
      @Autowired
      private AppointmentService appointmentService;

      /** The staff service. */
      @Autowired
      private StaffService staffService;

      /** The inbound sms repo. */
      @Autowired
      private InboundSMSAdditionalRepository inboundSmsRepo;

      /** The outbound sms repo. */
      @Autowired
      private AllOutboundSMSInterface outboundSmsRepo;

      /** The anganwadi center service. */
      @Autowired
      private AnganwadiCenterService anganwadiCenterService;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.PerformanceReportService#getPerformanceReport
       * (org.medicmobile.dto.PerformanceReportSearch)
       */
      @Override
      public PerformanceReport getPerformanceReport(
                  PerformanceReportSearch performanceReportSearch) {
            List<Staff> localVolunteerList = staffService
                        .findByRoleAndAnganwadiID(Role.LOCAL_VOLUNTEER,
                                    performanceReportSearch.getAnganwadiID());
            PerformanceReport performanceReport = new PerformanceReport();
            performanceReport.setAnganwadiCenter(anganwadiCenterService
                        .findAnganwadiCenterByID(performanceReportSearch
                                    .getAnganwadiID()));
            performanceReport
                        .setAnganwadiCenterReportAccodion(getAnganwadiCenterReportAccodion(performanceReportSearch));
            List<PerformanceReportAccordion> reportAccordions = new ArrayList<PerformanceReportAccordion>();
            for (Staff staff : localVolunteerList) {
                  reportAccordions.add(getStaffReport(staff,
                              performanceReportSearch));
            }
            performanceReport.setReportAccordions(reportAccordions);
            return performanceReport;
      }

      private PerformanceReportAccordion getAnganwadiCenterReportAccodion(
                  PerformanceReportSearch performanceReportSearch) {

            PerformanceReportAccordion reportAccordion = new PerformanceReportAccordion();
            VisitsQuery visitQuery = new VisitsQuery();
            visitQuery.withOriginalDueDateIn(
                        performanceReportSearch.getStartsDate(),
                        performanceReportSearch.getEndsDate());
            visitQuery.havingMetadata(MedicMobileConstants.ANGANWADI_ID,
                        performanceReportSearch.getAnganwadiID());
            List<VisitResponse> visitResponses = appointmentService
                        .search(visitQuery);
            Map<String, List<VisitResponse>> vaccineDetails = groupVisitResponses(visitResponses);
            reportAccordion
                        .setVaccines(getPerformanceReportVaccineList(vaccineDetails));

            return reportAccordion;

      }

      /**
       * Gets the staff report.
       * 
       * @param staff
       *              the staff
       * @param performanceReportSearch
       *              the performance report search
       * @return the staff report
       */
      private PerformanceReportAccordion getStaffReport(Staff staff,
                  PerformanceReportSearch performanceReportSearch) {
            PerformanceReportAccordion reportAccordion = new PerformanceReportAccordion();
            VisitsQuery visitQuery = new VisitsQuery();
            visitQuery.withOriginalDueDateIn(
                        performanceReportSearch.getStartsDate(),
                        performanceReportSearch.getEndsDate());
            visitQuery.havingMetadata("localVolunteerID", staff.getStaffID());
            List<VisitResponse> visitResponses = appointmentService
                        .search(visitQuery);
            Map<String, List<VisitResponse>> vaccineDetails = groupVisitResponses(visitResponses);
            reportAccordion
                        .setVaccines(getPerformanceReportVaccineList(vaccineDetails));
            reportAccordion.setNosUpdates(inboundSmsRepo
                        .messagesReceivedBetweenFrom(staff.getStaffID(),
                                    performanceReportSearch.getStartsDate(),
                                    performanceReportSearch.getEndsDate())
                        .size());
            reportAccordion.setNosUpdatesSuccessful(inboundSmsRepo
                        .messagesReceivedBetweenFromSuccessful(
                                    staff.getStaffID(),
                                    performanceReportSearch.getStartsDate(),
                                    performanceReportSearch.getEndsDate())
                        .size());
            reportAccordion.setNosUpdatesUnsuccessful(reportAccordion
                        .getNosUpdates()
                        - reportAccordion.getNosUpdatesSuccessful());
            reportAccordion.setNosSMSReceived(outboundSmsRepo
                        .messagesSentBetween(staff.getPhoneNumber(),
                                    performanceReportSearch.getStartsDate(),
                                    performanceReportSearch.getEndsDate())
                        .size());
            reportAccordion.setName(staff.getName());
            return reportAccordion;

      }

      /**
       * Gets the performance report vaccine list.
       * 
       * @param vaccineDetails
       *              the vaccine details
       * @return the performance report vaccine list
       */
      private List<PerformanceReportVaccine> getPerformanceReportVaccineList(
                  Map<String, List<VisitResponse>> vaccineDetails) {
            List<PerformanceReportVaccine> reportVaccines = new ArrayList<PerformanceReportVaccine>();
            for (String key : vaccineDetails.keySet()) {
                  PerformanceReportVaccine vaccine = new PerformanceReportVaccine();
                  vaccine.setVaccineName(key);
                  vaccine.setNosTotal(vaccineDetails.get(key).size());
                  int missed = 0;
                  for (VisitResponse visitResponse : vaccineDetails.get(key)) {
                        if (visitResponse.getVisitDate() == null) {
                              missed++;
                        }
                  }
                  vaccine.setNosMissed(missed);
                  vaccine.setNosReceived(vaccine.getNosTotal()
                              - vaccine.getNosMissed());
                  reportVaccines.add(vaccine);
            }
            return reportVaccines;
      }

      /**
       * Group visit responses.
       * 
       * @param visitResponses
       *              the visit responses
       * @return the map
       */
      private Map<String, List<VisitResponse>> groupVisitResponses(
                  List<VisitResponse> visitResponses) {
            Map<String, List<VisitResponse>> vaccineMap = new HashMap<String, List<VisitResponse>>();
            for (VisitResponse visit : visitResponses) {
                  String name = visit.getName();
                  if (vaccineMap.containsKey(name)) {
                        vaccineMap.get(name).add(visit);
                  } else {
                        List<VisitResponse> temp = new ArrayList<>();
                        temp.add(visit);
                        vaccineMap.put(name, temp);
                  }
            }
            return vaccineMap;
      }

}
