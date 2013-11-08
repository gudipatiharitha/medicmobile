package org.medicmobile.repositoryImpl;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.medicmobile.model.SmsStartCode;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class SmsStartCodeRepositoryImpl.
 */
@Repository
public class SmsStartCodeRepositoryImpl extends
            MotechBaseRepository<SmsStartCode> {

      /**
       * Instantiates a new sms start code repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected SmsStartCodeRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(SmsStartCode.class, db);
      }

      /**
       * Gets the sms start code.
       * 
       * @return the sms start code
       */
      public int getSmsStartCode() {
            return getAll().get(0).getStartInteger();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.motechproject.commons.couchdb.dao.MotechBaseRepository#getAll()
       */
      @GenerateView
      public List<SmsStartCode> getAll() {
            return super.getAll();
      }

      /**
       * Sets the sms start code.
       */
      public void setSmsStartCode() {
            SmsStartCode startCode = this.getAll().get(0);
            startCode.setStartInteger((startCode.getStartInteger()) + 1);
            super.update(startCode);
      }

}
