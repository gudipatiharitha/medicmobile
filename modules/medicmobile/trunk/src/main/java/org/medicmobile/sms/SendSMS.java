package org.medicmobile.sms;

import java.util.ArrayList;
import java.util.List;

import org.medicmobile.dto.SendSmsObject;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repositoryImpl.StaffRepositoryImpl;
import org.motechproject.sms.api.service.SendSmsRequest;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class SendSMS.
 */
@Service(value = "sendSMS")
public class SendSMS {

      /** The sms service impl. */
      @Autowired
      private SmsService smsServiceImpl;

      /** The staff repo. */
      @Autowired
      private StaffRepositoryImpl staffRepo;

      /**
       * Send sms event.
       * 
       * @param request
       *              the request
       */
      public void sendSMSEvent(SendSmsRequest request) {
            if (!request.getRecipients().isEmpty()) {
                  smsServiceImpl.sendSMS(request);
            }
      }

      /**
       * Send sms to local volunteers.
       * 
       * @param object
       *              the object
       */
      public void sendSmsToLocalVolunteers(SendSmsObject object) {
            List<String> phoneNumbers = new ArrayList<String>();
            List<Staff> localVolunteers = staffRepo.findByRoleAndAnganwadiID(
                        Role.LOCAL_VOLUNTEER, object.getAnganwadiCenterID());
            for (Staff current : localVolunteers) {
                  phoneNumbers.add(current.getPhoneNumber());
            }
      }
}
