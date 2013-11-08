package org.medicmobile.service;

import org.medicmobile.model.EventsServed;

// : Auto-generated Javadoc
/**
 * The Interface EventsServedService.
 */
public interface EventsServedService {

      /**
       * Checks if is event served.
       * 
       * @param jobId
       *              the job id
       * @return true, if is event served
       */
      boolean isEventServed(String jobId);

      /**
       * Save event served.
       * 
       * @param eventsServed
       *              the events served
       */
      void saveEventServed(EventsServed eventsServed);
}
