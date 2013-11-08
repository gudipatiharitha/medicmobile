package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class InboundSMSAdditional.
 */
@TypeDiscriminator("doc.type=='InboundSMSAdditional'")
public class InboundSMSAdditional extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 1L;

      /** The staff id. */
      @JsonProperty
      private String staffID;

      /** The update success. */
      @JsonProperty
      private boolean updateSuccess;

      /** The updated time. */
      @JsonProperty
      private DateTime updatedTime;

      /** The message id. */
      @JsonProperty
      private String messageID;

      /**
       * Instantiates a new inbound sms additional.
       */
      public InboundSMSAdditional() {

      }

      /**
       * Instantiates a new inbound sms additional.
       * 
       * @param localVolunteerID
       *              the local volunteer id
       * @param updateSuccess
       *              the update success
       * @param updatedTime
       *              the updated time
       * @param messageID
       *              the message id
       */
      public InboundSMSAdditional(String localVolunteerID,
                  boolean updateSuccess, DateTime updatedTime, String messageID) {
            this.staffID = localVolunteerID;
            this.messageID = messageID;
            this.updatedTime = updatedTime;
            this.updateSuccess = updateSuccess;
      }

      /**
       * Gets the local volunteer id.
       * 
       * @return the local volunteer id
       */
      public String getLocalVolunteerID() {
            return staffID;
      }

      /**
       * Sets the local volunteer id.
       * 
       * @param localVolunteerID
       *              the new local volunteer id
       */
      public void setLocalVolunteerID(String localVolunteerID) {
            this.staffID = localVolunteerID;
      }

      /**
       * Gets the update success.
       * 
       * @return the update success
       */
      public boolean getUpdateSuccess() {
            return updateSuccess;
      }

      /**
       * Sets the update success.
       * 
       * @param updateSuccess
       *              the new update success
       */
      public void setUpdateSuccess(boolean updateSuccess) {
            this.updateSuccess = updateSuccess;
      }

      /**
       * Gets the updated time.
       * 
       * @return the updated time
       */
      public DateTime getUpdatedTime() {
            return updatedTime;
      }

      /**
       * Sets the updated time.
       * 
       * @param updatedTime
       *              the new updated time
       */
      public void setUpdatedTime(DateTime updatedTime) {
            this.updatedTime = updatedTime;
      }

      /**
       * Gets the message id.
       * 
       * @return the message id
       */
      public String getMessageID() {
            return messageID;
      }

      /**
       * Sets the message id.
       * 
       * @param messageID
       *              the new message id
       */
      public void setMessageID(String messageID) {
            this.messageID = messageID;
      }

}
