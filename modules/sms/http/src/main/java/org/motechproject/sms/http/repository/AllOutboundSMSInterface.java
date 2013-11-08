package org.motechproject.sms.http.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.motechproject.sms.http.OutboundSMS;

public interface AllOutboundSMSInterface {

    void updateDeliveryStatus(String recipient, String refNo, String deliveryStatus);

    OutboundSMS findLatestBy(String refNo, String phoneNumber);

    List<OutboundSMS> findAllBy(String refNo, String phoneNumber);

    OutboundSMS findBy(DateTime deliveryTime, String refNo, String phoneNumber);

    List<OutboundSMS> messagesSentBetween(String phoneNumber, DateTime from, DateTime to);
    List<OutboundSMS> messagesSentBetween(DateTime from, DateTime to);
    void createOrReplace(OutboundSMS outboundSMS);
}
