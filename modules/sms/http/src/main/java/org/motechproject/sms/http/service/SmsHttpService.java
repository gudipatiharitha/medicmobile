package org.motechproject.sms.http.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.motechproject.sms.api.DeliveryStatus;
import org.motechproject.sms.api.SmsDeliveryFailureException;
import org.motechproject.sms.http.OutboundSMS;
import org.motechproject.sms.http.SMSGatewayResponse;
import org.motechproject.sms.http.TemplateReader;
import org.motechproject.sms.http.repository.AllOutboundSMS;
import org.motechproject.sms.http.template.Authentication;
import org.motechproject.sms.http.template.SmsHttpTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SmsHttpService {
    private HttpClient commonsHttpClient;


    private static Logger log = LoggerFactory.getLogger(SmsHttpService.class);
    private TemplateReader templateReader;

    @Autowired
    private AllOutboundSMS outboundSMSRepo;
    
 
    public static final String SAVE_SMS_DETAILS = "sms.details.save";
    private SmsHttpService() {
    }

    @Autowired
    public SmsHttpService(TemplateReader templateReader, HttpClient commonsHttpClient) {
        this.templateReader = templateReader;
        this.commonsHttpClient = commonsHttpClient;
    }

    public void sendSMS(String recipient, String message) throws SmsDeliveryFailureException {
        sendSms(Arrays.asList(recipient), message);
    }

    public void sendSms(List<String> recipients, String message) throws SmsDeliveryFailureException {
        if (CollectionUtils.isEmpty(recipients) || StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Recipients or Message should not be empty");
        }

        String response = null;
        HttpMethod httpMethod = null;
        SmsHttpTemplate smsHttpTemplate = template();
        try {
            httpMethod = smsHttpTemplate.generateRequestFor(recipients, message);
            setAuthenticationInfo(smsHttpTemplate.getAuthentication());
            int status = commonsHttpClient.executeMethod(httpMethod);
            response = httpMethod.getResponseBodyAsString();
            log.info("HTTP Status:" + status + "|Response:" + response);
            saveToDataBase(smsHttpTemplate, recipients, message);
        } catch (Exception e) {
            log.error("SMSDeliveryFailure due to : ", e);
            throw new SmsDeliveryFailureException(response, e);
        } finally {
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }

        if (!new SMSGatewayResponse(template(), response).isSuccess()) {
            log.error(String.format("SMS delivery failed. Retrying...; Response: %s", response));
            throw new SmsDeliveryFailureException(response);
        }

        log.debug("SMS with message %s sent successfully to %s:", message, StringUtils.join(recipients.iterator(), ","));
    }

    private void setAuthenticationInfo(Authentication authentication) {
        if (authentication == null) {
            return;
        }

        commonsHttpClient.getParams().setAuthenticationPreemptive(true);
        commonsHttpClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(authentication.getUsername(), authentication.getPassword()));
    }

    //Recreating the template for every request so that latest templates can be changed
    private SmsHttpTemplate template() {
        return templateReader.getTemplate();
    }
    
    private void saveToDataBase(SmsHttpTemplate smsHttpTemplate, List<String> recipients, String message)
    {
        for (String recipient : recipients) {
            outboundSMSRepo.add(new OutboundSMS(recipient, smsHttpTemplate.getSuccessfulResponsePattern(), message, DateTime.now(), DeliveryStatus.DELIVERED));
        }
    }
    public AllOutboundSMS getOutboundSMSRepo() {
         return outboundSMSRepo;
    }

    public void setOutboundSMSRepo(AllOutboundSMS outboundSMSRepo) {
         this.outboundSMSRepo = outboundSMSRepo;
    }

}
