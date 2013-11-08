package org.medicmobile.web.controller;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.DetailedPerson;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.serviceImpl.CareGiverServiceImpl;
import org.medicmobile.serviceImpl.StaffServiceImpl;
import org.medicmobile.util.UtilService;
import org.motechproject.decisiontree.core.DecisionTreeService;
import org.motechproject.decisiontree.core.EventKeys;
import org.motechproject.decisiontree.core.FlowSession;
import org.motechproject.decisiontree.core.model.AudioPrompt;
import org.motechproject.decisiontree.core.model.Node;
import org.motechproject.decisiontree.core.model.RecordPrompt;
import org.motechproject.decisiontree.core.model.TextToSpeechPrompt;
import org.motechproject.decisiontree.core.model.Transition;
import org.motechproject.decisiontree.core.model.Tree;
import org.motechproject.decisiontree.server.service.FlowSessionService;
import org.motechproject.event.MotechEvent;
import org.motechproject.scheduler.MotechSchedulerService;
import org.motechproject.scheduler.domain.RunOnceSchedulableJob;
import org.motechproject.server.config.SettingsFacade;
import org.motechproject.server.kookoo.web.KookooIVRControllerInterface;
import org.motechproject.server.kookoo.web.KookooIvrController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

// : Auto-generated Javadoc
/**
 * The Class IVRController.
 */
@Controller
public class IVRController {

    private static final String UNABLE_FETCH_MESSAGE = "Unable to fetch the message";
    /** The logger. */
    private static Logger logger = Logger.getLogger(IVRController.class);

    private static final String TREE = "tree";

    /** The Constant MEDIC_MOBILE_URL. */
    private static final String MEDIC_MOBILE_URL = "medicmobile.url";

    /** The Constant CALL_WITH_RECORDED_AUDIO. */
    public static final String CALL_WITH_RECORDED_AUDIO = "org.medicmobile.ivr.callwithrecordedaudio";

    /** The medicmobile url. */
    private String medicmobileURL = "";

    /** The Constant TEMPLATE_BASE_PATH. */
    public static final String TEMPLATE_BASE_PATH = "/vm/";

    /** The Constant RECORD_TEMPLATE_FOR_LOCAL_VOLUNTEER. */
    public static final String RECORD_TEMPLATE_FOR_LOCAL_VOLUNTEER = TEMPLATE_BASE_PATH
            + "record-kookoo-local-volunteer";

    /** The Constant RECORD_TEMPLATE_FOR_ANM. */
    public static final String RECORD_TEMPLATE_FOR_ANM = TEMPLATE_BASE_PATH
            + "record-kookoo-anm";

    private static final String PHONE_NUMBER = "phone_no";

    private static final String STATUS_DETAILS = "status_details";

    private static final String STATUS = "status";

    public static final String KOOKOO_SID = "sid";

    public static final String MAX_NUMBER_OF_ATTEMPTS = "max.number.of.attempts";

    public static final String MAX_GAP_BETWEEN_CALLS_IN_MINUTES = "max.gap.in.minutes.between.consecutive.calls";

    public static final String MAX_GAP_BETWEEN_TWO_DIFFERENT_CALL_IN_MIN = "max.gap.in.minutes.between.two.persons";

    private int maxGapBetweenConsecutiveCalls;

    private int maxNumberOfAttempts;

    private int maxGapBetweenTwoDifferentCalls;

    public static final String RECORDED_AUDIO_URL = "recordedAudioUrl";

    public static final String NUMBER_OF_TIMES = "number_of_times";

    /** The kookoo ivr controller. */
    @Autowired
    private KookooIVRControllerInterface kookooIvrController;

    /** The decision tree service. */
    @Autowired
    private DecisionTreeService decisionTreeService;

    /** The staff service. */
    @Autowired
    private StaffServiceImpl staffService;

    /** The flow session service. */
    @Autowired
    private FlowSessionService flowSessionService;

    /** The scheduler. */
    @Autowired
    private MotechSchedulerService scheduler;

    /** The care giver service. */
    @Autowired
    private CareGiverServiceImpl careGiverService;

    /**
     * Instantiates a new iVR controller.
     * 
     * @param settings
     *            the settings
     */
    @Autowired
    public IVRController(
            @Qualifier("medicmobileURLSettings") SettingsFacade settings) {
        medicmobileURL = settings.getProperty(MEDIC_MOBILE_URL);
        maxNumberOfAttempts = Integer.parseInt(settings
                .getProperty(MAX_NUMBER_OF_ATTEMPTS));
        maxGapBetweenConsecutiveCalls = Integer.parseInt(settings
                .getProperty(MAX_GAP_BETWEEN_CALLS_IN_MINUTES));
        maxGapBetweenTwoDifferentCalls = Integer.parseInt(settings
                .getProperty(MAX_GAP_BETWEEN_TWO_DIFFERENT_CALL_IN_MIN));
    }

    private String handleNewCall(String intree, String inphoneNumber) {

        String tree = intree;
        String phoneNumber = "0" + inphoneNumber;
        List<Staff> staffFromSearchByPhoneNumber = staffService
                .findByPhoneNumber(phoneNumber);
        if (staffFromSearchByPhoneNumber != null
                && staffFromSearchByPhoneNumber.size() > 0) {
            Role staffRole = staffFromSearchByPhoneNumber.get(0).getRole();
            if (staffFromSearchByPhoneNumber != null
                    && staffFromSearchByPhoneNumber.size() != 0
                    && !staffRole.equals(Role.AUXILIARY_NURSING_MOTHER)) {
                tree = "MedicMobile";
            } else {
                tree = "Education";
            }
        } else {
            tree = "Education";
        }
        return tree;
    }

    private List<DetailedPerson> getDetailedPersonListLV(
            Staff staffHavingPhoneNumber) {
        List<CareGiverView> careGiverViewList = careGiverService
                .findByLocalVolunteerID(staffHavingPhoneNumber.getStaffID());
        List<DetailedPerson> detailedPersonList = new ArrayList<DetailedPerson>();
        for (CareGiverView careGiverView : careGiverViewList) {
            detailedPersonList.add((DetailedPerson) careGiverView
                    .getCareGiver());
        }
        return detailedPersonList;
    }

    private List<DetailedPerson> getDetailedPersonListAW(String transitionKey,
            Staff staffHavingPhoneNumber) {
        List<DetailedPerson> detailedPersonList = new ArrayList<DetailedPerson>();
        List<Staff> staffList;
        List<CareGiverView> careGiverViewList;
        if (transitionKey.equals("1")) {
            careGiverViewList = careGiverService
                    .findByAnganwadiID(staffHavingPhoneNumber.getAnganwadiID()
                            .get(0));
            staffList = staffService.findByRoleAndAnganwadiID(
                    Role.LOCAL_VOLUNTEER, staffHavingPhoneNumber
                            .getAnganwadiID().get(0));
            for (CareGiverView careGiverView : careGiverViewList) {
                if (careGiverView.getCareGiver().getPhoneNumber() != null) {
                    detailedPersonList.add(careGiverView.getCareGiver());
                }
            }
            for (Staff staff : staffList) {
                detailedPersonList.add(staff);
            }
        } else if (transitionKey.equals("2")) {
            staffList = staffService.findByRoleAndAnganwadiID(
                    Role.LOCAL_VOLUNTEER, staffHavingPhoneNumber
                            .getAnganwadiID().get(0));
            for (Staff staff : staffList) {
                detailedPersonList.add(staff);
            }
        }
        return detailedPersonList;
    }

    private ModelAndView getModelAndViewAW() {
        ModelAndView modelAndView = new ModelAndView(RECORD_TEMPLATE_FOR_ANM);
        modelAndView.addObject("provider", "kookoo");
        modelAndView.addObject("escape", new StringEscapeUtils());
        modelAndView.addObject("maxDigits", 1);
        modelAndView.addObject("maxTimeout", 5000);
        modelAndView.addObject("role", Role.ANGANWADI_WORKER.getValue());
        modelAndView.addObject("transitionKeyEndMarker", "#");
        return modelAndView;
    }

    private String getRecordOutputString(HttpServletRequest request){
        String recordedAudioUrl = request.getParameter("data");
        String kookooSid = request.getParameter(KOOKOO_SID);
        String phoneNumber = request.getParameter("cid");
        String tree = request.getParameter(TREE);
        FlowSession flowSession = flowSessionService.findOrCreate(
                kookooSid, phoneNumber);
        String phoneNumberAfterRecord = flowSession.getPhoneNumber();
        phoneNumberAfterRecord = "0" + phoneNumberAfterRecord;
        Staff staffHavingPhoneNumber = staffService.findByPhoneNumber(
                phoneNumberAfterRecord).get(0);
        if (staffHavingPhoneNumber.getRole().equals(Role.LOCAL_VOLUNTEER)) {
            List<DetailedPerson> detailedPersonList = getDetailedPersonListLV(staffHavingPhoneNumber);

            handleRecordAudio(recordedAudioUrl,
                    getPhoneNumberList(detailedPersonList), 3);
            ModelAndView modelAndView = new ModelAndView(
                    RECORD_TEMPLATE_FOR_LOCAL_VOLUNTEER);
            return getOutPutString(modelAndView, IVRController.class);
        } else if (staffHavingPhoneNumber.getRole().equals(
                Role.ANGANWADI_WORKER)) {
            ModelAndView modelAndView = getModelAndViewAW();
            modelAndView.addObject(TREE, tree);
            modelAndView.addObject("recordedAudioUrl", recordedAudioUrl);
            modelAndView.addObject("language", flowSession.getLanguage());
            modelAndView.addObject("contextPath", request.getContextPath());
            modelAndView.addObject("servletPath", request.getServletPath());
            modelAndView.addObject("host", request.getHeader("Host"));
            modelAndView.addObject("scheme", request.getScheme());
            return getOutPutString(modelAndView, IVRController.class);
        }
        return null;
        
    }
    /**
     * Ivr call back.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the object
     */
    @ResponseBody
    @RequestMapping(value = "/ivr")
    public Object ivrCallBack(HttpServletRequest request,
            HttpServletResponse response) {
        if ("NewCall".equals(request.getParameter("event"))) {
            String tree = (String) request.getParameter(TREE);
            String phoneNumber = (String) request.getParameter("cid");
            tree = handleNewCall(tree, phoneNumber);
            request.setAttribute(TREE, tree);
        } else if ("Record".equals(request.getParameter("event"))) {
            String outputString = getRecordOutputString(request);
            if(outputString != null) {
                return outputString;
            }
            
        } else if ("GotDTMF".equals(request.getParameter("event"))) {
            String role = request.getParameter("role");
            if (role != null
                    && (role.equals(Role.ANGANWADI_WORKER.getValue()) || role
                            .equals(Role.LOCAL_VOLUNTEER.getValue()))) {

                String transitionKey = request.getParameter("data");

                String kookooSid = request.getParameter(KOOKOO_SID);
                String phoneNumber = request.getParameter("cid");
                String recordedAudioUrl = request
                        .getParameter("recordedAudioUrl");
                FlowSession flowSession = flowSessionService.findOrCreate(
                        kookooSid, phoneNumber);
                String phoneNumberAfterRecord = flowSession.getPhoneNumber();
                phoneNumberAfterRecord = "0" + phoneNumberAfterRecord;
                Staff staffHavingPhoneNumber = staffService.findByPhoneNumber(
                        phoneNumberAfterRecord).get(0);

                if (role.equals(Role.ANGANWADI_WORKER.getValue())) {
                    List<DetailedPerson> detailedPersonList = getDetailedPersonListAW(
                            transitionKey, staffHavingPhoneNumber);
                    handleRecordAudio(recordedAudioUrl,
                            getPhoneNumberList(detailedPersonList), 3);
                    ModelAndView modelAndView = new ModelAndView(
                            RECORD_TEMPLATE_FOR_LOCAL_VOLUNTEER);
                    return getOutPutString(modelAndView, IVRController.class);
                }
            }
        }
        return this.getOutPutString(
                kookooIvrController.ivrCallback(request, response),
                KookooIvrController.class);
    }

    @RequestMapping(value = "/callbackivr")
    public void callBackIVR(HttpServletRequest request,
            HttpServletResponse response) {
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String status = request.getParameter(STATUS);
        String statusDetails = request.getParameter(STATUS_DETAILS);
        String recordedAudioUrl = request.getParameter(RECORDED_AUDIO_URL);
        List<String> phoneNumberList = new ArrayList<String>();
        phoneNumberList.add(phoneNumber);
        int numberOfTimes;
        if ("ring".equals(status)
                && ("Busy".equals(statusDetails) || "NoAnswer"
                        .equals(statusDetails))) {
            if (request.getParameter(NUMBER_OF_TIMES) == null
                    || request.getParameter(NUMBER_OF_TIMES).equals("null")) {
                numberOfTimes = 1;
            } else {
                numberOfTimes = Integer.parseInt(request
                        .getParameter(NUMBER_OF_TIMES)) + 1;

            }
            if (numberOfTimes < maxNumberOfAttempts) {
                handleRecordAudio(recordedAudioUrl, phoneNumberList,
                        maxGapBetweenConsecutiveCalls, numberOfTimes);
            }
        }
    }

    /**
     * Handle record audio.
     * 
     * @param recordedAudioUrl
     *            the recorded audio url
     * @param phoneNumberList
     *            the phone number list
     */
    private void handleRecordAudio(String recordedAudioUrl,
            List<String> phoneNumberList, int minutes) {
        Date date;
        Calendar calendar;
        int tempMinutes = minutes;
        if (phoneNumberList != null && phoneNumberList.size() > 0) {
            for (String phoneNumber : phoneNumberList) {
                date = new Date();
                calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, tempMinutes);
                date = calendar.getTime();
                RunOnceSchedulableJob runOnceSchedulableJob = new RunOnceSchedulableJob(
                        createMotechEvent(recordedAudioUrl, phoneNumber), date);
                scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
                tempMinutes += maxGapBetweenTwoDifferentCalls;
            }
        }
    }

    private void handleRecordAudio(String recordedAudioUrl,
            List<String> phoneNumberList, int minutes, int numberOfTimes) {
        Date date;
        Calendar calendar;
        if (phoneNumberList != null && phoneNumberList.size() > 0) {
            for (String phoneNumber : phoneNumberList) {
                date = new Date();
                calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, minutes);
                date = calendar.getTime();
                RunOnceSchedulableJob runOnceSchedulableJob = new RunOnceSchedulableJob(
                        createMotechEvent(recordedAudioUrl, phoneNumber,
                                numberOfTimes), date);
                scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
            }
        }
    }

    /**
     * Creates the motech event.
     * 
     * @param recordedAudioUrl
     *            the recorded audio url
     * @param phoneNumber
     *            the phone number
     * @return the motech event
     */
    private MotechEvent createMotechEvent(String recordedAudioUrl,
            String phoneNumber) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(EventKeys.PHONE_KEY, phoneNumber);
        paramsMap.put(NUMBER_OF_TIMES, 0);
        paramsMap.put(MotechSchedulerService.JOB_ID_KEY, phoneNumber + "-"
                + UtilService.recordedFileName(recordedAudioUrl));
        paramsMap.put(EventKeys.RECORDED_URL, recordedAudioUrl);
        MotechEvent motechEvent = new MotechEvent(
                IVRController.CALL_WITH_RECORDED_AUDIO, paramsMap);
        return motechEvent;
    }

    private MotechEvent createMotechEvent(String recordedAudioUrl,
            String phoneNumber, int numberOfTimes) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(EventKeys.PHONE_KEY, phoneNumber);
        paramsMap.put(NUMBER_OF_TIMES, numberOfTimes);
        paramsMap.put(MotechSchedulerService.JOB_ID_KEY, phoneNumber + "-"
                + UtilService.recordedFileName(recordedAudioUrl));
        paramsMap.put(EventKeys.RECORDED_URL, recordedAudioUrl);
        MotechEvent motechEvent = new MotechEvent(
                IVRController.CALL_WITH_RECORDED_AUDIO, paramsMap);
        return motechEvent;
    }

    /**
     * Gets the phone number list.
     * 
     * @param personList
     *            the person list
     * @return the phone number list
     */
    private List<String> getPhoneNumberList(List<DetailedPerson> personList) {
        String phoneNumber = null;
        List<String> phoneNumberList = new ArrayList<String>();
        if (personList != null && personList.size() > 0) {
            for (DetailedPerson person : personList) {
                phoneNumber = person.getPhoneNumber();
                if (phoneNumber != null && phoneNumber.length() > 0) {
                    phoneNumberList.add(phoneNumber);
                }
            }
        }
        return phoneNumberList;
    }

    /**
     * Gets the out put string.
     * 
     * @param view
     *            the view
     * @param resourceBasicClass
     *            the resource basic class
     * @return the out put string
     */
    private String getOutPutString(ModelAndView view,
            Class<?> resourceBasicClass) {
        try {
            VelocityContext context = new VelocityContext();
            context.put("filename", new Random().nextInt(2147483647));
            Map<String, Object> map = view.getModel();
            if (map != null) {
                Iterator<Entry<String, Object>> iter = map.entrySet()
                        .iterator();
                while (iter.hasNext()) {
                    Entry<String, Object> curr = iter.next();
                    context.put(curr.getKey(), curr.getValue());
                }
            }
            InputStream s = resourceBasicClass.getResourceAsStream(view
                    .getViewName() + ".vm");
            StringWriter writer = new StringWriter();
            IOUtils.copy(s, writer, "UTF-8");
            String vmContent = writer.toString();

            Writer outwriter = new StringWriter();
            String logC = "";
            Velocity.evaluate(context, outwriter, logC, vmContent);
            return outwriter.toString();
        } catch (ResourceNotFoundException e) {
            logger.info(e.getMessage());
        } catch (ParseErrorException e) {
            logger.info(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return "";
    }

    /**
     * Creates the education tree.
     */
    @RequestMapping(value = "/tree/education", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createEducationTree() {
        decisionTreeService
                .saveDecisionTree(new Tree()
                        .setName("Education")
                        .setRootTransition(
                                new Transition().setDestinationNode(new Node()
                                        .setPrompts(
                                                new TextToSpeechPrompt()
                                                        .setMessage("Press 1 to listen in hindi 2 to telugu"))
                                        .setTransitions(
                                                new Object[][] {
                                                        {
                                                                "1",
                                                                new Transition()
                                                                        .setDestinationNode(new Node()
                                                                                .setPrompts(
                                                                                        new AudioPrompt()
                                                                                                .setAudioFileUrl(
                                                                                                        medicmobileURL
                                                                                                                + "/wav/hi/hello.wav")
                                                                                                .setAltMessage(
                                                                                                        UNABLE_FETCH_MESSAGE),
                                                                                        new TextToSpeechPrompt()
                                                                                                .setMessage(
                                                                                                        "Press * to go to previous menu")
                                                                                                .setName(
                                                                                                        "previousmenu"))) },
                                                        {
                                                                "2",
                                                                new Transition()
                                                                        .setDestinationNode(new Node()
                                                                                .setPrompts(
                                                                                        new AudioPrompt()
                                                                                                .setAudioFileUrl(
                                                                                                        medicmobileURL
                                                                                                                + "/wav/te/hello.wav")
                                                                                                .setAltMessage(
                                                                                                        UNABLE_FETCH_MESSAGE),
                                                                                        new TextToSpeechPrompt()
                                                                                                .setMessage("press * to go to previous menu"))) } }))));
    }

    /**
     * Creates the main tree.
     */
    @RequestMapping(value = "/tree/main", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createMainTree() {
        String treeName = "MedicMobile";
        decisionTreeService
                .saveDecisionTree(new Tree()
                        .setName(treeName)
                        .setRootTransition(
                                new Transition().setDestinationNode(new Node()
                                        .setPrompts(
                                                new TextToSpeechPrompt()
                                                        .setMessage("Press 1 to record, 2 for education"))
                                        .setTransitions(
                                                new Object[][] {
                                                        {
                                                                "1",
                                                                new Transition()
                                                                        .setDestinationNode(new Node()
                                                                                .setPrompts(
                                                                                        new RecordPrompt()
                                                                                                .setMaxDuration(
                                                                                                        10L)
                                                                                                .setSilence(
                                                                                                        3L),
                                                                                        new TextToSpeechPrompt()
                                                                                                .setMessage("Thanks for your recording"))) },
                                                        {
                                                                "2",
                                                                new Transition()
                                                                        .setDestinationNode(new Node()
                                                                                .setPrompts(
                                                                                        new TextToSpeechPrompt()
                                                                                                .setMessage("Welcome to the Medic Mobile Education part"),
                                                                                        new TextToSpeechPrompt()
                                                                                                .setMessage("Press 1 to listen in hindi, 2 to telugu or * to previous menu"))
                                                                                .setTransitions(
                                                                                        new Object[][] {
                                                                                                {
                                                                                                        "1",
                                                                                                        new Transition()
                                                                                                                .setDestinationNode(new Node()
                                                                                                                        .setPrompts(
                                                                                                                                new AudioPrompt()
                                                                                                                                        .setAudioFileUrl(
                                                                                                                                                medicmobileURL
                                                                                                                                                        + "/wav/hi/hello.wav")
                                                                                                                                        .setAltMessage(
                                                                                                                                                UNABLE_FETCH_MESSAGE),
                                                                                                                                new TextToSpeechPrompt()
                                                                                                                                        .setMessage("Press * to go to previous menu"))) },
                                                                                                {
                                                                                                        "2",
                                                                                                        new Transition()
                                                                                                                .setDestinationNode(new Node()
                                                                                                                        .setPrompts(
                                                                                                                                new AudioPrompt()
                                                                                                                                        .setAudioFileUrl(
                                                                                                                                                medicmobileURL
                                                                                                                                                        + "/wav/te/hello.wav")
                                                                                                                                        .setAltMessage(
                                                                                                                                                UNABLE_FETCH_MESSAGE),
                                                                                                                                new TextToSpeechPrompt()
                                                                                                                                        .setMessage("Press * to go to previous menu"))) }, })) } })

                                )));
    }
}
