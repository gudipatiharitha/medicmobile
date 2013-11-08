/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medicmobile.repositoryImpl;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.medicmobile.model.MasterVaccine;
import org.medicmobile.repository.MasterVaccineRepository;
import org.medicmobile.util.MedicMobileConstants;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * The Class MasterVaccineRepositoryImpl.
 */
@Repository(value = "masterVaccine")
public class MasterVaccineRepositoryImpl extends
            MotechBaseRepository<MasterVaccine> implements
            MasterVaccineRepository {

      /**
       * Instantiates a new master vaccine repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      public MasterVaccineRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(MasterVaccine.class, db);

      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.motechproject.commons.couchdb.dao.MotechBaseRepository#getAll()
       */
      @GenerateView
      public List<MasterVaccine> getAll() {
            ViewQuery query = createQuery(MedicMobileConstants.ALL)
                        .includeDocs(true);
            return db.queryView(query, MasterVaccine.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.MasterVaccineRepository#findByVaccineName(
       * java.lang.String)
       */
      @View(name = "by_vaccineName", map = "function(doc)" + "{"

      + "if((doc.type=='MasterVaccine') && doc.vaccineName)"

      + "{" + "emit(doc.vaccineName , doc._id);" + "}" + "}")
      public List<MasterVaccine> findByVaccineName(String vaccineName) {
            ViewQuery query = createQuery("by_vaccineName").key(vaccineName)
                        .includeDocs(true);
            return db.queryView(query, MasterVaccine.class);

      }
}
