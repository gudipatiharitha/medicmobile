package org.medicmobile.repository;

import java.util.List;

import org.medicmobile.model.EventsServed;

// : Auto-generated Javadoc
/**
 * The Interface EventsServedRepository.
 */
public interface EventsServedRepository {

      /**
       * Find by job id.
       * 
       * @param jobId
       *              the job id
       * @return the list
       */
      List<EventsServed> findByJobId(String jobId);

      /**
       * Find by subject.
       * 
       * @param subject
       *              the subject
       * @return the list
       */
      List<EventsServed> findBySubject(String subject);

      /**
       * Adds the.
       * 
       * @param eventsServed
       *              the events served
       */
      void add(EventsServed eventsServed);
}
