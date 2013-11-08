/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medicmobile.sms;

import static org.motechproject.sms.api.constants.EventDataKeys.INBOUND_MESSAGE;
import static org.motechproject.sms.api.constants.EventDataKeys.SENDER;
import static org.motechproject.sms.api.constants.EventDataKeys.TIMESTAMP;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.medicmobile.dto.VisitUpdate;
import org.medicmobile.model.InboundSMSAdditional;
import org.medicmobile.model.Role;
import org.medicmobile.model.SMSCodeMap;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.InboundSMSAdditionalRepository;
import org.medicmobile.repositoryImpl.SMSCodeMapRepositoryImpl;
import org.medicmobile.repositoryImpl.StaffRepositoryImpl;
import org.medicmobile.service.WeeklyReportService;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.sms.api.constants.EventSubjects;
import org.motechproject.sms.api.service.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// : Auto-generated Javadoc
/**
 * The Class InboundSMSReciever.
 */
@Service
public class InboundSMSReciever {

      /** The Constant SMS_TIMESTAMP_PATTERN. */
      public static final String SMS_TIMESTAMP_PATTERN = "sms.timestamp.pattern";

      /** The staff repo. */
      @Resource(name = "staffRepo")
      private StaffRepositoryImpl staffRepo;

      /** The weekly report. */
      @Autowired
      private WeeklyReportService weeklyReport;

      /** The inbound save. */
      @Autowired
      private InboundSMSAdditionalRepository inboundSave;

      /** The sms code map repo. */
      @Autowired
      private SMSCodeMapRepositoryImpl smsCodeMapRepo;

      /** The send sms. */
      @Autowired
      private SendSMS sendSMS;

      /**
       * Handle.
       * 
       * @param event
       *              the event
       */
      @MotechListener(subjects = EventSubjects.INBOUND_SMS)
      public void handle(MotechEvent event) {
            String phoneNumber = (String) event.getParameters().get(SENDER);
            phoneNumber = phoneNumber.substring(2);
            phoneNumber = "0" + phoneNumber;
            List<Staff> staff = staffRepo.findStaffByPhoneNumber(phoneNumber);
            List<String> phoneNumberList;
            SendSmsRequest sendSMSRequest;
            if (!staff.isEmpty()) {
                  Staff staffMem = staff.get(0);
                  if (staffMem.getRole() == Role.AUXILIARY_NURSING_MOTHER) {
                  } else {
                        VisitUpdate visitUpdate = new VisitUpdate();
                        List<SMSCodeMap> codeMaps = smsCodeMapRepo
                                    .findBySMSCode(Integer
                                                .parseInt((String) event
                                                            .getParameters()
                                                            .get(INBOUND_MESSAGE)));
                        if (codeMaps != null && codeMaps.size() > 0) {
                              visitUpdate.setVaccineID(codeMaps.get(0)
                                          .getSystemCode());
                              visitUpdate.setPersonName(staffMem.getName());
                              visitUpdate.setVisitedDate(parseTimeStampTODateTime((String) event
                                          .getParameters().get(TIMESTAMP)));
                              if (weeklyReport.update(visitUpdate)) {
                                    inboundSave.add(new InboundSMSAdditional(
                                                staffMem.getStaffID(),
                                                true,
                                                parseTimeStampTODateTime((String) event
                                                            .getParameters()
                                                            .get(TIMESTAMP)),
                                                ""));
                              } else {
                                    inboundSave.add(new InboundSMSAdditional(
                                                staffMem.getStaffID(),
                                                false,
                                                parseTimeStampTODateTime((String) event
                                                            .getParameters()
                                                            .get(TIMESTAMP)),
                                                ""));
                              }
                        } else {
                              visitUpdate.setVaccineID(null);
                              phoneNumberList = new ArrayList<String>();
                              phoneNumberList.add(phoneNumber);
                              sendSMSRequest = new SendSmsRequest(
                                          phoneNumberList,
                                          "हम अपने  कोड पर आधारित टीके के प्रशासित तारीख को अद्यतन करने में असमर्थ हैं("
                                                      + ((String) event
                                                                  .getParameters()
                                                                  .get(INBOUND_MESSAGE))
                                                      + "). सही  कोड भेज कृपया.");
                              inboundSave.add(new InboundSMSAdditional(
                                          staffMem.getStaffID(),
                                          false,
                                          parseTimeStampTODateTime((String) event
                                                      .getParameters().get(
                                                                  TIMESTAMP)),
                                          ""));
                              sendSMS.sendSMSEvent(sendSMSRequest);
                        }
                  }
            } else {
                  VisitUpdate visitUpdate = new VisitUpdate();
                  visitUpdate.setVaccineID((String) event.getParameters().get(
                              INBOUND_MESSAGE));
                  visitUpdate.setVisitedDate(parseTimeStampTODateTime((String) event
                              .getParameters().get(TIMESTAMP)));
                  if (weeklyReport.update(visitUpdate)) {
                        inboundSave.add(new InboundSMSAdditional(
                                    "unknown",
                                    true,
                                    parseTimeStampTODateTime((String) event
                                                .getParameters().get(TIMESTAMP)),
                                    ""));
                  } else {
                        inboundSave.add(new InboundSMSAdditional(
                                    "unknown",
                                    false,
                                    parseTimeStampTODateTime((String) event
                                                .getParameters().get(TIMESTAMP)),
                                    ""));
                  }
            }
      }

      /**
       * Parses the time stamp to date time.
       * 
       * @param timeStamp
       *              the time stamp
       * @return the date time
       */
      private DateTime parseTimeStampTODateTime(String timeStamp) {
            DateTimeFormatter format = DateTimeFormat

            .forPattern("MM/dd/yyyy hh:mm:ss aa");

            return format.parseDateTime(timeStamp);
      }
}
