package org.medicmobile.model;

import org.joda.time.DateTime;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class SMSObject.
 */
public class SMSObject extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 1L;

      /** The message. */
      private String message;

      /** The phone number. */
      private String phoneNumber;

      /** The recieved date. */
      private DateTime recievedDate;

      /**
       * Gets the message.
       * 
       * @return the message
       */
      public String getMessage() {
            return message;
      }

      /**
       * Sets the message.
       * 
       * @param message
       *              the new message
       */
      public void setMessage(String message) {
            this.message = message;
      }

      /**
       * Gets the phone number.
       * 
       * @return the phone number
       */
      public String getPhoneNumber() {
            return phoneNumber;
      }

      /**
       * Sets the phone number.
       * 
       * @param phoneNumber
       *              the new phone number
       */
      public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
      }

      /**
       * Gets the recieved date.
       * 
       * @return the recieved date
       */
      public DateTime getRecievedDate() {
            return recievedDate;
      }

      /**
       * Sets the recieved date.
       * 
       * @param recievedDate
       *              the new recieved date
       */
      public void setRecievedDate(DateTime recievedDate) {
            this.recievedDate = recievedDate;
      }

}
