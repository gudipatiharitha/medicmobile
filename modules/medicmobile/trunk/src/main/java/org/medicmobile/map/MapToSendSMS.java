/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medicmobile.map;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medicmobile.ivr.IVRListener;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.DetailedPerson;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.StaffRepository;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.motechproject.server.config.SettingsFacade;
import org.motechproject.sms.api.DeliveryStatus;
import org.motechproject.sms.api.service.SendSmsRequest;
import org.motechproject.sms.http.OutboundSMS;
import org.motechproject.sms.http.repository.AllOutboundSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

// : Auto-generated Javadoc
/**
 * The Class MapToSendSMS.
 */
@Service
public final class MapToSendSMS {

      /**
       * Instantiates a new map to send sms.
       */
      private MapToSendSMS() {

      }

      /** The logger. */
      private static Logger logger = Logger.getLogger(MapToSendSMS.class);
      /** The staff repo. */
      @Autowired
      private StaffRepository staffRepo;

      /** The person sms list. */
      private List<DetailedPerson> personSMSList;

      /** The care giver repo. */
      @Autowired
      private CareGiverRepository careGiverRepo;

      /** The outbound sms repo. */
      @Autowired
      private AllOutboundSMS outboundSMSRepo;

      /** The kookoo api key. */
      private String kookooApiKey;

      /** The comms client. */
      private HttpClient commsClient;

      /** The Constant MESSAGE. */
      private static final String MESSAGE = "message";

      /** The Constant KOOKOO_SMS_URL. */
      private static final String KOOKOO_SMS_URL = "kookoo.sms.url";

      /** The kookoo out bound sms url. */
      private String kookooOutBoundSmsUrl;

      /**
       * Instantiates a new map to send sms.
       * 
       * @param settings
       *              the settings
       * @param commonsHttpClient
       *              the commons http client
       */
      @Autowired
      public MapToSendSMS(
                  @Qualifier("medicmobileURLSettings") final SettingsFacade settings,
                  final HttpClient commonsHttpClient) {
            kookooApiKey = settings.getProperty(IVRListener.KOOKOO_API_KEY);
            this.commsClient = commonsHttpClient;
            this.kookooOutBoundSmsUrl = settings.getProperty(KOOKOO_SMS_URL);
      }

      /**
       * Send english message.
       * 
       * @param phoneNumberList
       *              the phone number list
       * @param message
       *              the message
       * @return true, if successful
       */
      public boolean sendEnglishMessage(List<String> phoneNumberList,
                  String message) {
            GetMethod getMethod;
            for (String phoneNumber : phoneNumberList) {
                  getMethod = new GetMethod(kookooOutBoundSmsUrl);
                  for (String messagePart : splitMessage(message)) {
                        getMethod.setQueryString(new NameValuePair[] {
                                    new NameValuePair(
                                                IVRListener.PHONE_NUMBER_KEY,
                                                phoneNumber),
                                    new NameValuePair(IVRListener.API_KEY_KEY,
                                                kookooApiKey),
                                    new NameValuePair(MapToSendSMS.MESSAGE,
                                                messagePart) });
                        try {
                              commsClient.executeMethod(getMethod);
                              outboundSMSRepo.add(new OutboundSMS(phoneNumber,
                                          UtilService.generateUniqueID(),
                                          message, DateTime.now(),
                                          DeliveryStatus.DELIVERED));
                        } catch (IOException exception) {
                              logger.info(exception.getMessage());
                        }
                  }
            }
            return false;
      }

      /**
       * Split message.
       * 
       * @param message
       *              the message
       * @return the list
       */
      public List<String> splitMessage(String message) {
            List<String> splitMessages = new ArrayList<String>();
            int startIndex = 0;
            String tempMessage = "";
            while (message.length() > 160) {
                  splitMessages.add(message.substring(startIndex, 159));
                  tempMessage = message.substring(160);
                  startIndex += 159;
            }
            splitMessages.add(tempMessage);
            return splitMessages;
      }

      /**
       * Gets the person sms list.
       * 
       * @return the person sms list
       */

      public List<DetailedPerson> getPersonSMSList() {
            return personSMSList;
      }

      /**
       * Sets the person sms list.
       * 
       * @param personSMSList
       *              the new person sms list
       */
      public void setPersonSMSList(List<DetailedPerson> personSMSList) {
            this.personSMSList = personSMSList;
      }

      /**
       * Map sms object.
       * 
       * @param map
       *              the map
       * @return the send sms request
       */
      public SendSmsRequest mapSmsObject(Map<String, String[]> map) {
            List<String> recipients = new ArrayList<String>();
            String messageFinal = null;
            try {
                  messageFinal = URLDecoder.decode(map.get("message")[0],
                              "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug(e.getMessage());
            }
            if (map.containsKey("toWhom")
                        && map.get("toWhom")[0].equals("notRegistered")) {
                  recipients = getPhoneNumberNotInDataBase(map);
            } else {
                  personSMSList = new ArrayList<>();
                  if (map.get("toWhomInAnganwadi")[0].equals("careGiver")) {
                        recipients = getCareGiverNumbers(map);
                  } else {
                        recipients = getStaffNumbers(map);
                  }
            }
            return new SendSmsRequest(recipients, messageFinal);
      }

      /**
       * Gets the staff numbers.
       * 
       * @param map
       *              the map
       * @return the staff numbers
       */
      public List<String> getStaffNumbers(final Map<String, String[]> map) {
            List<String> recipients = new ArrayList<String>();
            if (map.get("staffRole")[0].equals("all")) {
                  List<Staff> staffList = staffRepo.findByAnganwadiID(map
                              .get("anganwadiID")[0]);
                  personSMSList.addAll(staffList);
                  for (Staff current : staffList) {
                        if (current.getWhetherToReceiveSMS()) {
                              recipients.add(current.getPhoneNumber());
                        }
                  }

            } else if ("all".equals(map.get("staffID")[0])) {
                  List<Staff> staffList = staffRepo.findByRoleAndAnganwadiID(
                              Role.valueOf(map.get("staffRole")[0]),
                              map.get("anganwadiID")[0]);
                  personSMSList.addAll(staffList);
                  for (Staff current : staffList) {
                        if (current.getWhetherToReceiveSMS()) {
                              recipients.add(current.getPhoneNumber());
                        }
                  }
            } else {
                  List<Staff> staffList = staffRepo.findByStaffID(map
                              .get("staffID")[0]);
                  personSMSList.addAll(staffList);
                  for (Staff current : staffList) {
                        if (current.getWhetherToReceiveSMS()) {
                              recipients.add(current.getPhoneNumber());
                        }
                  }
            }
            return recipients;
      }

      /**
       * Gets the phone number not in data base.
       * 
       * @param map
       *              the map
       * @return the phone number not in data base
       */
      public List<String> getPhoneNumberNotInDataBase(
                  final Map<String, String[]> map) {
            List<String> recipients = new ArrayList<String>();
            if (map.get(MedicMobileConstants.NEW_PHONE_NUMBER)[0].length() == MedicMobileConstants.PHONE_NUMBER_ELEVEN) {
                  String phoneNumber = map
                              .get(MedicMobileConstants.NEW_PHONE_NUMBER)[0];
                  recipients.add(phoneNumber);
            } else if (map.get(MedicMobileConstants.NEW_PHONE_NUMBER)[0]
                        .length() == MedicMobileConstants.PHONE_NUMBER_TEN
                        && !"0".startsWith(map
                                    .get(MedicMobileConstants.NEW_PHONE_NUMBER)[0])) {
                  String phoneNumber = "0"
                              + map.get(MedicMobileConstants.NEW_PHONE_NUMBER)[0];
                  recipients.add(phoneNumber);
            }

            return recipients;
      }

      /**
       * Gets the care giver numbers.
       * 
       * @param map
       *              the map
       * @return the care giver numbers
       */
      public List<String> getCareGiverNumbers(final Map<String, String[]> map) {
            List<String> recipients = new ArrayList<String>();

            if ("all".equals(map.get("careGiverID")[0])) {
                  List<CareGiver> careGiverList = careGiverRepo
                              .findByAnganwadiCenterID(map.get("anganwadiID")[0]);

                  for (CareGiver current : careGiverList) {
                        if (!(current.getPhoneNumber() == null || ""
                                    .equals(current.getPhoneNumber()))) {
                              personSMSList.add(current);
                              recipients.add(current.getPhoneNumber());
                        }
                  }
            } else {
                  List<CareGiver> careGiverList = careGiverRepo
                              .findByCareGiverIDS(map.get("careGiverID")[0]);
                  recipients.add(careGiverList.get(0).getPhoneNumber());
                  personSMSList.add(careGiverList.get(0));
            }
            return recipients;
      }
}
