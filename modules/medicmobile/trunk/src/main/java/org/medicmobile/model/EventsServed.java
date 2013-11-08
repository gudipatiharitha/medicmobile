package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class EventsServed.
 */
@TypeDiscriminator("doc.type=='EventsServed'")
public class EventsServed extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 8434212617243188987L;

      /** The subject. */
      @JsonProperty
      private String subject;

      /** The unique id. */
      @JsonProperty
      private String uniqueId;

      /** The served time. */
      @JsonProperty
      private DateTime servedTime;

      /**
       * Gets the subject.
       * 
       * @return the subject
       */
      public String getSubject() {
            return subject;
      }

      /**
       * Sets the subject.
       * 
       * @param subject
       *              the subject
       * @return the events served
       */
      public EventsServed setSubject(String subject) {
            this.subject = subject;
            return this;
      }

      /**
       * Gets the unique id.
       * 
       * @return the unique id
       */
      public String getUniqueId() {
            return uniqueId;
      }

      /**
       * Sets the unique id.
       * 
       * @param uniqueId
       *              the unique id
       * @return the events served
       */
      public EventsServed setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
            return this;
      }

      /**
       * Gets the served time.
       * 
       * @return the served time
       */
      public DateTime getServedTime() {
            return servedTime;
      }

      /**
       * Sets the served time.
       * 
       * @param servedTime
       *              the served time
       * @return the events served
       */
      public EventsServed setServedTime(DateTime servedTime) {
            this.servedTime = servedTime;
            return this;
      }
}
