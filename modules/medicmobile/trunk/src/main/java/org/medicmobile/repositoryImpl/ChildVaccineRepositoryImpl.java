package org.medicmobile.repositoryImpl;

import java.util.Date;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.medicmobile.exceptions.IDNotAvailableException;
import org.medicmobile.model.ChildVaccine;
import org.medicmobile.model.VaccineSchedule;
import org.medicmobile.repository.ChildVaccineRepository;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The Class ChildVaccineRepositoryImpl.
 */
public class ChildVaccineRepositoryImpl extends
            MotechBaseRepository<ChildVaccine> implements
            ChildVaccineRepository {

      /**
       * Instantiates a new child vaccine repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected ChildVaccineRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(ChildVaccine.class, db);
            initStandardDesignDocument();
            //  Auto-generated constructor stub
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#addChildVaccine(org
       * .medicmobile.model.ChildVaccine)
       */
      public void addChildVaccine(ChildVaccine childVaccine) {

            if (childVaccine.getChildID() == null
                        || childVaccine.getChildVaccineID() == null) {
                  throw new IDNotAvailableException(
                              "Child ID or ChildVaccineID can't be null");
            }
            List<ChildVaccine> childVaccineListFromSearch = this
                        .findByChildVaccineID(childVaccine.getChildVaccineID());

            if (!childVaccineListFromSearch.isEmpty()) {
                  ChildVaccine childVaccineFromSearch = childVaccineListFromSearch
                              .get(0);
                  this.updateFields(childVaccineFromSearch, childVaccine);
                  this.update(childVaccineFromSearch);
            }

      }

      /**
       * Update fields.
       * 
       * @param childVaccineFromSearch
       *              the child vaccine from search
       * @param childVaccine
       *              the child vaccine
       */
      private void updateFields(ChildVaccine childVaccineFromSearch,
                  ChildVaccine childVaccine) {

            childVaccineFromSearch.setAdministeredDate(childVaccine
                        .getAdministeredDate());
            childVaccineFromSearch.setBatchNo(childVaccine.getBatchNo());
            childVaccineFromSearch.setEndDate(childVaccine.getEndDate());
            childVaccineFromSearch.setStartDate(childVaccine.getStartDate());
            childVaccineFromSearch.setDosage(childVaccine.getDosage());

      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByChildID(java.
       * lang.String)
       */
      @View(name = "by_childID", map = "function(doc){if(doc.type == 'ChildVaccine' && doc.childID){emit(doc.childID, doc._id);}}")
      public List<ChildVaccine> findByChildID(String childID) {
            ViewQuery query = createQuery("by_childID").key(childID)
                        .includeDocs(true);
            return db.queryView(query, ChildVaccine.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByDueStartDate(
       * java.util.Date)
       */
      @View(name = "by_dueStartDate", map = "function(doc){if(doc.type=='ChildVaccine' && doc.startDate){emit(doc.startDate, doc._id);}}")
      public List<ChildVaccine> findByDueStartDate(Date dueStartDate) {
            ViewQuery query = createQuery("by_dueStartDate").key(dueStartDate)
                        .includeDocs(true);
            return db.queryView(query, ChildVaccine.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByDueEndDate(java
       * .util.Date)
       */
      @View(name = "by_dueEndDate", map = "function(doc){if(doc.type=='ChildVaccine' && doc.endDate){emit(doc.endDate, doc._id);}}")
      public List<ChildVaccine> findByDueEndDate(Date dueEndDate) {
            ViewQuery query = createQuery("by_dueStartDate").key(dueEndDate)
                        .includeDocs(true);
            return db.queryView(query, ChildVaccine.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.ektorp.support.CouchDbRepositorySupport#update(java.lang.Object)
       */
      public void update(ChildVaccine childVaccine) {
            //  Auto-generated method stub
            super.update(childVaccine);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByAdministeredDate
       * (java.util.Date)
       */
      public List<ChildVaccine> findByAdministeredDate(Date administeredDate) {
            //  Auto-generated method stub
            return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByChildVaccineID
       * (java.lang.String)
       */
      public List<ChildVaccine> findByChildVaccineID(String childVaccineID) {
            //  Auto-generated method stub
            return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#findByDueDateRange(
       * java.util.Date, java.util.Date)
       */
      @View(name = "by_dueDateRange", map = "function(doc){if(doc.type == '.ChildVaccine' && doc.startDate && doc.endDate){emit();}}")
      public List<ChildVaccine> findByDueDateRange(Date dueStartDate,
                  Date dueEndDate) {
            return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildVaccineRepository#addVaccineSchedule(
       * org.medicmobile.model.VaccineSchedule)
       */
      public void addVaccineSchedule(VaccineSchedule vaccineSchedule) {
            //  Auto-generated method stub

      }

}
