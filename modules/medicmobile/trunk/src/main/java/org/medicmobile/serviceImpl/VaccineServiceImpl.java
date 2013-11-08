package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;
import org.medicmobile.model.MasterVaccine;
import org.medicmobile.model.VaccineSchedule;
import org.medicmobile.repositoryImpl.CareGiverRepositoryImpl;
import org.medicmobile.repositoryImpl.ChildRepositoryImpl;
import org.medicmobile.repositoryImpl.MasterVaccineRepositoryImpl;
import org.medicmobile.service.VaccineService;
import org.medicmobile.util.MedicMobileConstants;
import org.motechproject.appointments.api.EventKeys;
import org.motechproject.appointments.api.model.AppointmentCalendar;
import org.motechproject.appointments.api.model.Visit;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.AppointmentCalendarRequest;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.EventRelay;
import org.motechproject.scheduler.MotechSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class VaccineServiceImpl.
 */
@Service
public class VaccineServiceImpl implements VaccineService {

      /** The care giver repo. */
      @Autowired
      private CareGiverRepositoryImpl careGiverRepo;

      /** The child repo. */
      @Autowired
      private ChildRepositoryImpl childRepo;

      /** The vaccine repo. */
      @Resource(name = "masterVaccine")
      private MasterVaccineRepositoryImpl vaccineRepo;

      /** The appointment service. */
      @Autowired
      private AppointmentService appointmentService;

      /** The event relay. */
      @Autowired
      private EventRelay eventRelay;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.VaccineService#createAppointmentSchedule(java
       * .util.List)
       */
      public void createAppointmentSchedule(List<Child> childList) {

            if (!childList.isEmpty()) {
                  for (Child currentChild : childList) {
                        this.createAppointmentSchedule(currentChild);
                        /*
                         * if (current.getChildCalendarID() == null ||
                         * current.getChildCalendarID() == "") { String
                         * externalId = UtilService.generateUniqueID();
                         * current.setChildCalendarID(externalId);
                         * childRepo.addChild(current);
                         * AppointmentCalendarRequest calendarRequest = new
                         * AppointmentCalendarRequest();
                         * calendarRequest.setExternalId(externalId); for
                         * (MasterVaccine vaccine : masterVaccine) {
                         * List<CreateVisitRequest> visitResponse =
                         * getVisitRequest( vaccine, current, careGiver);
                         * calendarRequest.getCreateVisitRequests().addAll(
                         * visitResponse); }
                         * appointmentService.addCalendar(calendarRequest);
                         */
                  }
            }
      }

      /**
       * Gets the visit request.
       * 
       * @param vaccine
       *              the vaccine
       * @param child
       *              the child
       * @param careGiver
       *              the care giver
       * @return the visit request
       */
      public List<CreateVisitRequest> getVisitRequest(MasterVaccine vaccine,
                  Child child, CareGiver careGiver) {
            List<CreateVisitRequest> visitRequests = new ArrayList<CreateVisitRequest>();
            for (VaccineSchedule schedule : vaccine.getVaccineSchedule()) {

                  CreateVisitRequest request = new CreateVisitRequest();
                  request.setTypeOfVisit(vaccine.getVaccineName());
                  request.setVisitName(schedule.getVaccineName());
                  request.setAppointmentDueDate(getDueDate(
                              schedule.getStartDay(),
                              new DateTime(child.getDateOfBirth())));
                  Map<String, Object> data = new HashMap<String, Object>();
                  data.put("anganwadiID", careGiver.getAnganwadiID());
                  data.put("localVolunteerID", careGiver.getLocalVolunteerID());
                  data.put("childID", child.getChildID());
                  request.setData(data);
                  visitRequests.add(request);
            }
            return visitRequests;

      }

      /**
       * Gets the due date.
       * 
       * @param vaccineWeek
       *              the vaccine week
       * @param childDateOFBirth
       *              the child date of birth
       * @return the due date
       */
      public DateTime getDueDate(int vaccineWeek, DateTime childDateOFBirth) {
            return childDateOFBirth.plusDays(vaccineWeek);
      }

      /**
       * Update appointment schedule.
       * 
       * @param child
       *              the child
       * @param currentVisit
       *              the current visit
       * @return the appointment calendar
       */
      public AppointmentCalendar updateAppointmentSchedule(Child child,
                  Visit currentVisit) {
            throw new UnsupportedOperationException("Not supported yet.");
      }

      /**
       * Update appointment schedule.
       */
      public void updateAppointmentSchedule() {
            throw new UnsupportedOperationException("Not supported yet.");
      }

      /**
       * Creates the appointment schedule.
       * 
       * @param child
       *              the child
       */
      public String createAppointmentSchedule(Child child) {
            List<MasterVaccine> masterVaccine = vaccineRepo.getAll();
            List<VisitResponse> oldVisitResponses = new ArrayList<VisitResponse>();
            if (child.getChildCalendarID() != null) {
                  oldVisitResponses.addAll(appointmentService
                              .getAllVisits(child.getChildCalendarID()));
                  appointmentService.removeCalendar(child.getChildCalendarID());
            }
            CareGiver careGiver = careGiverRepo.findByCareGiverIDS(
                        child.getCareGiverID()).get(0);
            String externalId = child.getChildID();
            child.setChildCalendarID(externalId);
            childRepo.addChild(child);
            AppointmentCalendarRequest calendarRequest = new AppointmentCalendarRequest();
            calendarRequest.setExternalId(externalId);
            for (MasterVaccine vaccine : masterVaccine) {
                  List<CreateVisitRequest> createVisitRequest = getVisitRequest(
                              vaccine, child, careGiver);
                  calendarRequest.getCreateVisitRequests().addAll(
                              createVisitRequest);
            }
            appointmentService.addCalendar(calendarRequest);
            for (VisitResponse oldVisitResponse : oldVisitResponses) {
                  if (oldVisitResponse.getVisitDate() != null) {
                        appointmentService.visited(externalId,
                                    oldVisitResponse.getName(),
                                    oldVisitResponse.getVisitDate());
                  }
            }
            Map<String, Object> data = new HashMap<String, Object>();
            String anganwadiId = careGiver.getAnganwadiID();
            data.put(MotechSchedulerService.JOB_ID_KEY, anganwadiId
                        + "-child-dob-updated");
            data.put(MedicMobileConstants.CHILD_ID, child.getChildID());
            MotechEvent event = new MotechEvent(
                        WeeklyReportServiceImpl.RESCHEDULE_VISIT_CHILD, data);
            eventRelay.sendEventMessage(event);
            return externalId;
      }

      /**
       * Creates the appointment schedule for child.
       * 
       * @param child
       *              the child
       */
      public void createAppointmentScheduleForChild(Child child) {

            List<MasterVaccine> masterVaccine = vaccineRepo.getAll();

            String externalId = child.getChildID();
            child.setChildCalendarID(externalId);
            AppointmentCalendarRequest calendarRequest = new AppointmentCalendarRequest();
            calendarRequest.setExternalId(externalId);
            CareGiver careGiver = new CareGiver();
            careGiver.setAnganwadiID("TEstsdsa");
            careGiver.setLocalVolunteerID("sdsdfgh");
            careGiver.setCareGiverID("craps");
            for (MasterVaccine vaccine : masterVaccine) {
                  List<CreateVisitRequest> visitResponse = getVisitRequest(
                              vaccine, child, careGiver);
                  calendarRequest.getCreateVisitRequests()
                              .addAll(visitResponse);
            }
            appointmentService.addCalendar(calendarRequest);
      }

      /**
       * Update future vaccine schedules.
       * 
       * @param calenderID
       *              the calender id
       * @param vaccineName
       *              the vaccine name
       */
      public void updateFutureVaccineSchedules(String calenderID,
                  String vaccineName) {
      }

      // @PostConstruct
      /**
       * Creat event.
       */
      public void creatEvent() {
            Map<String, Object> test = new HashMap<>();
            test.put(EventKeys.EXTERNAL_ID_KEY, "_id-3912353264");
            Map<String, DateTime> testMap = new HashMap<>();
            testMap.put("Tesuing", new DateTime());
            test.put(EventKeys.VISIT_REQUESTS, testMap);
            MotechEvent event = new MotechEvent(
                        EventKeys.CREATE_APPOINTMENT_EVENT_SUBJECT, test);
            eventRelay.sendEventMessage(event);
      }

      @Override
      public void changeVolunteerDetailsForChildSchedule(CareGiver careGiver,
                  String localVolunteerID, String angawadiID) {
            boolean check = !(careGiver.getAnganwadiID().equals(angawadiID) && careGiver
                        .getLocalVolunteerID().equals(localVolunteerID));
            if (check) {
                  changeParams(careGiver);
            }

      }

      private void changeParams(CareGiver careGiver) {
            List<Child> childList = childRepo.findByCareGiverID(careGiver
                        .getCareGiverID());
            List<String> childCalenderIDs = new ArrayList<>();
            for (Child current : childList) {
                  childCalenderIDs.add(current.getChildCalendarID());
            }
            List<String> visitNames = new ArrayList<>();
            List<MasterVaccine> vaccines = vaccineRepo.getAll();
            for (MasterVaccine vaccine : vaccines) {
                  List<VaccineSchedule> vaccineSchedules = vaccine
                              .getVaccineSchedule();
                  for (VaccineSchedule vaccineSchedule : vaccineSchedules) {
                        visitNames.add(vaccineSchedule.getVaccineID());
                  }
            }
            Map<String, Object> data = new HashMap<>();
            data.put(MedicMobileConstants.ANGANWADI_ID,
                        careGiver.getAnganwadiID());
            data.put(MedicMobileConstants.LOCAL_VOLUNTEER_ID,
                        careGiver.getLocalVolunteerID());

            changeData(childCalenderIDs, visitNames, data);

      }

      private void changeData(List<String> childCalenderIDs, List<String> visitNames, Map<String, Object> data) {

            for (String current : childCalenderIDs) {
                  for (String visitName : visitNames) {
                        appointmentService.addCustomDataToVisit(current,
                                    visitName, data);
                  }
            }

      }
}
