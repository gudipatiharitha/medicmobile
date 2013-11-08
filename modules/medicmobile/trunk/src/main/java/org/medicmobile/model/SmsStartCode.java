package org.medicmobile.model;

import org.ektorp.support.TypeDiscriminator;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class SmsStartCode.
 */
@TypeDiscriminator("doc.type =='SmsStartCode'")
public class SmsStartCode extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 1L;

      /** The start integer. */
      private int startInteger;

      /**
       * Gets the start integer.
       * 
       * @return the start integer
       */
      public int getStartInteger() {
            return startInteger;
      }

      /**
       * Sets the start integer.
       * 
       * @param startInteger
       *              the new start integer
       */
      public void setStartInteger(int startInteger) {
            this.startInteger = startInteger;
      }

}
