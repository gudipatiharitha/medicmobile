package org.medicmobile.repositoryImpl;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.medicmobile.model.EventsServed;
import org.medicmobile.repository.EventsServedRepository;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class EventsServedRepositoryImpl.
 */
@Repository(value = "eventsServedRepo")
public class EventsServedRepositoryImpl extends
            MotechBaseRepository<EventsServed> implements
            EventsServedRepository {

      /**
       * Instantiates a new events served repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected EventsServedRepositoryImpl(
                  @Qualifier("eventsServedDatabase") CouchDbConnector db) {
            super(EventsServed.class, db);
            initStandardDesignDocument();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.EventsServedRepository#findByJobId(java.lang
       * .String)
       */
      @View(name = "by_jobId", map = "function(doc){if(doc.type=='EventsServed' && doc.uniqueId){emit(doc.uniqueId, doc._id);}}")
      @Override
      public List<EventsServed> findByJobId(String jobId) {
            ViewQuery queryView = createQuery("by_jobId").includeDocs(true)
                        .key(jobId);
            return db.queryView(queryView, EventsServed.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.EventsServedRepository#findBySubject(java.
       * lang.String)
       */
      @View(name = "by_subject", map = "function(doc){if(doc.type=='EventsServed' && doc.subject){emit(doc.subject, doc._id);}}")
      @Override
      public List<EventsServed> findBySubject(String subject) {
            ViewQuery queryView = createQuery("by_subject").includeDocs(true)
                        .key(subject);
            return db.queryView(queryView, EventsServed.class);
      }

}
