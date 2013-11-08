package org.medicmobile.dto;

// : Auto-generated Javadoc
/**
 * The Class SendSmsObject.
 */
public final class SendSmsObject {

      /** The anganwadi center id. */
      private String anganwadiCenterID;

      /**
       * Instantiates a new send sms object.
       */
      public SendSmsObject() {
      }

      /** The message. */
      private String message;

      /**
       * Instantiates a new send sms object.
       * 
       * @param anganwadiCenterID
       *              the anganwadi center id
       * @param message
       *              the message
       */
      public SendSmsObject(final String anganwadiCenterID, final String message) {
            this.anganwadiCenterID = anganwadiCenterID;
            this.message = message;
      }

      /**
       * Gets the anganwadi center id.
       * 
       * @return the anganwadi center id
       */
      public String getAnganwadiCenterID() {
            return anganwadiCenterID;
      }

      /**
       * Sets the anganwadi center id.
       * 
       * @param anganwadiCenterID
       *              the new anganwadi center id
       */
      public void setAnganwadiCenterID(final String anganwadiCenterID) {
            this.anganwadiCenterID = anganwadiCenterID;
      }

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
      public void setMessage(final String message) {
            this.message = message;
      }
}
