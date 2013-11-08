package org.motechproject.sms.http.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.EventRelay;
import org.motechproject.server.config.SettingsFacade;
import org.motechproject.sms.api.constants.EventDataKeys;
import org.motechproject.sms.api.constants.EventSubjects;
import org.motechproject.sms.http.InboundSMS;
import org.motechproject.sms.http.TemplateReader;
import org.motechproject.sms.http.repository.AllInboundSMS;
import org.motechproject.sms.http.template.SmsHttpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sms")
public class InboundSmsController {

    private EventRelay eventRelay;
    private SmsHttpTemplate template;
    @Autowired
    private AllInboundSMS inboundSMSRepo;
    @Autowired
    private SettingsFacade smsAuditSettings;
    
    public static final String SAVE_SMS_DETAILS = "sms.details.save";
    
    public static final String SMS_TIMESTAMP_PATTERN =  "sms.timestamp.pattern";

    @Autowired
    public InboundSmsController(TemplateReader templateReader, EventRelay eventRelay, AllInboundSMS inboundSMSRepo, @Qualifier("smsAuditSettings") SettingsFacade smsAuditSettings) {
        this.template = templateReader.getTemplate();
        this.eventRelay = eventRelay;
        this.inboundSMSRepo = inboundSMSRepo;
        this.smsAuditSettings = smsAuditSettings;
    }
    public InboundSmsController(TemplateReader templateReader, EventRelay eventRelay) {
        this.template = templateReader.getTemplate();
        this.eventRelay = eventRelay;
    }

    @RequestMapping(value = "inbound")
    public void handle(HttpServletRequest request) {
        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put(EventDataKeys.SENDER, request.getParameter(template.getIncoming().getSenderKey()));
        payload.put(EventDataKeys.INBOUND_MESSAGE, request.getParameter(template.getIncoming().getMessageKey()));
        payload.put(EventDataKeys.TIMESTAMP, request.getParameter(template.getIncoming().getTimestampKey()));
        eventRelay.sendEventMessage(new MotechEvent(EventSubjects.INBOUND_SMS, payload));
        inboundSMSRepo.add(new InboundSMS(request.getParameter(template.getIncoming().getSenderKey()), request.getParameter(template.getIncoming().getMessageKey()), parseTimeStampTODateTime(request.getParameter(template.getIncoming().getTimestampKey()))));
    }
    
    private DateTime parseTimeStampTODateTime(String timeStamp) {
        DateTimeFormatter format = DateTimeFormat.forPattern(smsAuditSettings.getProperty(SMS_TIMESTAMP_PATTERN));
        if (timeStamp == null) {
            return null;
        } else {
            return format.parseDateTime(timeStamp);
        }
    }
}
