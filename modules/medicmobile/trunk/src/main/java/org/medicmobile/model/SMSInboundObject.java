package org.medicmobile.model;

import org.joda.time.DateTime;

// : Auto-generated Javadoc
/**
 * The Class SMSInboundObject.
 */
public class SMSInboundObject {

      /** The cid. */
      private String cid;

      /** The message. */
      private String message;

      /**
       * Gets the message.
       * 
       * @return the message
       */
      public String getMessage() {
            return message;
      }

      /**
       * Set the value of message.
       * 
       * @param message
       *              new value of message
       */
      public void setMessage(String message) {
            this.message = message;
      }

      /** The date. */
      private DateTime date;

      /**
       * Gets the cid.
       * 
       * @return the cid
       */
      public String getCid() {
            return cid;
      }

      /**
       * Sets the cid.
       * 
       * @param cid
       *              the new cid
       */
      public void setCid(String cid) {
            this.cid = cid;
      }

      /**
       * Gets the date.
       * 
       * @return the date
       */
      public DateTime getDate() {
            return date;
      }

      /**
       * Sets the date.
       * 
       * @param date
       *              the new date
       */
      public void setDate(DateTime date) {
            this.date = date;
      }

}
