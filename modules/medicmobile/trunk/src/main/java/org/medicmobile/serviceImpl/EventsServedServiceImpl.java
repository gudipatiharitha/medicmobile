package org.medicmobile.serviceImpl;

import javax.annotation.Resource;

import org.medicmobile.model.EventsServed;
import org.medicmobile.repository.EventsServedRepository;
import org.medicmobile.service.EventsServedService;
import org.springframework.stereotype.Service;

/**
 * The Class EventsServedServiceImpl.
 */
@Service(value = "eventsServedService")
public class EventsServedServiceImpl implements EventsServedService {

      /** The events served repository. */
      @Resource(name = "eventsServedRepo")
      private EventsServedRepository eventsServedRepository;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.EventsServedService#isEventServed(java.lang
       * .String )
       */
      @Override
      public boolean isEventServed(String jobId) {
            if (eventsServedRepository.findByJobId(jobId) != null
                        && eventsServedRepository.findByJobId(jobId).size() == 0) {
                  return true;
            } else {
                  return false;
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.EventsServedService#saveEventServed(org.medicmobile
       * .model.EventsServed)
       */
      public void saveEventServed(EventsServed eventServed) {
            eventsServedRepository.add(eventServed);
      }

}
