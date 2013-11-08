package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class SMSCodeMap.
 */
@TypeDiscriminator("doc.type =='SMSCodeMap'")
public class SMSCodeMap extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 1L;

      /** The sms code. */
      @JsonProperty
      private int smsCode;

      /** The system code. */
      @JsonProperty
      private String systemCode;

      /**
       * Instantiates a new sMS code map.
       */
      public SMSCodeMap() {

      }

      /**
       * Instantiates a new sMS code map.
       * 
       * @param startCode
       *              the start code
       * @param vaccineID
       *              the vaccine id
       */
      public SMSCodeMap(int startCode, String vaccineID) {
            this.smsCode = startCode;
            this.systemCode = vaccineID;
      }

      /**
       * Gets the sms code.
       * 
       * @return the sms code
       */
      public int getSmsCode() {
            return smsCode;
      }

      /**
       * Sets the sms code.
       * 
       * @param smsCode
       *              the new sms code
       */
      public void setSmsCode(int smsCode) {
            this.smsCode = smsCode;
      }

      /**
       * Gets the system code.
       * 
       * @return the system code
       */
      public String getSystemCode() {
            return systemCode;
      }

      /**
       * Sets the system code.
       * 
       * @param systemCode
       *              the new system code
       */
      public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
      }

}
