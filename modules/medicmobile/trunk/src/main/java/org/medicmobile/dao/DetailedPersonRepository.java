package org.medicmobile.dao;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.medicmobile.model.Person;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The Class DetailedPersonRepository.
 */
public class DetailedPersonRepository extends MotechBaseRepository<Person> {

      /**
       * Instantiates a new detailed person repository.
       * 
       * @param db
       *              the db
       */
      public DetailedPersonRepository(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(Person.class, db);
            initStandardDesignDocument();

      }

      /**
       * Adds the detailed person.
       * 
       * @param person
       *              the person
       */
      public void addDetailedPerson(Person person) {
            super.add(person);
      }

      /**
       * Gets the all persons.
       * 
       * @return the all persons
       */
      @View(name = "findAllPersons", map = "function(doc) {if (doc.type ='Person') {emit(null, doc);}}")
      public List<Person> getAllPersons() {
            return queryView("findAllPersons");
      }
}
