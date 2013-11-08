package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medicmobile.dto.ReportSearch;
import org.medicmobile.dto.ReportUpdates;
import org.medicmobile.dto.VisitUpdate;
import org.medicmobile.dto.WeeklyReport;
import org.medicmobile.dto.WeeklyReportAccordian;
import org.medicmobile.dto.WeeklyReportRow;
import org.medicmobile.model.AnganwadiCenterView;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.Child;
import org.medicmobile.model.MasterVaccine;
import org.medicmobile.model.SMSCodeMap;
import org.medicmobile.model.Staff;
import org.medicmobile.model.VaccineSchedule;
import org.medicmobile.repository.MasterVaccineRepository;
import org.medicmobile.repositoryImpl.SMSCodeMapRepositoryImpl;
import org.medicmobile.service.AnganwadiCenterService;
import org.medicmobile.service.CareGiverService;
import org.medicmobile.service.ChildService;
import org.medicmobile.service.StaffService;
import org.medicmobile.service.WeeklyReportService;
import org.medicmobile.sms.SendSMS;
import org.medicmobile.util.HindiTransliteratorHelper;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.RescheduleAppointmentRequest;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.scheduler.MotechSchedulerService;
import org.motechproject.scheduler.domain.RepeatingSchedulableJob;
import org.motechproject.scheduler.domain.RunOnceSchedulableJob;
import org.motechproject.server.config.SettingsFacade;
import org.motechproject.sms.api.service.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class WeeklyReportServiceImpl.
 */
@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {

      /** The logger. */
      private static Logger logger = Logger
                  .getLogger(WeeklyReportServiceImpl.class);

      /** The appointment service. */
      @Autowired
      private AppointmentService appointmentService;

      /** The staff service. */
      @Autowired
      private StaffService staffService;

      /** The child service. */
      @Autowired
      private ChildService childService;

      /** The scheduler. */
      @Autowired
      private MotechSchedulerService scheduler;

      /** The care giver service. */
      @Autowired
      private CareGiverService careGiverService;

      @Autowired
      private AnganwadiCenterService anganwadiCenterService;

      /** The send sms. */
      @Autowired
      private SendSMS sendSms;

      /** The master vaccine repo. */
      @Autowired
      private MasterVaccineRepository masterVaccineRepo;

      /** The sms code map repo. */
      @Autowired
      private SMSCodeMapRepositoryImpl smsCodeMapRepo;

      @Resource(name = "vaccineOrderSettings")
      private SettingsFacade vaccineOrderSettings;

      /** The Constant SCHEDULE_SMS. */
      private static final String SCHEDULE_SMS = "schedule_SMS";

      public static final String RESCHEDULE_VISIT = "reschedule_JOB";

      public static final String TRIGGER_SMS = "trigger-sms-event";

      /** The Constant ENDDATE. */
      public static final DateTime ENDDATE = new DateTime(2014, 4, 16, 00, 00);

      public static final String FREQUENCY_OF_SCHEDULER_IN_DAYS = "frequency.of.scheduler.in.days";

      public static final String DIFFRENCE_BETWEEN_ANGANWADI_IN_HOURS = "difference.between.each.anganwadi.in.hours";

      public static final String RESCHEDULE_VISIT_CHILD = "reschedule_JOB_Child";

      public static final int DIFFERENCE = 1200000;

      public static final long INTERVALINMILLISECONDS = 86400000L;

      public static final String[] VACCINELIST = { "DPT1", "DT1", "OPV0",
                  "VIT1", "HEPB0" };

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.WeeklyReportService#getWeeklyReport(org.medicmobile
       * .dto.ReportSearch)
       */

      /*
       * @Autowired public
       * WeeklyReportServiceImpl(@Qualifier("schedulerMedicMobileSettings")
       * SettingsFacade schedulerSettings) { this.INTERVALINMILLISECONDS =
       * 86400000*Integer.parseInt(schedulerSettings.getProperty(
       * FREQUENCY_OF_SCHEDULER_IN_DAYS)); this.DIFFERENCE =
       * 3600000*Integer.parseInt
       * (schedulerSettings.getProperty(DIFFRENCE_BETWEEN_ANGANWADI_IN_HOURS));
       * }
       */
      @Override
      public WeeklyReport getWeeklyReport(ReportSearch reportSearch) {
            VisitsQuery visitQuery = new VisitsQuery();
            /*
             * visitQuery.withDueDateIn(reportSearch.getReportDate().minusDays(27
             * ), reportSearch.getReportDate().plusDays(1));
             */
            visitQuery.withVisitDateIn(
                        reportSearch.getReportDate().minusDays(27),
                        reportSearch.getReportDate().plusDays(1));
            visitQuery.havingMetadata(MedicMobileConstants.ANGANWADI_ID,
                        reportSearch.getAnganwadiID());
            List<VisitResponse> allVaccineDueDatesTemp = appointmentService
                        .search(visitQuery);
            List<VisitResponse> allVaccineDueDates = new ArrayList<VisitResponse>();
            logger.error("Checking the number of records after first criterion "
                        + allVaccineDueDates.size());
            for (VisitResponse visitResponseTemp : allVaccineDueDatesTemp) {
                  if (!visitResponseTemp.isMissed()) {
                        allVaccineDueDates.add(visitResponseTemp);
                  }
            }
            allVaccineDueDates.addAll(appointmentService
                        .getResponseAnganwadiCenter("anganwadiID",
                                    reportSearch.getAnganwadiID(), false,
                                    reportSearch.getReportDate()));
            Map<String, List<WeeklyReportRow>> accordion = new HashMap<>();
            for (VisitResponse visitResponse : allVaccineDueDates) {
                  if (accordion.containsKey(visitResponse.getVisitData().get(
                              "localVolunteerID"))) {
                        accordion.get(
                                    visitResponse.getVisitData().get(
                                                "localVolunteerID")).add(
                                    getWeeklyReportRow(visitResponse));
                  } else {
                        List<WeeklyReportRow> rows = new ArrayList<WeeklyReportRow>();
                        WeeklyReportRow tempRow = getWeeklyReportRow(visitResponse);
                        if (tempRow != null) {
                              rows.add(tempRow);
                        }
                        accordion.put((String) visitResponse.getVisitData()
                                    .get("localVolunteerID"), rows);
                  }

            }

            WeeklyReport weeklyReport = new WeeklyReport();
            weeklyReport.setReportRows(convertAccordion(accordion));
            return weeklyReport;
      }

      /**
       * Convert accordion.
       * 
       * @param accordion
       *              the accordion
       * @return the list
       */
      private List<WeeklyReportAccordian> convertAccordion(
                  Map<String, List<WeeklyReportRow>> accordion) {
            List<WeeklyReportAccordian> accordionsList = new ArrayList<WeeklyReportAccordian>();
            for (String key : accordion.keySet()) {

                  List<Staff> localVolunteerList = staffService
                              .findByStaffID(key);

                  String localVolunteerName;
                  if (localVolunteerList.isEmpty()) {
                        localVolunteerName = "Other";
                  } else {
                        localVolunteerName = localVolunteerList.get(0)
                                    .getName();
                  }
                  WeeklyReportAccordian report = new WeeklyReportAccordian();
                  report.setLocalVolunteerName(localVolunteerName);
                  report.setLocalVolunteerID(key);
                  report.setRows(accordion.get(key));
                  accordionsList.add(report);
            }
            return accordionsList;
      }

      /**
       * Gets the weekly report row.
       * 
       * @param visitResponse
       *              the visit response
       * @return the weekly report row
       */
      private WeeklyReportRow getWeeklyReportRow(VisitResponse visitResponse) {
            WeeklyReportRow weeklyReportRow = new WeeklyReportRow();
            weeklyReportRow.setDueDate(visitResponse
                        .getOriginalAppointmentDueDate());
            if (visitResponse.getVisitDate() != null) {
                  weeklyReportRow.setAdministeredDate(visitResponse
                              .getVisitDate());
                  weeklyReportRow.setVaccineTaken(true);
            } else {
                  weeklyReportRow.setAdministeredDate(visitResponse
                              .getVisitDate());
                  weeklyReportRow.setVaccineTaken(false);
            }
            weeklyReportRow.setVaccineID(visitResponse.getExternalId() + "."
                        + visitResponse.getName());
            String childID = (String) visitResponse.getVisitData().get(
                        "childID");
            String updatedBy = "";
            if (visitResponse.getVisitData().containsKey("updateBy")) {
                  updatedBy = (String) visitResponse.getVisitData().get(
                              "updateBy");
            }
            Child child = childService.getChildByID(childID);
            if (child == null
                        || !child.getChildCalendarID().equals(
                                    visitResponse.getExternalId())) {
                  return null;
            }
            weeklyReportRow.setChildName(child.getName());
            weeklyReportRow.setChildID(childID);
            weeklyReportRow.setChildNameInHindi(child.getNameInHindi());
            CareGiver careGiver = careGiverService.getByCareGiverID(child
                        .getCareGiverID());
            weeklyReportRow.setCareGiverName(careGiver.getName());
            weeklyReportRow.setCareGiverNameInHindi(careGiver.getNameInHindi());
            weeklyReportRow.setCareGiverID(careGiver.getCareGiverID());
            weeklyReportRow.setVaccineName(visitResponse.getName());
            weeklyReportRow.setMissed(visitResponse.isMissed());
            weeklyReportRow.setUpdatedBy(updatedBy);
            int startCode = -1;
            List<SMSCodeMap> codeMaps = smsCodeMapRepo
                        .findBySystemCode(weeklyReportRow.getVaccineID() + ".2");
            if (codeMaps.isEmpty()) {
                  String vaccineCode = vaccineOrderSettings
                              .getProperty(visitResponse.getName());
                  int vaccineCodeInt = Integer.parseInt(vaccineCode);
                  if (vaccineCodeInt < 10) {
                        vaccineCode = "0" + vaccineCode;
                  }
                  if (child.getChildSystemId() != null
                              && !child.getChildSystemId().equals("")) {
                        startCode = Integer.parseInt(child.getChildSystemId()
                                    + vaccineCode);
                        smsCodeMapRepo.add(new SMSCodeMap(startCode,
                                    weeklyReportRow.getVaccineID() + ".2"));
                  }
            } else {
                  startCode = codeMaps.get(0).getSmsCode();
            }
            weeklyReportRow.setSmsCode(((Integer) (startCode)).toString());
            return weeklyReportRow;

      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.WeeklyReportService#update(org.medicmobile.
       * dto.VisitUpdate)
       */
      @Override
      public boolean update(VisitUpdate visitUpdate) {
            try {
                  String uniqueID = visitUpdate.getVaccineID();
                  int nameStartIndex = uniqueID.indexOf('.') + 1;
                  if (uniqueID.charAt(uniqueID.length() - 1) == '0') {
                        appointmentService.visited(
                                    uniqueID.substring(0, nameStartIndex - 1),
                                    uniqueID.substring(nameStartIndex,
                                                uniqueID.length() - 2),
                                    visitUpdate.getVisitedDate());
                  } else if (uniqueID.charAt(uniqueID.length() - 1) == '1') {
                        appointmentService.visited(
                                    uniqueID.substring(0, nameStartIndex - 1),
                                    uniqueID.substring(nameStartIndex,
                                                uniqueID.length() - 2), null);
                  } else if (uniqueID.charAt(uniqueID.length() - 1) == '2') {
                        Map<String, Object> visitMap = new HashMap<String, Object>();
                        visitMap.put("updateBy", visitUpdate.getPersonName());
                        appointmentService.visited(
                                    uniqueID.substring(0, nameStartIndex - 1),
                                    uniqueID.substring(nameStartIndex,
                                                uniqueID.length() - 2),
                                    visitUpdate.getVisitedDate());
                        appointmentService.addCustomDataToVisit(
                                    uniqueID.substring(0, nameStartIndex - 1),
                                    uniqueID.substring(nameStartIndex,
                                                uniqueID.length() - 2),
                                    visitMap);
                  }
                  return true;
            } catch (Exception exception) {
                  logger.info(exception.getMessage());
                  return false;
            }
      }

      @Override
      public boolean updateFromReport(ReportUpdates reportUpdates) {

            if (reportUpdates.getUpdates() == null) {
                  return false;
            }

            for (String visitString : reportUpdates.getUpdates()) {
                  VisitUpdate visitUpdate;
                  visitUpdate = new VisitUpdate();
                  visitUpdate.setVaccineID(visitString);
                  visitUpdate.setVisitDate(reportUpdates.getVisitDate());
                  this.update(visitUpdate);
            }
            return true;
            /** The sms code repo. */
      }

      @Override
      @MotechListener(subjects = SCHEDULE_SMS)
      public void sendReminders(MotechEvent event) {
            ReportSearch reportSearch = new ReportSearch();
            DateTime date = new DateTime().plusDays(2);
            String anganwadiID = (String) event.getParameters().get(
                        "anganwadiID");
            reportSearch.setReportDate(date);
            reportSearch.setAnganwadiID(anganwadiID);
      }

      /**
       * Send reminder to care giver.
       * 
       * @param careGiverNameInHindi
       *              the care giver
       * @param childName
       *              the child name
       * @param vaccineNameListInHindi
       *              the vaccine name
       * @param date
       *              the date
       */
      public void sendReminderToCareGiver(String careGiverPhoneNumber,
                  String childNameInHindi, String vaccineNameListInHindi,
                  DateTime date) {
            String tempCareGiverNumber = null;
            if (careGiverPhoneNumber != null
                        && !"".equals(careGiverPhoneNumber)) {
                  if (careGiverPhoneNumber.length() == 10
                              && !careGiverPhoneNumber.startsWith("0")) {
                        tempCareGiverNumber = "0" + careGiverPhoneNumber;
                  }
                  String message = "";
                  message = "आप के बच्चे(" + childNameInHindi + ") का "
                              + vaccineNameListInHindi + " "
                              + HindiTransliteratorHelper.getDateInHindi(date)
                              + " तिथि का टीकाकरन बाकि है";
                  sendSms.sendSMSEvent(new SendSmsRequest(Arrays
                              .asList(tempCareGiverNumber), message));
            }
      }

      @MotechListener(subjects = RESCHEDULE_VISIT)
      public void rescheduleAppointmentJob(MotechEvent event) {
            String anganwadiID = (String) event.getParameters().get(
                        MedicMobileConstants.ANGANWADI_ID);
            List<String> masterVaccineListFinal = new ArrayList<>();
            List<MasterVaccine> masterVaccineList = masterVaccineRepo.getAll();
            for (MasterVaccine vaccine : masterVaccineList) {
                  masterVaccineListFinal.add(vaccine.getVaccineName());
            }
            List<CareGiverView> careGiverList = careGiverService
                        .findByAnganwadiID(anganwadiID);
            for (CareGiverView careGiver : careGiverList) {
                  String careGiverID = careGiver.getCareGiver()
                              .getCareGiverID();
                  List<Child> childList = childService
                              .findByCareGiverID(careGiverID);
                  for (Child currentChild : childList) {
                        rescheduleForChild(currentChild, masterVaccineListFinal);
                  }
            }
      }

      @MotechListener(subjects = RESCHEDULE_VISIT_CHILD)
      public void rescheduleAppointmentJobForChild(MotechEvent event) {
            String childId = (String) event.getParameters().get(
                        MedicMobileConstants.CHILD_ID);
            Child child = childService.findByChildID(childId).get(0).getChild();
            List<String> masterVaccineListFinal = new ArrayList<>();
            List<MasterVaccine> masterVaccineList = masterVaccineRepo.getAll();
            for (MasterVaccine vaccine : masterVaccineList) {
                  masterVaccineListFinal.add(vaccine.getVaccineName());
            }
            rescheduleForChild(child, masterVaccineListFinal);
      }

      private void rescheduleForChild(Child child,
                  List<String> masterVaccineListFinal) {
            for (String currentMasterVaccineType : masterVaccineListFinal) {
                  fetchPreviousRecords(child.getChildCalendarID(),
                              currentMasterVaccineType, child.getDateOfBirth());
            }
      }

      public void fetchPreviousRecords(String childCalendarId,
                  String masterVaccineName, Date childDateOfBirth) {
            List<VisitResponse> childAllVisits = appointmentService
                        .getAllVisits(childCalendarId);
            List<VisitResponse> childVisitResponsesForVaccineName = new ArrayList<>();
            for (VisitResponse currentVisit : childAllVisits) {
                  if (!currentVisit.getTypeOfVisit().equals(masterVaccineName)) {

                  } else {
                        childVisitResponsesForVaccineName.add(currentVisit);
                  }
            }
            List<VaccineSchedule> masterVaccineSchedulerWithVaccineName = masterVaccineRepo
                        .findByVaccineName(masterVaccineName).get(0)
                        .getVaccineSchedule();
            reSchedule(childVisitResponsesForVaccineName,
                        masterVaccineSchedulerWithVaccineName, childCalendarId,
                        childDateOfBirth);
      }


      private void reSchedule(
                  List<VisitResponse> childVisitResponsesForVaccineName,
                  List<VaccineSchedule> masterVaccineSchedulerWithVaccineName,
                  String childCalendarId, Date childDateOfBirth) {
            DateTime currentDateTime;
            DateTime childDateOfBirthDateTime = new DateTime(childDateOfBirth);
            int lengthOfChildVisitResponseVaccineName = childVisitResponsesForVaccineName
                        .size();
            VisitResponse childVisitResponseTemp;
            boolean condition = false;
            for (int childVisitResponseIndex = 0; childVisitResponseIndex < lengthOfChildVisitResponseVaccineName; childVisitResponseIndex++) {
                  condition = false;
                  childVisitResponseTemp = childVisitResponsesForVaccineName
                              .get(childVisitResponseIndex);
                  currentDateTime = new DateTime();
                  condition = handlingSpecialVaccine(childVisitResponseTemp, childCalendarId, childVisitResponseIndex, childVisitResponsesForVaccineName, lengthOfChildVisitResponseVaccineName, condition);
                  if (childVisitResponseTemp.getVisitDate() == null) {
                        if (childVisitResponseTemp.getAppointmentDueDate()
                                    .isBefore(currentDateTime)) {
                              if (currentDateTime
                                          .isAfter(childDateOfBirthDateTime
                                                      .plusDays(masterVaccineSchedulerWithVaccineName
                                                                  .get(childVisitResponseIndex)
                                                                  .getEndDay()))
                                          && !masterVaccineSchedulerWithVaccineName
                                                      .get(childVisitResponseIndex)
                                                      .getIsApplicableAfterEndDay()) {
                                    markAsMissed(
                                                childVisitResponseTemp
                                                            .getName(),
                                                childCalendarId);

                                    for (int nextMasterVaccineIndex = childVisitResponseIndex + 1; nextMasterVaccineIndex < lengthOfChildVisitResponseVaccineName; nextMasterVaccineIndex++) {
                                          if (masterVaccineSchedulerWithVaccineName
                                                      .get(nextMasterVaccineIndex)
                                                      .getIsBeforeCompulsory()) {
                                                markAsMissed(
                                                            masterVaccineSchedulerWithVaccineName
                                                                        .get(nextMasterVaccineIndex)
                                                                        .getVaccineName(),
                                                            childCalendarId);
                                                childVisitResponsesForVaccineName
                                                            .get(nextMasterVaccineIndex)
                                                            .setMissed(true);
                                          } else {
                                                break;
                                          }
                                    }
                              } else {
                                    if (childVisitResponseIndex < lengthOfChildVisitResponseVaccineName - 1) {
                                          this.reScheduleRemainingJob(
                                                      childVisitResponsesForVaccineName,
                                                      childCalendarId,
                                                      childVisitResponseIndex,
                                                      currentDateTime
                                                                  .plusWeeks(2000));
                                          break;
                                    }
                              }
                        } else {
                              this.reScheduleRemainingJob(
                                          childVisitResponsesForVaccineName,
                                          childCalendarId,
                                          childVisitResponseIndex,
                                          currentDateTime.plusWeeks(2000));
                              break;
                        }
                  } else {
                        reScheduleHelper(childVisitResponseIndex, lengthOfChildVisitResponseVaccineName, condition, childVisitResponsesForVaccineName, masterVaccineSchedulerWithVaccineName, childCalendarId, childVisitResponseTemp, currentDateTime);
                        break;
                  }
            }
      }
      
      private boolean handlingSpecialVaccine(VisitResponse childVisitResponseTemp, String childCalendarId, int childVisitResponseIndex, List<VisitResponse> childVisitResponsesForVaccineName, int lengthOfChildVisitResponseVaccineName, boolean vCondition) {
          boolean condition = vCondition;
          if (!childVisitResponseTemp.isMissed()
                  && childVisitResponseTemp.getName()
                              .equals("DPT1")) {
            markAsMissed("DT1", childCalendarId);
            markAsMissed("DT2", childCalendarId);
      }
      if (childVisitResponseIndex < lengthOfChildVisitResponseVaccineName - 1) {
            VisitResponse nextVisitResponse = childVisitResponsesForVaccineName
                        .get(childVisitResponseIndex + 1);
            if (nextVisitResponse.getVisitDate() != null) {
                  condition = true;
            }
      }
      return condition;
      }
      
      private void reScheduleHelper(int childVisitResponseIndex, int lengthOfChildVisitResponseVaccineName, boolean condition, List<VisitResponse> childVisitResponsesForVaccineName, List<VaccineSchedule> masterVaccineSchedulerWithVaccineName,
              String childCalendarId, VisitResponse childVisitResponseTemp, DateTime currentDateTime) {
          if (childVisitResponseIndex < lengthOfChildVisitResponseVaccineName - 1
                  && !condition) {
            VisitResponse nextVisitResponse = childVisitResponsesForVaccineName
                        .get(childVisitResponseIndex + 1);
            DateTime dateTimeToChange = childVisitResponseTemp
                        .getVisitDate()
                        .plusWeeks(masterVaccineSchedulerWithVaccineName
                                    .get(childVisitResponseIndex + 1)
                                    .getGapInWeeks());
            if (dateTimeToChange.isAfter(nextVisitResponse
                        .getOriginalAppointmentDueDate())) {
                  changeDatesOfAppointment(nextVisitResponse,
                              dateTimeToChange.toDate(),
                              childCalendarId);
            } else {
                  changeDatesOfAppointment(
                              nextVisitResponse,
                              nextVisitResponse
                                          .getOriginalAppointmentDueDate()
                                          .toDate(),
                              childCalendarId);
            }
            this.reScheduleRemainingJob(
                        childVisitResponsesForVaccineName,
                        childCalendarId,
                        childVisitResponseIndex + 1,
                        currentDateTime.plusWeeks(2000));
      }
      }
      private void reScheduleRemainingJob(
                  List<VisitResponse> childVisitResponsesForVaccineName,
                  String childCalendarId, int childVisitResponseIndex,
                  DateTime currentDateTime) {
            DateTime tempCurrentDateTime = null;
            int lengthOfChildVisitResponseForVaccineName = childVisitResponsesForVaccineName
                        .size();
            for (int childVaccineIndex = childVisitResponseIndex + 1; childVaccineIndex < lengthOfChildVisitResponseForVaccineName; childVaccineIndex++) {
                  tempCurrentDateTime = currentDateTime.plusWeeks(2000);
                  VisitResponse childVisitResponseTemp = childVisitResponsesForVaccineName
                              .get(childVaccineIndex);
                  if (currentDateTime.isAfter(childVisitResponseTemp
                              .getOriginalAppointmentDueDate())) {
                        changeDatesOfAppointment(childVisitResponseTemp,
                                    tempCurrentDateTime.toDate(),
                                    childCalendarId);
                  }
            }
      }

      public void changeDatesOfAppointment(VisitResponse visitResponse,
                  Date toDate, String externalID) {
            RescheduleAppointmentRequest request = new RescheduleAppointmentRequest();
            request.setExternalId(externalID);
            request.setVisitName(visitResponse.getName());
            request.setAppointmentDueDate(new DateTime(toDate));
            appointmentService.rescheduleAppointment(request);
      }

      public void markAsMissed(String visitName, String externalID) {
            if ("DPT1".equals(visitName)) {
                  removeAsMissed("DT1", externalID);
                  removeAsMissed("DT2", externalID);
            }
            appointmentService.markVisitAsMissed(externalID, visitName);
      }

      public void removeAsMissed(String visitName, String externalID) {
            appointmentService.removeVisitAsMissed(externalID, visitName);
      }

      public void rescheduleVisit(String visitName, String externalID,
                  DateTime toDate) {
            RescheduleAppointmentRequest request = new RescheduleAppointmentRequest();
            request.setExternalId(externalID);
            request.setVisitName(visitName);
            request.setAppointmentDueDate(toDate);
            appointmentService.rescheduleAppointment(request);
      }

      public void scheduleSMSToAnganwadi(String anganwadiID, DateTime date) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put(MotechSchedulerService.JOB_ID_KEY, anganwadiID
                        + "-trigger-sms-event");
            data.put(MedicMobileConstants.DATE_TIME, date);
            data.put(MedicMobileConstants.ANGANWADI_ID, anganwadiID);
            MotechEvent event = new MotechEvent(TRIGGER_SMS, data);
            DateTime triggerDate = new DateTime();
            triggerDate = triggerDate.plusMinutes(2);
            RunOnceSchedulableJob runOnceSchedulableJob = new RunOnceSchedulableJob(
                        event, triggerDate.toDate());
            scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
      }

      @MotechListener(subjects = TRIGGER_SMS)
      public void scheduleSMSToAnganwadiEvent(MotechEvent event) {
            String anganwadiID = (String) event.getParameters().get(
                        MedicMobileConstants.ANGANWADI_ID);
            DateTime date = (DateTime) event.getParameters().get(
                        MedicMobileConstants.DATE_TIME);
            ReportSearch reportSearch = new ReportSearch();
            DateTime dateTime = new DateTime(date);
            reportSearch.setReportDate(dateTime);
            reportSearch.setAnganwadiID(anganwadiID);
            WeeklyReport weeklyReport = getWeeklyReport(reportSearch);
            List<String> phoneNumberList;
            String messageToLocalVolunteer = "";
            for (WeeklyReportAccordian current : weeklyReport
                        .getReportAccordians()) {
                  Map<String, String> childCareGiverMap = UtilService
                              .getChildCareGiverIDs(current);
                  Map<String, String> childIdVaccineNamesInHindiMap = HindiTransliteratorHelper
                              .getBulkVaccineNameForChildInHindi(current);
                  Map<String, String> childIdChildNameInHindiMap = UtilService
                              .getChildIdChildNameInHindi(current);
                  Map<String, String> careGiverIdCareGiverNameInHindiMap = UtilService
                              .getCareGiverIdCareGiverNameInHindi(current);
                  phoneNumberList = new ArrayList<String>();
                  String phoneNumber = staffService
                              .findByStaffID(current.getLocalVolunteerID())
                              .get(0).getPhoneNumber();
                  phoneNumberList.add(phoneNumber);
                  CareGiver careGiverTemp = null;
                  String careGiverId = "";
                  String careGiverPhoneNumber = "";
                  String childNameInHindi = "";
                  String vaccineListInHindi = "";
                  String careGiverNameInHindi = "";
                  for (String childId : childCareGiverMap.keySet()) {
                        careGiverId = (String) childCareGiverMap.get(childId);
                        careGiverTemp = careGiverService.findByCareGiverID(
                                    careGiverId).getCareGiver();
                        careGiverPhoneNumber = careGiverTemp.getPhoneNumber();
                        childNameInHindi = childIdChildNameInHindiMap
                                    .get(childId);
                        vaccineListInHindi = childIdVaccineNamesInHindiMap
                                    .get(childId);
                        careGiverNameInHindi = careGiverIdCareGiverNameInHindiMap
                                    .get(careGiverId);
                        messageToLocalVolunteer = messageToLocalVolunteer
                                    + careGiverNameInHindi + " के बच्चे("
                                    + childNameInHindi + ") का "
                                    + vaccineListInHindi + " और ";
                        sendReminderToCareGiver(careGiverPhoneNumber,
                                    childNameInHindi, vaccineListInHindi,
                                    dateTime);
                  }
                  messageToLocalVolunteer = messageToLocalVolunteer
                              + " "
                              + HindiTransliteratorHelper
                                          .getDateInHindi(dateTime)
                              + " तिथि का टीकाकरन बाकि है";
                  if (messageToLocalVolunteer.length() > 0) {
                        sendSms.sendSMSEvent(new SendSmsRequest(
                                    phoneNumberList, messageToLocalVolunteer));
                  }
            }
      }

//      @PostConstruct
      public void rescheduleAppointmentDailyJob() {
            scheduler.unscheduleAllJobs(RESCHEDULE_VISIT);
            List<AnganwadiCenterView> anganwadiCenterList = anganwadiCenterService
                        .findAllAnganwadiCenter();
            MotechEvent event;
            Map<String, Object> data = new HashMap<String, Object>();
            String anganwadiId;
            int timeDifference = 0;
            DateTime referenceDate = new DateTime();
            DateTime startDate;

            startDate = new DateTime(referenceDate.getYear(),
                        referenceDate.getMonthOfYear(),
                        referenceDate.getDayOfMonth() + 1, 0, 0, 0);
            for (AnganwadiCenterView anganwadiCenterView : anganwadiCenterList) {
                  anganwadiId = anganwadiCenterView.getAnganwadiCenter()
                              .getAnganwadiID();
                  data.put(MotechSchedulerService.JOB_ID_KEY, anganwadiId);
                  data.put(MedicMobileConstants.ANGANWADI_ID, anganwadiId);
                  event = new MotechEvent(RESCHEDULE_VISIT, data);
                  RepeatingSchedulableJob repeatingSchedulableJob;
                  repeatingSchedulableJob = new RepeatingSchedulableJob(event,
                              startDate.plusMillis(timeDifference * DIFFERENCE)
                                          .toDate(), ENDDATE.toDate(),
                              INTERVALINMILLISECONDS, true);
                  RunOnceSchedulableJob runOnceSchedulableJob = new RunOnceSchedulableJob(
                              event, new DateTime().plusMinutes(2).toDate());
                  scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
                  scheduler.safeScheduleRepeatingJob(repeatingSchedulableJob);
                  timeDifference++;
            }
      }

}