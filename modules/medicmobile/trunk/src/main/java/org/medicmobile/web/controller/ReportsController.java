package org.medicmobile.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.medicmobile.dto.PerformanceReport;
import org.medicmobile.dto.PerformanceReportSearch;
import org.medicmobile.dto.ReportSearch;
import org.medicmobile.dto.ReportUpdates;
import org.medicmobile.dto.VisitUpdate;
import org.medicmobile.dto.WeeklyReport;
import org.medicmobile.model.AnganwadiCenterView;
import org.medicmobile.service.AnganwadiCenterService;
import org.medicmobile.service.PerformanceReportService;
import org.medicmobile.service.WeeklyReportService;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The Class ReportsController.
 */
@SuppressWarnings("unchecked")
@Controller
public class ReportsController {

      private static final String ANGANWADI_ID = "anganwadiID";
      private static final String ANGANWADI_CENTER_LIST = "anganwadiCenterList";
      private static final String VISIT_DATE = "visitDate";
      /** The weekly report service. */
      @Resource
      private WeeklyReportService weeklyReportService;

      /** The anganwadi center service. */
      @Resource(name = "anganwadiCenterService")
      private AnganwadiCenterService anganwadiCenterService;

      /** The performance report service. */
      @Autowired
      private PerformanceReportService performanceReportService;

      /**
       * Gets the weekly report.
       * 
       * @param reportSearch
       *              the report search
       * @return the weekly report
       */
      @RequestMapping(value = "/weeklyReport", method = RequestMethod.POST)
      @ResponseBody
      public WeeklyReport getWeeklyReport(ReportSearch reportSearch) {
            return weeklyReportService.getWeeklyReport(reportSearch);

      }

      /**
       * Update from report.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/getWeeklyReport", method = RequestMethod.POST)
      public ModelAndView updateFromReport(HttpServletRequest request) {
            Map<String, String[]> paramsMap = request.getParameterMap();
            ReportUpdates reportUpdates = new ReportUpdates();
            if (paramsMap.containsKey(VISIT_DATE)) {
                  reportUpdates.setVisitDate(paramsMap.get(VISIT_DATE)[0]);
            }
            if (paramsMap.containsKey("updates")) {
                  reportUpdates.setUpdates(Arrays.asList(paramsMap
                              .get("updates")));
            }
            weeklyReportService.updateFromReport(reportUpdates);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("weeklyReport");
            modelAndView.addObject(ANGANWADI_CENTER_LIST,
                        anganwadiCenterService.findAllAnganwadiCenter());
            if (paramsMap.containsKey(ANGANWADI_ID)) {
                  modelAndView.addObject(ANGANWADI_ID,
                              paramsMap.get(ANGANWADI_ID));
            }
            if (paramsMap.containsKey("reportsDate")) {
                  modelAndView.addObject("reportsDate",
                              paramsMap.get("reportsDate"));
            }
            if (paramsMap.containsKey(VISIT_DATE)) {
                  modelAndView.addObject(VISIT_DATE,
                              paramsMap.get(VISIT_DATE)[0]);
            }
            modelAndView.addObject("userName", request.getUserPrincipal()
                        .getName());
            return modelAndView;
      }

      /**
       * Gets the performance report.
       * 
       * @param search
       *              the search
       * @return the performance report
       */
      @RequestMapping(value = "/performanceReport", method = RequestMethod.POST)
      @ResponseBody
      public PerformanceReport getPerformanceReport(
                  PerformanceReportSearch search) {
            return performanceReportService.getPerformanceReport(search);
      }

      /**
       * Weekly report.
       * 
       * @param request
       *              the request
       * @return the model and view
       */
      @RequestMapping(value = "/getWeeklyReport", method = RequestMethod.GET)
      public ModelAndView weeklyReport(HttpServletRequest request) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("weeklyReport");
            modelAndView.addObject("userName", request.getUserPrincipal()
                        .getName());
            modelAndView.addObject(ANGANWADI_CENTER_LIST,
                        anganwadiCenterService.findAllAnganwadiCenter());
            return modelAndView;
      }

      /**
       * Update vaccination for child.
       * 
       * @param update
       *              the update
       * @param childID
       *              the child id
       * @return the model and view
       */
      @RequestMapping(value = "/updateVaccination/{childID}")
      @ResponseBody
      public RedirectView updateVaccinationForChild(HttpServletRequest request,
                  @PathVariable("childID") String childID) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String key : parameterMap.keySet()) {
                  VisitUpdate update = new VisitUpdate();
                  update.setVaccineID(key);
                  update.setVisitDate(parameterMap.get(key)[0]);
                  weeklyReportService.update(update);
            }
            return new RedirectView(
                        "/motech-platform-server/module/medicmobile/api/viewChild/"
                                    + childID);
      }

      /**
       * Schedule anm visit.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @RequestMapping(value = "/triggerSMS", method = RequestMethod.POST)
      public ModelAndView scheduleAnmVisit(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            Map<String, Object[]> scheduleMap = request.getParameterMap();
            String anganwadiID = (String) scheduleMap.get(ANGANWADI_ID)[0];
            DateTime date = new DateTime(
                        UtilService.stringToDate((String) scheduleMap
                                    .get("date")[0]));
            weeklyReportService.scheduleSMSToAnganwadi(anganwadiID, date);
            modelAndView.setViewName("trigger-sms");
            modelAndView.addObject("previousObject", anganwadiID);
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(ANGANWADI_CENTER_LIST, anganwadiCenterViews);
            return modelAndView;
      }

      @RequestMapping(value = "/triggerSMS", method = RequestMethod.GET)
      public ModelAndView scheduleANMVisitGet(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(MedicMobileConstants.USER_NAME, request
                        .getUserPrincipal().getName());
            modelAndView.setViewName("trigger-sms");
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(ANGANWADI_CENTER_LIST, anganwadiCenterViews);
            return modelAndView;
      }

      @RequestMapping(value = "/reschedule", method = RequestMethod.GET)
      public ModelAndView reschedule(HttpServletRequest request,
                  HttpServletResponse response) {
            weeklyReportService.rescheduleAppointmentDailyJob();
            return null;
      }

      /**
       * Schedule anm visit get.
       * 
       * @param request
       *              the request
       * @param response
       *              the response
       * @return the model and view
       */
      @RequestMapping(value = "/schedule", method = RequestMethod.GET)
      public ModelAndView scheduleAnmVisitGet(HttpServletRequest request,
                  HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("schedule");
            modelAndView.addObject("previousObject", "");
            List<AnganwadiCenterView> anganwadiCenterViews = anganwadiCenterService
                        .findAllAnganwadiCenter();
            modelAndView.addObject(ANGANWADI_CENTER_LIST, anganwadiCenterViews);

            return modelAndView;
      }
}