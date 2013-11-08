package org.medicmobile.repositoryImpl;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.medicmobile.model.SMSCodeMap;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class SMSCodeMapRepositoryImpl.
 */
@Repository
public class SMSCodeMapRepositoryImpl extends MotechBaseRepository<SMSCodeMap> {

      /**
       * Instantiates a new sMS code map repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected SMSCodeMapRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(SMSCodeMap.class, db);
            initStandardDesignDocument();
      }

      /**
       * Find by system code.
       * 
       * @param systemCode
       *              the system code
       * @return the list
       */
      @View(name = "by_systemCode", map = "function(doc){if((doc.type=='SMSCodeMap') && doc.systemCode){emit(doc.systemCode, doc._id);}}")
      public List<SMSCodeMap> findBySystemCode(String systemCode) {
            ViewQuery query = createQuery("by_systemCode").key(systemCode)
                        .includeDocs(true);
            return db.queryView(query, SMSCodeMap.class);

      }

      /**
       * Find by sms code.
       * 
       * @param smsCode
       *              the sms code
       * @return the list
       */
      @View(name = "by_SMSCode", map = "function(doc){if((doc.type=='SMSCodeMap') && doc.smsCode){emit(doc.smsCode, doc._id);}}")
      public List<SMSCodeMap> findBySMSCode(int smsCode) {

            ViewQuery query = createQuery("by_SMSCode").key(smsCode)
                        .includeDocs(true);
            return db.queryView(query, SMSCodeMap.class);

      }

}
