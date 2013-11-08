package org.medicmobile.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.medicmobile.dto.CareGiverSearch;
import org.medicmobile.dto.SendSmsObject;
import org.medicmobile.dto.StaffSearch;
import org.medicmobile.exceptions.IDNotAvailableException;
import org.medicmobile.map.MapToCareGiver;
import org.medicmobile.map.MapToSendSMS;
import org.medicmobile.map.MaptoChild;
import org.medicmobile.model.AnganwadiCenter;
import org.medicmobile.model.AnganwadiCenterView;
import org.medicmobile.model.BloodGroup;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.Child;
import org.medicmobile.model.ChildView;
import org.medicmobile.model.DetailedPerson;
import org.medicmobile.model.Education;
import org.medicmobile.model.IDCardType;
import org.medicmobile.model.Language;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.service.AnganwadiCenterService;
import org.medicmobile.serviceImpl.CareGiverServiceImpl;
import org.medicmobile.serviceImpl.ChildServiceImpl;
import org.medicmobile.serviceImpl.StaffServiceImpl;
import org.medicmobile.serviceImpl.VaccineServiceImpl;
import org.medicmobile.sms.SendSMS;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.motechproject.sms.api.service.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The Class DataController.
 */
@Controller
public class DataController {
    
    private static Logger logger = Logger.getLogger(DataController.class);

      private static final String CAREGIVER_RESPONSE = "careGiverResponse";
      private static final String PERSONNEL_LIST = "personnelList";
      private static final String UTF_8 = "UTF-8";
      /** The staff service. */
      @Autowired
      private StaffServiceImpl staffService;

      /** The care giver service. */
      @Autowired
      private CareGiverServiceImpl careGiverService;

      /** The child service. */
      @Autowired
      private ChildServiceImpl childService;

      /** The anganwadi center service. */
      @Autowired
      private AnganwadiCenterService anganwadiCenterService;

      /** The send sms. */
      @Autowired
      private SendSMS sendSms;

      /** The vaccine service impl. */
      @Autowired
      private VaccineServiceImpl vaccineServiceImpl;

      /** The map to send sms. */
      @Autowired
      private MapToSendSMS mapToSendSms;

      /**
       * Adds the staff.
       * 
       * @param staffReceived
       *              the staff received
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/staff", method = RequestMethod.POST)
      public ModelAndView addStaff(Staff staffReceived,
                  HttpServletRequest request) {
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            Staff staff = staffService.addStaff(staffReceived);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(MedicMobileConstants.STAFF);
            modelAndView.addObject(MedicMobileConstants.STAFF, staff);
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject(MedicMobileConstants.ALL_ROLES,
                        Role.values());
            modelAndView.addObject(MedicMobileConstants.ALL_LANGUAGES,
                        Language.values());
            modelAndView.addObject(MedicMobileConstants.ALL_EDUCATION,
                        Education.values());
            return modelAndView;
      }

      /**
       * View staff.
       * 
       * @param staffID
       *              the staff id
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/staff/{staffID}", method = RequestMethod.GET)
      public ModelAndView viewStaff(@PathVariable("staffID") String staffID,
                  HttpServletRequest request) {
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            ModelAndView modelAndView = new ModelAndView();
            Staff staff = null;
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.setViewName(MedicMobileConstants.STAFF);
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject(MedicMobileConstants.ALL_ROLES,
                        Role.values());
            modelAndView.addObject(MedicMobileConstants.ALL_LANGUAGES,
                        Language.values());
            modelAndView.addObject(MedicMobileConstants.ALL_EDUCATION,
                        Education.values());
            try {
                  staff = staffService.findByStaffID(staffID).get(0);
            } catch (IDNotAvailableException exception) {
                  return addStaff(request);
            }
            modelAndView.addObject(MedicMobileConstants.STAFF, staff);
            return modelAndView;
      }

      /**
       * Adds the staff.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/staff", method = RequestMethod.GET)
      public ModelAndView addStaff(HttpServletRequest request) {
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(MedicMobileConstants.STAFF);
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ALL_ROLES,
                        Role.values());
            modelAndView.addObject(MedicMobileConstants.ALL_LANGUAGES,
                        Language.values());
            modelAndView.addObject(MedicMobileConstants.ALL_EDUCATION,
                        Education.values());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            return modelAndView;
      }

      /**
       * Gets the care giver form.
       * 
       * @param request
       *              the request
       * @return the care giver form
       */
      @RequestMapping(value = "/CareGiver", method = RequestMethod.GET)
      public ModelAndView getCareGiverForm(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject(MedicMobileConstants.ALL_BLOOD_GROUPS,
                        BloodGroup.values());
            modelAndView.addObject(MedicMobileConstants.ALL_ID_CARD_TYPES,
                        IDCardType.values());
            modelAndView.setViewName(CAREGIVER_RESPONSE);
            response.setCharacterEncoding(UTF_8);
            return modelAndView;
      }

      /**
       * View care giver.
       * 
       * @param careGiverID
       *              the care giver id
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/CareGiver/{careGiverID}", method = RequestMethod.GET)
      public ModelAndView viewCareGiver(@PathVariable String careGiverID,
                  HttpServletRequest request, HttpServletResponse response) {
            try {
                  request.setCharacterEncoding(UTF_8);
            } catch (UnsupportedEncodingException e) {
                logger.debug(e.getMessage());
            }
            ModelAndView modelAndView = new ModelAndView();
            CareGiverView careGiverView = careGiverService
                        .findByCareGiverID(careGiverID);
            modelAndView.setViewName(CAREGIVER_RESPONSE);
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject("careGiverView", careGiverView);
            List<Child> childViewList = childService
                        .findByCareGiverID(careGiverID);
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject("childList", childViewList);
            modelAndView.addObject(MedicMobileConstants.ALL_BLOOD_GROUPS,
                        BloodGroup.values());
            modelAndView.addObject(MedicMobileConstants.ALL_ID_CARD_TYPES,
                        IDCardType.values());
            response.setCharacterEncoding(UTF_8);
            return modelAndView;
      }

      /**
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @SuppressWarnings("unchecked")
      @RequestMapping(value = "/CareGiver", method = RequestMethod.POST)
      public ModelAndView saveCareGiver(HttpServletRequest request,
                  HttpServletResponse response) {
            try {
                  request.setCharacterEncoding(UTF_8);
            } catch (UnsupportedEncodingException e) {
                  logger.debug(e.getMessage());
            }
            ModelAndView modelAndView = new ModelAndView();
            Map<Object, Object[]> paramsMap = request.getParameterMap();
            CareGiver careGiver = MapToCareGiver.mapToCareGiver(paramsMap);
            CareGiverView careGiverView = careGiverService
                        .addCareGiver(careGiver);
            modelAndView.addObject("careGiverView", careGiverView);
            List<Child> childList = MapToCareGiver.mapToChildList(paramsMap,
                        careGiverView.getCareGiver().getCareGiverID());
            List<Child> childViewList = childService.addChild(childList);
            modelAndView.addObject("childList", childViewList);
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            vaccineServiceImpl.createAppointmentSchedule(childViewList);
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.setViewName(CAREGIVER_RESPONSE);
            return modelAndView;
      }

      /**
       * Gets the care givers.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the care givers
       */
      @RequestMapping(value = "/getCareGivers")
      @ResponseBody
      public Map<String, List<CareGiverView>> getCareGivers(
                  HttpServletRequest request, HttpServletResponse response) {
            Map<String, Object[]> searchData = request.getParameterMap();
            Map<String, List<CareGiverView>> careGiverList = new HashMap<String, List<CareGiverView>>();
            List<CareGiverView> list = careGiverService
                        .findByAnganwadiID((String) searchData
                                    .get("anganwadiID")[0]);
            List<CareGiverView> tempCareGiverList = new ArrayList<CareGiverView>();
            for (CareGiverView current : list) {
                  if (current.getCareGiver().getPhoneNumber() != null
                              && !current.getCareGiver().getPhoneNumber()
                                          .equals("")) {
                        tempCareGiverList.add(current);
                  }
            }
            careGiverList.put("careGiverList", tempCareGiverList);
            return careGiverList;
      }

      /**
       * View staff.
       * 
       * @param staffSearch
       *              the staff search
       * @return the map
       */
      @RequestMapping(value = "/findStaff")
      @ResponseBody
      public Map<String, List<Staff>> viewStaff(StaffSearch staffSearch) {
            Map<String, List<Staff>> staffList = new HashMap<String, List<Staff>>();
            if (staffSearch.getAnganwadiID() == null
                        && staffSearch.getEmployeeID() == null
                        && staffSearch.getStaffRole() == null
                        && staffSearch.getStaffName() == null) {
                  staffList.put(PERSONNEL_LIST, staffService.findAllStaff());
            } else {
                  staffList.put(PERSONNEL_LIST,
                              staffService.findByAllConstraints(staffSearch));
            }
            return staffList;
      }

      @RequestMapping(value = "/findStaffById")
      @ResponseBody
      public Map<String, List<Staff>> getStaffByID(StaffSearch staffSearch) {
            Map<String, List<Staff>> staffList = new HashMap<String, List<Staff>>();
            if (staffSearch.getStaffID() == null) {
                  staffList.put(PERSONNEL_LIST, Arrays.asList(new Staff()));
            } else {
                  staffList.put(PERSONNEL_LIST,
                              staffService.findByAllConstraints(staffSearch));
            }
            return staffList;
      }

      /**
       * Adds the anganwadi center.
       * 
       * @param anganwadiCenterSent
       *              the anganwadi center sent
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/AnganwadiCenter", method = RequestMethod.POST)
      public ModelAndView addAnganwadiCenter(
                  AnganwadiCenter anganwadiCenterSent,
                  HttpServletRequest request) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            AnganwadiCenter anganwadiCenter = anganwadiCenterService
                        .addAnganwadiCenter(anganwadiCenterSent);
            modelAndView.setViewName("anganwadiCenterSave");
            modelAndView.addObject("anganwadiCenter", anganwadiCenter);
            return modelAndView;
      }

      /**
       * View anganwadi center.
       * 
       * @param anganwadiCenterSent
       *              the anganwadi center sent
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/AnganwadiCenter/{anganwadiID}", method = RequestMethod.GET)
      public ModelAndView viewAnganwadiCenter(
                  @PathVariable("anganwadiID") String anganwadiID,
                  HttpServletRequest request) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            AnganwadiCenter anganwadiCenter = anganwadiCenterService
                        .findAnganwadiCenterByID(anganwadiID);
            modelAndView.setViewName("anganwadiCenterSave");
            modelAndView.addObject("anganwadiCenter", anganwadiCenter);
            return modelAndView;
      }

      /**
       * Show empty anganwadi center.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/anganwadiCenterForm")
      public ModelAndView showEmptyAnganwadiCenter(HttpServletRequest request) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.setViewName("anganwadiCenterSave");
            return modelAndView;
      }

      /**
       * View anganwadi center.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = { "/viewAnganwadiCenters", "/" })
      public ModelAndView viewAnganwadiCenter(HttpServletRequest request) {
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("anganwadiCentersView");
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            return modelAndView;
      }

      /**
       * View care giver.
       * 
       * @param careGiverSearch
       *              the care giver search
       * @return the object
       */
      @RequestMapping(value = "/viewCareGiver", method = RequestMethod.POST)
      @ResponseBody
      public Object viewCareGiver(CareGiverSearch careGiverSearch) {
            List<CareGiverView> careGiverList = careGiverService
                        .findByAllConstraints(careGiverSearch);
            Map<String, List<CareGiverView>> careGiverMap = new HashMap<String, List<CareGiverView>>();
            careGiverMap.put("careGiverList", careGiverList);
            return careGiverMap;
      }

      /**
       * View care givers.
       * 
       * @param careGiverSearch
       *              the care giver search
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/viewCareGivers")
      public ModelAndView viewCareGivers(CareGiverSearch careGiverSearch,
                  HttpServletRequest request) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("careGiverViewList",
                        careGiverService.findByAllConstraints(careGiverSearch));
            if (careGiverSearch.getAnganwadiID() != null) {
                  modelAndView.addObject("anganwadiCenterIDSelected",
                              careGiverSearch.getAnganwadiID());
            }
            if (careGiverSearch.getLocalVolunteerID() != null) {
                  modelAndView.addObject("localVolunteerSelectedID",
                              careGiverSearch.getLocalVolunteerID());
            }
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.setViewName("careGiverViewList");
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            return modelAndView;
      }

      /**
       * View personnel.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/viewPersonnel")
      public ModelAndView viewPersonnel(HttpServletRequest request) {
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("viewPersonnel");
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject("staffList", staffService.findAllStaff());
            modelAndView.addObject("rolesMap", UtilService.getRolesAsMap());
            modelAndView.addObject(MedicMobileConstants.ALL_ROLES,
                        Role.values());
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());

            return modelAndView;
      }

      /**
       * View vaccine.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @RequestMapping(value = "/viewVaccine")
      public ModelAndView viewVaccine(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("viewVaccine");
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            return modelAndView;
      }

      /**
       * Find care giver.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @param careGiverID
       *              the care giver id
       * @return the model and view
       */
      @RequestMapping(value = "/findCareGiver/{careGiverID}")
      public ModelAndView findCareGiver(HttpServletRequest request,
                  HttpServletResponse response,
                  @PathVariable("careGiverID") String careGiverID) {
            ModelAndView modelAndView = new ModelAndView();
            CareGiverView careGiverView = careGiverService
                        .findByCareGiverID(careGiverID);
            modelAndView.setViewName(CAREGIVER_RESPONSE);
            modelAndView.addObject("careGiverView", careGiverView);
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            return modelAndView;
      }

      /**
       * Gets the local volunteers.
       * 
       * @param anganwadiID
       *              the anganwadi id
       * @return the local volunteers
       */
      @RequestMapping(value = "/getLocalVolunteers")
      @ResponseBody
      public Object getLocalVolunteers(String anganwadiID) {
            Role role = Role.LOCAL_VOLUNTEER;
            List<Staff> staffList;
            if (anganwadiID.equals("all")) {
                  staffList = staffService.findByRole(role);
            } else {
                  staffList = staffService.findByRoleAndAnganwadiID(role,
                              anganwadiID);
            }

            Map<String, List<Staff>> staffMap = new HashMap<String, List<Staff>>();
            staffMap.put("localVolunteerList", staffList);
            return staffMap;
      }

      /**
       * Performance report.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @RequestMapping(value = "/getPerformanceReport")
      public ModelAndView performanceReport(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("performanceReport");
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterService.findAllAnganwadiCenter());

            return modelAndView;
      }

      /**
       * Delete anganwadi center.
       * 
       * @param anganwadiID
       *              the anganwadi id
       * @param request
       *              the request
       * @return the model and view
       */
      @ResponseBody
      @RequestMapping(value = "/deleteAnganwadiCenter", method = RequestMethod.POST)
      public boolean deleteAnganwadiCenter(String anganwadiID) {
            anganwadiCenterService.deleteAnganwadiCenter(anganwadiID);
            return true;
      }

      /**
       * View child.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @param childID
       *              the child id
       * @return the model and view
       */
      @RequestMapping(value = "/viewChild/{childID}", method = RequestMethod.GET)
      public ModelAndView viewChild(HttpServletRequest request,
                  HttpServletResponse response,
                  @PathVariable("childID") String childID) {
            ModelAndView modelAndView = new ModelAndView();
            ChildView childView = childService.findByChildID(childID).get(0);
            modelAndView.setViewName("childView");
            modelAndView.addObject("childView", childView);
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ALL_BLOOD_GROUPS,
                        BloodGroup.values());
            response.setCharacterEncoding(UTF_8);
            return modelAndView;
      }

      /**
       * Save child.
       * 
       * @param child
       *              the child
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/saveChild", method = RequestMethod.POST)
      public RedirectView saveChild(HttpServletRequest request) {
            try {
                  request.setCharacterEncoding(UTF_8);
            } catch (UnsupportedEncodingException e) {
                logger.debug(e.getMessage());
            }
            Map<String, String[]> paramsMap = request.getParameterMap();
            Child child = MaptoChild.mapToChild(paramsMap);
            String externalId = null;
            if (child.getChildID() != null) {
                  List<ChildView> childViewListFromDatabase = childService
                              .findByChildID(child.getChildID());
                  ChildView childViewFromDatabase = null;
                  if (childViewListFromDatabase != null
                              && childViewListFromDatabase.size() > 0) {
                        childViewFromDatabase = childService.findByChildID(
                                    child.getChildID()).get(0);
                        if (child.getDateOfBirth().compareTo(
                                    childViewFromDatabase.getChild()
                                                .getDateOfBirth()) != 0) {
                              externalId = vaccineServiceImpl
                                          .createAppointmentSchedule(child);
                        } else {
                              externalId = child.getChildCalendarID();
                        }
                  }
            }
            child.setChildCalendarID(externalId);
            Child childUpdated = childService.addChild(child);
            return new RedirectView("viewChild/" + childUpdated.getChildID());
      }

      /**
       * Send sms.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @RequestMapping(value = "/sendSMS", method = RequestMethod.GET)
      public ModelAndView sendSMS(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("sendSMS");
            modelAndView.addObject("previousObject", new SendSmsObject());
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.addObject(MedicMobileConstants.ANGANWADI_CENTER_LIST,
                        anganwadiCenterViews);
            modelAndView.addObject(MedicMobileConstants.ALL_ROLES,
                        Role.values());
            response.setCharacterEncoding(UTF_8);
            return modelAndView;
      }

      /**
       * Send sms to local volunteers.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @SuppressWarnings("unchecked")
      @ResponseBody
      @RequestMapping(value = "/sendSMS", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded; charset=UTF-8")
      public Object sendSMSToLocalVolunteers(HttpServletRequest request,
                  HttpServletResponse response) {
            try {
                  request.setCharacterEncoding(UTF_8);
            } catch (UnsupportedEncodingException e) {
                logger.debug(e.getMessage());
            }
            Map<Object, Object[]> paramsMap = request.getParameterMap();
            Map<String, String[]> sendSmsMap = request.getParameterMap();
            SendSmsRequest smsRequest = mapToSendSms.mapSmsObject(sendSmsMap);
            List<DetailedPerson> personList = mapToSendSms.getPersonSMSList();
            if (sendSmsMap.get("language")[0].equals("hindi")) {
                  sendSms.sendSMSEvent(smsRequest);
            } else {
                  mapToSendSms.sendEnglishMessage(smsRequest.getRecipients(),
                              smsRequest.getMessage());
            }
            mapToSendSms.setPersonSMSList(null);
            List<String> personNameList = new ArrayList<String>();
            if (paramsMap.get("toWhom")[0].equals("registered")) {
                  for (DetailedPerson person : personList) {
                        personNameList.add(person.getName());
                  }
            } else if (paramsMap.get("toWhom")[0].equals("notRegistered")) {
                  personNameList.addAll(smsRequest.getRecipients());
            }
            Map<String, List<String>> data = new HashMap<String, List<String>>();
            List<String> messageList = new ArrayList<String>();
            messageList.add(smsRequest.getMessage());
            data.put("previousMessage", messageList);
            data.put("previousPersonList", personNameList);
            return data;
      }

      /**
       * Find all staff.
       * 
       * @return the object
       */
      @RequestMapping(value = "/findAllStaff", method = RequestMethod.POST)
      @ResponseBody
      public Object findAllStaff() {
            Map<String, List<Staff>> staffMap = new HashMap<String, List<Staff>>();
            staffMap.put(PERSONNEL_LIST, staffService.findAllStaff());
            return staffMap;
      }

      /**
       * Find care givers by local volunteer id.
       * 
       * @param localVolunteerID
       *              the local volunteer id
       * @return the object
       */
      @RequestMapping(value = "/findByLocalVolunteerId", method = RequestMethod.POST)
      @ResponseBody
      public Object findCareGiversByLocalVolunteerID(String localVolunteerID) {
            List<CareGiverView> careGiverViewList = careGiverService
                        .findByLocalVolunteerID(localVolunteerID);
            return careGiverViewList.size();
      }

      /**
       * Delete care giver.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @param careGiverID
       *              the care giver id
       * @return the model and view
       */
      @ResponseBody
      @RequestMapping(value = "/deleteCareGiver", method = RequestMethod.POST)
      public boolean deleteCareGiver(String careGiverID) {
            careGiverService.deleteCareGiver(careGiverID);
            return true;
      }

      /**
       * Delete staff.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @param staffID
       *              the staff id
       * @return the model and view
       */
      @ResponseBody
      @RequestMapping(value = "/deleteStaff", method = RequestMethod.POST)
      public boolean deleteStaff(String staffID) {
            staffService.deleteStaff(staffID);
            return true;
      }

      /**
       * Delete child.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @param careGiverID
       *              the care giver id
       * @return the model and view
       */
      @ResponseBody
      @RequestMapping(value = "/deleteChild", method = RequestMethod.POST)
      public boolean deleteChild(String childID) {
            childService.deleteChild(childID);
            return true;
      }

}