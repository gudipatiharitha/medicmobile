package org.medicmobile.repositoryImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.View;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;
import org.medicmobile.model.ChildVaccine;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.ChildRepository;
import org.medicmobile.util.UtilService;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * The Class ChildRepositoryImpl.
 */
@Repository(value = "childRepo")
public class ChildRepositoryImpl extends MotechBaseRepository<Child> implements
            ChildRepository {

      /**
       * Instantiates a new child repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      public ChildRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(Child.class, db);
            initStandardDesignDocument();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#addChild(org.medicmobile
       * .model .Child)
       */
      public void addChild(Child child) {
            if (child.getChildID() == null || "".equals(child.getChildID())) {
                  child.setChildID(UtilService.generateChildId());
                  List<Child> childListFromSearch;
                  while (true) {
                        childListFromSearch = this.findByChildIDS(child
                                    .getChildID());
                        if (childListFromSearch == null
                                    || childListFromSearch.isEmpty()) {
                              if (child.getChildSystemId() == null) {
                                    child.setChildSystemId("");
                              }
                              super.add(child);
                              break;
                        } else {
                              child.setChildID(UtilService.generateChildId());
                        }
                  }
            } else {
                  List<Child> childListFromSearch = this.findByChildIDS(child
                              .getChildID());
                  if (!childListFromSearch.isEmpty()) {
                        Child childFromSearch = childListFromSearch.get(0);
                        this.updateFields(childFromSearch, child);
                        this.update(childFromSearch);
                  } else {
                        super.add(child);
                  }
            }
      }

      /**
       * Update fields.
       * 
       * @param childFromSearch
       *              the child from search
       * @param child
       *              the child
       */
      private void updateFields(Child childFromSearch, Child child) {
            childFromSearch.setName(child.getName());
            childFromSearch.setDateOfBirth(child.getDateOfBirth());
            /*
             * if(child.getDateOfBirth().compareTo(childFromSearch.getDateOfBirth
             * ()) != 0) {
             * updateChildCalendar(childFromSearch.getChildCalendarID()); }
             */
            childFromSearch.setChildCalendarID(child.getChildCalendarID());
            childFromSearch.setNameInHindi(child.getNameInHindi());
            childFromSearch.setAlive(child.getAlive());
            childFromSearch.setGender(child.getGender());
            childFromSearch.setCareGiverID(child.getCareGiverID());
            childFromSearch.setChildCalendarID(child.getChildCalendarID());
            childFromSearch.setMctsID(child.getMctsID());
            childFromSearch.setPlaceOfDelivery(child.getPlaceOfDelivery());
            childFromSearch.setWeight(child.getWeight());
            childFromSearch.setHeight(child.getHeight());
            childFromSearch.setChildSystemId(child.getChildSystemId());
            childFromSearch.setBloodGroup(child.getBloodGroup());
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.ektorp.support.CouchDbRepositorySupport#update(java.lang.Object)
       */
      public void update(Child child) {
            super.update(child);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByChildID(java.lang.
       * String )
       */
      @View(name = "by_childID", map = "function(doc)" + "{"
                  + "if((doc.type=='Child') && doc.childID)" + "{"
                  + "emit(doc.childID, doc._id);" + "}" + "}")
      public List<Child> findByChildID(String childID) {
            ViewQuery query = createQuery("by_childID").key(childID)
                        .includeDocs(true);

            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByName(java.lang.String)
       */
      @View(name = "by_name", map = "function(doc)"
                  + "{"
                  + "if((doc.type=='Child') && doc.name)"
                  + "{"
                  + "var j;var i; var name = doc.name.toLowerCase().replace(/\\s/g, '');for(j=1;j <= name.length;j++)"
                  + "{" + "for(i = 0; i <= (name.length - j); i++)" + "{"
                  + "emit([name.slice(i, i+j),doc._id], doc._id);" + "}" + "}"
                  + "}" + "}", reduce = "function(keys, values)" + "{"
                  + "return values[0];" + "}")
      public List<Child> findByName(String childName) {
            ViewQuery query = createQuery("by_name")
                        .startKey(ComplexKey.of(childName))
                        .endKey(ComplexKey.of(childName,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> childDocIDList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  childDocIDList.add(row.getValue());
            }
            return this.findByDocIDS(childDocIDList);
      }

      @View(name = "by_docIDS", map = "function(doc){if(doc.type=='Child'){emit(doc._id, doc._id);}}")
      public List<Child> findByDocIDS(List<String> childDocIDList) {
            ViewQuery query = createQuery("by_docIDS").keys(childDocIDList)
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      @View(name = "by_childSystemId", map = "function(doc) {if(doc.type == 'Child'){emit(doc.childSystemId, null);}}")
      public List<Child> findByChildSystemId() {
            ViewQuery query = createQuery("by_childSystemId").key("")
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByDateOfBirth(java.util
       * .Date)
       */
      @View(name = "by_dateOfBirth", map = "function(doc){if((doc.type=='Child') && doc.dateOfBirth){emit(doc.dateOfBirth, doc._id);}}")
      public List<Child> findByDateOfBirth(Date dateOfBirth) {
            ViewQuery query = createQuery("by_dateOfBirth").key(dateOfBirth)
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#addChildVaccine(org.medicmobile
       * .model.ChildVaccine)
       */
      public void addChildVaccine(ChildVaccine childVaccine) {
            db.create(childVaccine);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByDateOfBirthRange(java
       * .util.Date, java.util.Date)
       */
      @View(name = "by_dateOfBirthRange", map = "function(doc){if(doc.type=='Child'){var date = doc.dateOfBirth.substring(0,doc.dateOfBirth.indexOf('T')).split('-');var i;var str = [];for(i in date){str.push(date[i]);}emit(str, doc._id);}}")
      public List<Child> findByDateOfBirthRange(Date startDate, Date endDate) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            String startMonthString = null;
            String startDateString = null;
            String endMonthString = null;
            String endDateString = null;
            String startYearString = "" + start.get(Calendar.YEAR);
            String endYearString = "" + end.get(Calendar.YEAR);
            if ((start.get(Calendar.MONTH) + 1) < 10) {
                  startMonthString = "0" + (start.get(Calendar.MONTH) + 1);
            } else {
                  startMonthString = "" + (start.get(Calendar.MONTH) + 1);
            }
            if (start.get(Calendar.DAY_OF_MONTH) < 10) {
                  startDateString = "0" + start.get(Calendar.DAY_OF_MONTH);
            } else {
                  startDateString = "" + start.get(Calendar.DAY_OF_MONTH);
            }
            if ((end.get(Calendar.MONTH) + 1) < 10) {
                  endMonthString = "0" + (end.get(Calendar.MONTH) + 1);
            } else {
                  endMonthString = "" + (end.get(Calendar.MONTH) + 1);
            }
            if (end.get(Calendar.DAY_OF_MONTH) < 10) {
                  endDateString = "0" + end.get(Calendar.DAY_OF_MONTH);
            } else {
                  endDateString = "" + end.get(Calendar.DAY_OF_MONTH);
            }
            ViewQuery query = createQuery("by_dateOfBirthRange")
                        .startKey(ComplexKey.of(startYearString,
                                    startMonthString, startDateString))
                        .endKey(ComplexKey.of(endYearString, endMonthString,
                                    endDateString)).includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByAllConstraints(java.
       * util.Map)
       */
      @View(name = "by_allConstraints", map = "function(doc){if((doc.type=='Child')){emit(doc.childID, doc._id);}}")
      public List<Child> findByAllConstraints(Map<String, Object> constraints) {
            ViewQuery query = createQuery("by_allConstraints")
                        .startKey(ComplexKey.of(null, constraints.get("role"),
                                    null))
                        .endKey(ComplexKey.of("A\ufff0",
                                    constraints.get("role"), "\ufff0"))
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByCareGiverName(java
       * .lang .String)
       */
      public List<Child> findByCareGiverName(String careGiverName) {
            CareGiverRepository careGiverRepo = new CareGiverRepositoryImpl(db);
            List<CareGiver> careGiverList = careGiverRepo
                        .findByName(careGiverName);
            List<Child> childList = new ArrayList<Child>();
            for (CareGiver careGiver : careGiverList) {
                  childList.addAll(careGiver.getChildren());
            }

            return childList;
      }

      /**
       * Find by child ids.
       * 
       * @param childIDS
       *              the child ids
       * @return the list
       */
      @View(name = "by_childIDS", map = "function(doc){if(doc.type=='Child' && doc.childID){emit(doc.childID, doc._id);}}")
      private List<Child> findByChildIDS(List<String> childIDS) {
            ViewQuery query = createQuery("by_childIDS").keys(childIDS)
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /**
       * Find by child ids.
       * 
       * @param childID
       *              the child id
       * @return the list
       */
      private List<Child> findByChildIDS(String childID) {
            ViewQuery query = createQuery("by_childIDS").key(childID)
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ChildRepository#findByCareGiverID(java.lang
       * .String)
       */
      @View(name = "by_careGiverID", map = "function(doc){if(doc.type == 'Child' && doc.careGiverID){emit(doc.careGiverID, doc._id);}}")
      public List<Child> findByCareGiverID(String careGiverID) {
            ViewQuery query = createQuery("by_careGiverID").key(careGiverID)
                        .includeDocs(true);
            return db.queryView(query, Child.class);
      }

      @Override
      public void deleteChild(String childID) {
            Child child = findByChildID(childID).get(0);
            safeRemove(child);

      }
}
