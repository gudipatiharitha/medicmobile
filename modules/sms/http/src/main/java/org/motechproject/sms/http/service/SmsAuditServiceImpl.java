package org.motechproject.sms.http.service;

import java.util.List;

import org.joda.time.DateTime;
import org.motechproject.sms.api.SMSRecord;
import org.motechproject.sms.api.service.SmsAuditService;
import org.motechproject.sms.http.SMSRecordFactory;
import org.motechproject.sms.http.repository.AllInboundSMS;
import org.motechproject.sms.http.repository.AllOutboundSMSInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsAuditServiceImpl implements SmsAuditService {
   
    private AllOutboundSMSInterface allOutboundSMS;
    private AllInboundSMS allInboundSMS;

    @Autowired
    public SmsAuditServiceImpl(AllOutboundSMSInterface allOutboundSMS, AllInboundSMS allInboundSMS) {
        this.allOutboundSMS = allOutboundSMS;
        this.allInboundSMS = allInboundSMS;
    }

    @Override
    public List<SMSRecord> allOutboundMessagesBetween(DateTime from, DateTime to) {
        return new SMSRecordFactory().mapOutbound(allOutboundSMS.messagesSentBetween(from, to));
    }

    @Override
    public List<SMSRecord> allOutboundMessagesBetween(String phoneNumber, DateTime from, DateTime to) {
        return new SMSRecordFactory().mapOutbound(allOutboundSMS.messagesSentBetween(phoneNumber, from, to));
    }

    @Override
    public List<SMSRecord> allInboundMessagesBetween(DateTime from, DateTime to) {
        return new SMSRecordFactory().mapInbound(allInboundSMS.messagesReceivedBetween(from, to));
    }

    @Override
    public List<SMSRecord> allInboundMessagesBetween(String phoneNumber, DateTime from, DateTime to) {
        return new SMSRecordFactory().mapInbound(allInboundSMS.messagesReceivedBetween(phoneNumber, from, to));
    }
}
