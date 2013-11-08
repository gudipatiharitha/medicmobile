package org.medicmobile.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.medicmobile.model.AnganwadiCenter;
import org.medicmobile.repository.AnganwadiCenterRepository;
import org.medicmobile.util.UtilService;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class AnganwadiCenterRepositoryImpl.
 */
@Repository(value = "anganwadiCenterRepo")
public class AnganwadiCenterRepositoryImpl extends
            MotechBaseRepository<AnganwadiCenter> implements
            AnganwadiCenterRepository {

      /**
       * Instantiates a new anganwadi center repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected AnganwadiCenterRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(AnganwadiCenter.class, db);
            initStandardDesignDocument();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.AnganwadiCenterRepository#deleteAnganwadiCenter
       * (java.lang.String)
       */
      public void deleteAnganwadiCenter(String anganwadiID) {
            AnganwadiCenter anganwadiCenter = this.findByAnganwadiID(
                        anganwadiID).get(0);

            safeRemove(anganwadiCenter);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.AnganwadiCenterRepository#addAnganwadiCenter
       * (org.medicmobile.model.AnganwadiCenter)
       */
      public void addAnganwadiCenter(AnganwadiCenter anganwadiCenter) {
            if (anganwadiCenter.getAnganwadiID().equals("")) {
                  anganwadiCenter
                              .setAnganwadiID(UtilService.generateUniqueID());
                  super.add(anganwadiCenter);
            } else {
                  List<AnganwadiCenter> anganwadiCenters = this
                              .findByAnganwadiID(anganwadiCenter
                                          .getAnganwadiID());
                  AnganwadiCenter searchedAnganwadiCenter = anganwadiCenters
                              .get(0);
                  this.updateFields(searchedAnganwadiCenter, anganwadiCenter);
                  super.update(searchedAnganwadiCenter);
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.AnganwadiCenterRepository#viewAnganwadiCenter
       * (org.medicmobile.model.AnganwadiCenter)
       */
      public AnganwadiCenter viewAnganwadiCenter(AnganwadiCenter anganwadiCenter) {
            List<AnganwadiCenter> anganwadiCenterList = new ArrayList<AnganwadiCenter>();
            anganwadiCenterList.addAll(this.findByAnganwadiID(anganwadiCenter
                        .getAnganwadiID()));
            if (anganwadiCenterList.size() > 0) {
                  return anganwadiCenterList.get(0);
            }
            return null;
      }

      /**
       * Update fields.
       * 
       * @param searchedAnganwadiCenter
       *              the searched anganwadi center
       * @param anganwadiCenter
       *              the anganwadi center
       */
      private void updateFields(AnganwadiCenter searchedAnganwadiCenter,
                  AnganwadiCenter anganwadiCenter) {

            searchedAnganwadiCenter.setAnganwadiCenterName(anganwadiCenter
                        .getAnganwadiCenterName());
            searchedAnganwadiCenter.setChildPopulation(anganwadiCenter
                        .getChildPopulation());
            searchedAnganwadiCenter.setFemalePopulation(anganwadiCenter
                        .getFemalePopulation());
            searchedAnganwadiCenter.setMalePopulation(anganwadiCenter
                        .getMalePopulation());
            searchedAnganwadiCenter
                        .setPanchayat(anganwadiCenter.getPanchayat());
            searchedAnganwadiCenter.setProgram(anganwadiCenter.getProgram());
            searchedAnganwadiCenter.setSector(anganwadiCenter.getSector());
            searchedAnganwadiCenter.setState(anganwadiCenter.getState());
            searchedAnganwadiCenter.setVillage(anganwadiCenter.getVillage());
            searchedAnganwadiCenter.setZilla(anganwadiCenter.getZilla());
            searchedAnganwadiCenter.setPara(anganwadiCenter.getPara());
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.motechproject.commons.couchdb.dao.MotechBaseRepository#getAll()
       */
      @GenerateView
      public List<AnganwadiCenter> getAll() {
            ViewQuery query = createQuery("all").includeDocs(true);
            return db.queryView(query, AnganwadiCenter.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.AnganwadiCenterRepository#findByAnganwadiID
       * (java.lang.String)
       */
      @View(name = "by_anganwadiID", map = "function(doc){if((doc.type=='AnganwadiCenter') && doc.anganwadiID){emit(doc.anganwadiID, doc._id);}}")
      public List<AnganwadiCenter> findByAnganwadiID(String anganwadiID) {
            ViewQuery query = createQuery("by_anganwadiID").key(anganwadiID)
                        .includeDocs(true);
            return db.queryView(query, AnganwadiCenter.class);

      }

}
