package org.medicmobile.repository;

import org.ektorp.CouchDbConnector;
import org.medicmobile.model.Person;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Qualifier;

// : Auto-generated Javadoc
/**
 * The Class PersonRepository.
 */
public class PersonRepository extends MotechBaseRepository<Person> {

      /**
       * Instantiates a new person repository.
       * 
       * @param db
       *              the db
       */
      protected PersonRepository(
                  @Qualifier("couchMRSDatabaseConnector") CouchDbConnector db) {
            super(Person.class, db);
            //  Auto-generated constructor stub
      }

}
