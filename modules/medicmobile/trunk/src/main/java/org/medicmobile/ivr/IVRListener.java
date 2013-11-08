package org.medicmobile.ivr;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.medicmobile.model.Staff;
import org.medicmobile.serviceImpl.StaffServiceImpl;
import org.medicmobile.util.UtilService;
import org.medicmobile.web.controller.IVRController;
import org.motechproject.decisiontree.core.EventKeys;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.ivr.event.IVREvent;
import org.motechproject.scheduler.MotechSchedulerService;
import org.motechproject.scheduler.domain.RunOnceSchedulableJob;
import org.motechproject.server.config.SettingsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The listener interface for receiving IVR events. The class that is interested
 * in processing a IVR event implements this interface, and the object created
 * with that class is registered with a component using the component's
 * <code>addIVRListener<code> method. When
 * the IVR event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see IVREvent
 */
public final class IVRListener {

    /** The logger. */
    private static Logger logger = Logger.getLogger(IVRListener.class);

    /** The staff service. */
    @Resource(name = "staffService")
    private StaffServiceImpl staffService;

    /** The scheduler. */
    @Autowired
    private MotechSchedulerService scheduler;

    /** The Constant CALL_WITH_RECORDED_AUDIO. */
    private static final String CALL_WITH_RECORDED_AUDIO = "org.medicmobile.ivr.callwithrecordedaudio";

    /** The settings. */
    private SettingsFacade settings;

    /** The commons http client. */
    private HttpClient commonsHttpClient;

    /** The Constant MEDIC_MOBILE_MAXMUM_RECORD_DURATION. */
    private static final String MEDIC_MOBILE_MAXMUM_RECORD_DURATION = "medicmobile.maxrecordduration";

    /** The Constant OUTBOUND_URL. */
    public static final String OUTBOUND_URL = "kookoo.outbound.url";

    /** The Constant API_KEY. */
    public static final String KOOKOO_API_KEY = "kookoo.api.key";

    /** The Constant API_KEY_KEY. */
    public static final String API_KEY_KEY = "api_key";

    /** The Constant PHONE_NUMBER_KEY. */
    public static final String PHONE_NUMBER_KEY = "phone_no";

    /** The Constant OUTBOUND_VERSION. */
    public static final String OUTBOUND_VERSION = "outbound_version";

    /** The Constant EXTRA_DATA. */
    public static final String EXTRA_DATA = "extra_data";

    /** The Constant URL_KEY. */
    public static final String CALLBACK_URL_KEY = "callback_url";

    /** The max record duration. */
    private int maxRecordDuration;

    private String medicmobileIVRUrl = "";

    private static final String MEDIC_MOBILE_IVR_URL = "medicmobile.ivr.url";

    /**
     * Instantiates a new iVR listener.
     * 
     * @param settings
     *            the settings
     * @param commonsHttpClient
     *            the commons http client
     */
    @Autowired
    public IVRListener(
            @Qualifier("medicmobileURLSettings") final SettingsFacade settings,
            final HttpClient commonsHttpClient) {
        this.settings = settings;
        maxRecordDuration = Integer.parseInt(this.settings
                .getProperty(MEDIC_MOBILE_MAXMUM_RECORD_DURATION));
        medicmobileIVRUrl = settings.getProperty(MEDIC_MOBILE_IVR_URL);
        this.commonsHttpClient = commonsHttpClient;
    }

    /**
     * Handle record is ready event.
     * 
     * @param event
     *            the event
     */
    @MotechListener(subjects = { EventKeys.RECORDED_AUDIO_IS_READY })
    public synchronized void handleRecordIsReadyEvent(final MotechEvent event) {
        String recordedURL = EventKeys.getRecordedURL(event);
        Map<String, Object> paramsMap = event.getParameters();
        String phoneNumber;
        RunOnceSchedulableJob runOnceSchedulableJob;
        try {
            Thread.sleep(maxRecordDuration);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        if (paramsMap.containsKey("phoneNumber")) {
            phoneNumber = "0" + (String) paramsMap.get("phoneNumber");
            runOnceSchedulableJob = new RunOnceSchedulableJob(
                    createMotechEventForRecordAudio(phoneNumber, recordedURL),
                    new Date());
            scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
            return;
        }
        List<Staff> staffFromSearch = staffService.findAllStaff();
        for (Staff staff : staffFromSearch) {
            phoneNumber = staff.getPhoneNumber();
            if (phoneNumber != null) {
                runOnceSchedulableJob = new RunOnceSchedulableJob(
                        createMotechEventForRecordAudio(phoneNumber,
                                recordedURL), new Date());
                scheduler.safeScheduleRunOnceJob(runOnceSchedulableJob);
            }
        }
    }

    /**
     * Creates the motech event for record audio.
     * 
     * @param phoneNumber
     *            the phone number
     * @param recordedURL
     *            the recorded url
     * @return the motech event
     */
    public MotechEvent createMotechEventForRecordAudio(
            final String phoneNumber, final String recordedURL) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(EventKeys.PHONE_KEY, phoneNumber);
        paramsMap.put(MotechSchedulerService.JOB_ID_KEY, phoneNumber + "-"
                + UtilService.recordedFileName(recordedURL));
        paramsMap.put(EventKeys.RECORDED_URL, recordedURL);
        MotechEvent motechEvent = new MotechEvent(
                IVRListener.CALL_WITH_RECORDED_AUDIO, paramsMap);
        motechEvent.setLastEvent(true);
        return motechEvent;
    }

    /**
     * Handle record event.
     * 
     * @param event
     *            the event
     */
    @MotechListener(subjects = { EventKeys.RECORDED_AUDIO })
    public void handleRecordEvent(final MotechEvent event) {

        String phoneNumber = EventKeys.getPhoneNumber(event);
        GetMethod getMethod = null;
        try {
            getMethod = new GetMethod(settings.getProperty(OUTBOUND_URL));
            getMethod.setQueryString(new NameValuePair[] {
                    new NameValuePair(PHONE_NUMBER_KEY, phoneNumber),
                    new NameValuePair(API_KEY_KEY, settings
                            .getProperty(KOOKOO_API_KEY)),
                    new NameValuePair(OUTBOUND_VERSION, "" + 2),
                    new NameValuePair(EXTRA_DATA, "<response><playaudio>"
                            + EventKeys.getRecordedURL(event)
                            + "</playaudio><hangup/></response>"),
                    new NameValuePair(CALLBACK_URL_KEY, medicmobileIVRUrl
                            + "?"
                            + EventKeys.RECORDED_URL
                            + "="
                            + EventKeys.getRecordedURL(event)
                            + "&"
                            + IVRController.NUMBER_OF_TIMES
                            + "="
                            + event.getParameters().get(
                                    IVRController.NUMBER_OF_TIMES)) });
            commonsHttpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
    }
}
