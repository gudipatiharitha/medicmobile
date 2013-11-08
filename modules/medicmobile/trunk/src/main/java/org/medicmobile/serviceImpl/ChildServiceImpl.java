package org.medicmobile.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.medicmobile.dto.ScheduleObject;
import org.medicmobile.map.MapToCareGiver;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.Child;
import org.medicmobile.model.ChildView;
import org.medicmobile.model.MasterVaccine;
import org.medicmobile.model.VaccineSchedule;
import org.medicmobile.repository.ChildRepository;
import org.medicmobile.repository.MasterVaccineRepository;
import org.medicmobile.repositoryImpl.SmsStartCodeRepositoryImpl;
import org.medicmobile.service.CareGiverService;
import org.medicmobile.service.ChildService;
import org.medicmobile.util.UtilService;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.scheduler.MotechSchedulerService;
import org.motechproject.scheduler.domain.RepeatingSchedulableJob;
import org.motechproject.server.config.SettingsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ChildServiceImpl.
 */
@Service(value = "childService")
public final class ChildServiceImpl implements ChildService {

      private static final String GENERATE_CHILD_SYSTEM_ID = "generate_child_system_id";
      /** The care giver service. */
      @Resource(name = "careGiverService")
      private CareGiverService careGiverService;

      /** The child repo. */
      @Resource(name = "childRepo")
      private ChildRepository childRepo;

      @Resource(name = "vaccineOrderSettings")
      private SettingsFacade vaccineOrderSettings;

      /** The schedule. */
      @Autowired
      private AppointmentService appointmentService;

      @Resource(name = "masterVaccine")
      private MasterVaccineRepository masterVaccineRepository;

      @Autowired
      private MotechSchedulerService scheduler;

      @Autowired
      private SmsStartCodeRepositoryImpl smsCodeRepo;

      /**
       * Adds the child.
       * 
       * @param childList
       *              the child list
       * @return the list
       */

      public List<Child> addChild(List<Child> childList) {
            for (Child child : childList) {
                  childRepo.addChild(child);

            }
            return childList;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#addChild(org.medicmobile.model
       * .Child )
       */
      public Child addChild(Child child) {
            childRepo.addChild(child);
            return child;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByChildID(java.lang.String)
       */
      public List<ChildView> findByChildID(String childID) {
            List<Child> listChild = childRepo.findByChildID(childID);

            ChildView childView;
            if (!listChild.isEmpty()) {

                  Child current = listChild.get(0);
                  CareGiverView careGiver = careGiverService
                              .findByCareGiverID(current.getCareGiverID());
                  List<VisitResponse> childVisitResponses = appointmentService
                              .getAllVisits(current.getChildCalendarID());
                  List<ScheduleObject> scheduleObject = MapToCareGiver
                              .toScheduleObject(childVisitResponses);
                  List<MasterVaccine> masterVaccineAll = masterVaccineRepository
                              .getAll();
                  List<VaccineSchedule> vaccineScheduleTempList;
                  for (ScheduleObject scheduleObjectTemp : scheduleObject) {
                        for (MasterVaccine masterVaccineTemp : masterVaccineAll) {
                              vaccineScheduleTempList = masterVaccineRepository
                                          .findByVaccineName(
                                                      masterVaccineTemp
                                                                  .getVaccineName())
                                          .get(0).getVaccineSchedule();
                              for (VaccineSchedule vaccineTemp : vaccineScheduleTempList) {
                                    if (scheduleObjectTemp.getName().equals(
                                                vaccineTemp.getVaccineID())) {
                                          scheduleObjectTemp
                                                      .setIsBeforeCompulsory(vaccineTemp
                                                                  .getIsBeforeCompulsory());
                                          scheduleObjectTemp
                                                      .setGapInWeeks(vaccineTemp
                                                                  .getGapInWeeks());
                                    }
                              }
                        }
                  }
                  childView = new ChildView(careGiver, current, scheduleObject,
                              vaccineOrderSettings);
                  return Arrays.asList(childView);
            }
            return new ArrayList<ChildView>();
      }

      public Child getChildByID(String childID) {
            List<Child> children = childRepo.findByChildID(childID);
            if (children.isEmpty()) {
                  return new Child();
            } else {
                  return children.get(0);
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByChildName(java.lang.String)
       */
      public List<ChildView> findByChildName(String childName) {
            return this.getListOfChildView(childRepo.findByName(childName));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByCareGiverName(java.lang.
       * String )
       */
      public List<ChildView> findByCareGiverName(String careGiverName) {
            return this.getListOfChildView(childRepo
                        .findByCareGiverName(careGiverName));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByDateOfBirth(java.util.Date)
       */
      public List<ChildView> findByDateOfBirth(Date dateOfBirth) {
            return this.getListOfChildView(childRepo
                        .findByDateOfBirth(dateOfBirth));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByDateOfBirthRange(java.util
       * .Date, java.util.Date)
       */
      public List<ChildView> findByDateOfBirthRange(Date startDate, Date endDate) {
            return this.getListOfChildView(childRepo.findByDateOfBirthRange(
                        startDate, endDate));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.ChildService#findByAllConstraints(java.util
       * .Map)
       */
      public List<ChildView> findByAllConstraints(
                  Map<String, Object> constraints) {
            return this.getListOfChildView(childRepo
                        .findByAllConstraints(constraints));
      }

      /**
       * Find by care giver id.
       * 
       * @param careGiverID
       *              the care giver id
       * @return the list
       */
      public List<Child> findByCareGiverID(String careGiverID) {
            return childRepo.findByCareGiverID(careGiverID);
      }

      public List<Child> findByChildSystemId() {
            return childRepo.findByChildSystemId();
      }

      /**
       * Gets the list of child view.
       * 
       * @param childList
       *              the child list
       * @return the list of child view
       */
      public List<ChildView> getListOfChildView(List<Child> childList) {
            return new ArrayList<ChildView>();
      }

      /**
       * Gets the empty child.
       * 
       * @return the empty child
       */
      public Child getEmptyChild() {
            return new Child();
      }

      @Override
      public void deleteChild(Child child) {
            childRepo.deleteChild(child.getChildID());
            appointmentService.removeCalendar(child.getChildCalendarID());
      }

      @Override
      public void deleteChild(String childID) {
            Child child = childRepo.findByChildID(childID).get(0);
            childRepo.deleteChild(childID);
            appointmentService.removeCalendar(child.getChildCalendarID());
      }

//      @PostConstruct
      public void generateChildSystemIdEvent() {
            scheduler.unscheduleAllJobs(GENERATE_CHILD_SYSTEM_ID);
            DateTime startDate = new DateTime();
            startDate.plusDays(1);
            startDate = new DateTime(startDate.getYear(),
                        startDate.getMonthOfYear(), startDate.getDayOfMonth(),
                        0, 0, 0);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put(MotechSchedulerService.JOB_ID_KEY,
                        UtilService.generateUniqueID());
            MotechEvent event = new MotechEvent(
                        ChildServiceImpl.GENERATE_CHILD_SYSTEM_ID, data);
            RepeatingSchedulableJob repeatingSchedulableJob = new RepeatingSchedulableJob(
                        event, new DateTime().plusMillis(120000).toDate(),
                        WeeklyReportServiceImpl.ENDDATE.toDate(), 120000L, true);
            scheduler.safeScheduleRepeatingJob(repeatingSchedulableJob);
      }

      @MotechListener(subjects = ChildServiceImpl.GENERATE_CHILD_SYSTEM_ID)
      public void generateChildSystemIdEventListener(MotechEvent event) {
            List<Child> childList = this.findByChildSystemId();
            int startCode = smsCodeRepo.getSmsStartCode();
            if (childList != null && !childList.isEmpty()) {
                  for (Child child : childList) {
                        if (child.getChildSystemId() == null
                                    || "".equals(child.getChildSystemId())) {
                              child.setChildSystemId(startCode + "");
                              childRepo.update(child);
                              smsCodeRepo.setSmsStartCode();
                              startCode = smsCodeRepo.getSmsStartCode();
                        }
                  }
            }
      }
}
