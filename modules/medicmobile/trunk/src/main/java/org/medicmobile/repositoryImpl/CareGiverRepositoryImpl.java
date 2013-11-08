package org.medicmobile.repositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.medicmobile.dto.CareGiverSearch;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.ChildRepository;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * The Class CareGiverRepositoryImpl.
 */
@Repository(value = "careGiverRepo")
public class CareGiverRepositoryImpl extends MotechBaseRepository<CareGiver>
            implements CareGiverRepository {
    
    private static final String BY_CAREGIVER_IDS = "by_careGiverIDS";

      /**
       * Instantiates a new care giver repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      public CareGiverRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(CareGiver.class, db);
            initStandardDesignDocument();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#addCareGiver(org.medicmobile
       * .model.CareGiver)
       */
      public CareGiver addCareGiver(CareGiver careGiver) {

            super.addOrReplace(careGiver, "careGiverID",
                        careGiver.getCareGiverID());
            return careGiver;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.motechproject.commons.couchdb.dao.MotechBaseRepository#getAll()
       */
      @GenerateView
      public List<CareGiver> getAll() {
            ViewQuery query = createQuery(MedicMobileConstants.ALL)
                        .includeDocs(true).limit(
                                    MedicMobileConstants.LIMIT_RESULTS);
            return db.queryView(query, CareGiver.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByName(java.lang
       * .String )
       */
      @View(name = "by_name", map =MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='CareGiver') && doc.name)"
                  + "{"
                  + "var j;var i; var name = doc.name.toLowerCase().replace(/\\s/g, '');for(j=1;j <= name.length;j++)"
                  + "{" + "for(i = 0; i <= (name.length - j); i++)" + "{"
                  + "emit([name.slice(i, i+j),doc._id], doc._id);" + "}" + "}"
                  + "}" + "}", reduce =MedicMobileConstants.REDUCE_FUNCTION + "{"
                  +MedicMobileConstants.RETURN + "}")
      public List<CareGiver> findByName(String careGiverName) {
            ViewQuery query = createQuery("by_name")
                        .startKey(ComplexKey.of(careGiverName))
                        .endKey(ComplexKey.of(careGiverName,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> careGiverDocIDSList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  careGiverDocIDSList.add(row.getValue());
            }
            return this.findByDocIDS(careGiverDocIDSList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByCareGiverIDS(java
       * .util.List)
       */
      @View(name = BY_CAREGIVER_IDS, map = "function(doc){if(doc.type=='CareGiver' && doc.careGiverID){emit(doc.careGiverID, doc._id)}}")
      public List<CareGiver> findByCareGiverIDS(List<String> careGiverIDS) {
            ViewQuery query = createQuery(BY_CAREGIVER_IDS).keys(careGiverIDS)
                        .includeDocs(true);
            return db.queryView(query, CareGiver.class);
      }

      /**
       * Find by care giver id.
       * 
       * @param careGiverID
       *              the care giver id
       * @return the list
       */
      @View(name = "by_careGiverID", map = "function(doc){if(doc.type=='CareGiver' && doc.careGiverID){emit(doc.careGiverID, doc._id)}}")
      public List<CareGiver> findByCareGiverID(String careGiverID) {
            ViewQuery query = createQuery("by_careGiverID").key(careGiverID)
                        .includeDocs(true);
            return db.queryView(query, CareGiver.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByCareGiverIDS(java
       * .lang.String)
       */
      public List<CareGiver> findByCareGiverIDS(String careGiverID) {
            ViewQuery query = createQuery(BY_CAREGIVER_IDS).key(careGiverID)
                        .includeDocs(true);
            return db.queryView(query, CareGiver.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByIDCardNumber(java
       * .lang.String)
       */
      @View(name = "by_idCardNumber", map = MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='CareGiver') && doc.idCardNumber)"
                  + "{"
                  + "var j;var i; var idCardNumber = doc.idCardNumber.toLowerCase().replace(/\\s/g, '');for(j=1;j <= idCardNumber.length;j++)"
                  + "{" + "for(i = 0; i <= (idCardNumber.length - j); i++)"
                  + "{"
                  + "emit([idCardNumber.slice(i, i+j), doc._id], doc._id);"
                  + "}" + "}" + "}" + "}", reduce =MedicMobileConstants.REDUCE_FUNCTION
                  + "{" +MedicMobileConstants.RETURN + "}")
      public List<CareGiver> findByIDCardNumber(String idCardNumber) {
            ViewQuery query = createQuery("by_idCardNumber")
                        .startKey(ComplexKey.of(idCardNumber))
                        .endKey(ComplexKey.of(idCardNumber,
                                    ComplexKey.emptyObject())).group(true)
                        .groupLevel(2);
            List<String> careGiverDocIDSList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  careGiverDocIDSList.add(row.getValue());
            }
            return this.findByDocIDS(careGiverDocIDSList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByVillage(java.lang
       * .String)
       */
      @View(name = "by_village", map =MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='CareGiver') && doc.village)"
                  + "{"
                  + "var j;var i; var village = doc.village.toLowerCase().replace(/\\s/g, '');"
                  + "for(j=1;j <= village.length;j++)" + "{"
                  + "for(i = 0; i <= (village.length - j); i++)" + "{"
                  + "emit([village.slice(i, i+j),doc._id], doc._id);" + "}"
                  + "}" + "}" + "}", reduce =MedicMobileConstants.REDUCE_FUNCTION + "{"
                  +MedicMobileConstants.RETURN + "}")
      public List<CareGiver> findByVillage(String village) {
            ViewQuery query = createQuery("by_village")
                        .startKey(ComplexKey.of(village))
                        .endKey(ComplexKey.of(village, ComplexKey.emptyObject()))
                        .reduce(true).group(true).groupLevel(2);
            List<String> careGiverDocIDSList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  careGiverDocIDSList.add(row.getValue());
            }
            return this.findByDocIDS(careGiverDocIDSList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByChildName(java
       * .lang .String)
       */
      public List<CareGiver> findByChildName(String childName) {
            ChildRepository childRepo = new ChildRepositoryImpl(db);
            List<Child> childList = childRepo.findByName(childName);
            Set<CareGiver> careGiverSet = new HashSet<CareGiver>();
            ViewQuery query;
            for (Child child : childList) {
                  query = createQuery(BY_CAREGIVER_IDS).key(
                              child.getCareGiverID()).includeDocs(true);
                  careGiverSet.addAll(db.queryView(query, CareGiver.class));
            }
            return new ArrayList<CareGiver>(careGiverSet);

      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByChildBirthDate
       * (java .util.Date, java.util.Date)
       */
      public List<CareGiver> findByChildBirthDate(Date startDate, Date endDate) {
            ChildRepository childRepo = new ChildRepositoryImpl(db);
            List<Child> childList = childRepo.findByDateOfBirthRange(startDate,
                        endDate);
            Set<CareGiver> careGiverSet = new HashSet<CareGiver>();
            ViewQuery query;
            for (Child child : childList) {
                  query = createQuery(BY_CAREGIVER_IDS).key(
                              child.getCareGiverID()).includeDocs(true);
                  careGiverSet.addAll(db.queryView(query, CareGiver.class));
            }
            return new ArrayList<CareGiver>(careGiverSet);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByLocalVolunteerID
       * (java.lang.String)
       */
      @View(name = "by_localVolunteerID", map =MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='CareGiver') && doc.localVolunteerID)"
                  + "{"
                  + "var j;var i; var localVolunteerID = doc.localVolunteerID.toLowerCase().replace(/\\s/g, '');"
                  + "for(j=1;j <= localVolunteerID.length;j++)" + "{"
                  + "for(i = 0; i <= (localVolunteerID.length - j); i++)" + "{"
                  + "emit([localVolunteerID.slice(i, i+j),doc._id], doc._id);"
                  + "}" + "}" + "}" + "}", reduce =MedicMobileConstants.REDUCE_FUNCTION
                  + "{" +MedicMobileConstants.RETURN + "}")
      public List<CareGiver> findByLocalVolunteerID(String localVolunteerID) {
            ViewQuery query = createQuery("by_localVolunteerID")
                        .startKey(ComplexKey.of(localVolunteerID))
                        .endKey(ComplexKey.of(localVolunteerID,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> careGiverDocIDSList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  careGiverDocIDSList.add(row.getValue());
            }
            return this.findByDocIDS(careGiverDocIDSList);
      }

      /**
       * Find by doc ids.
       * 
       * @param docIDS
       *              the doc ids
       * @return the list
       */
      @View(name = "by_docIDS", map = "function(doc){if(doc.type=='CareGiver'){emit(doc._id, doc._id);}}")
      public List<CareGiver> findByDocIDS(List<String> docIDS) {
            ViewQuery query = createQuery("by_docIDS").keys(docIDS)
                        .includeDocs(true);
            return db.queryView(query, CareGiver.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByAnganwadiCenterID
       * (java.lang.String)
       */
      @View(name = "by_anganwadiID", map =MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='CareGiver') && doc.anganwadiID)"
                  + "{"
                  + "var j;var i; var anganwadiID = doc.anganwadiID.toLowerCase().replace(/\\s/g, '');"
                  + "for(j=1;j <= anganwadiID.length;j++)" + "{"
                  + "for(i = 0; i <= (anganwadiID.length - j); i++)" + "{"
                  + "emit([anganwadiID.slice(i, i+j),doc._id], doc._id);" + "}"
                  + "}" + "}" + "}", reduce =MedicMobileConstants.REDUCE_FUNCTION + "{"
                  +MedicMobileConstants.RETURN + "}")
      public List<CareGiver> findByAnganwadiCenterID(String anganwadiID) {
            ViewQuery query = createQuery("by_anganwadiID")
                        .startKey(ComplexKey.of(anganwadiID))
                        .endKey(ComplexKey.of(anganwadiID,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> careGiverDocIDSList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  careGiverDocIDSList.add(row.getValue());
            }
            return this.findByDocIDS(careGiverDocIDSList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#addChild(org.medicmobile
       * .model.Child)
       */
      public void addChild(Child child) {
            if (child.getChildID() == null && child.getDateOfBirth() != null) {
                  child.setChildID(UtilService.generateChildId());
                  db.create(child);
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.ektorp.support.CouchDbRepositorySupport#update(java.lang.Object)
       */
      public void update(CareGiver careGiver) {
            super.update(careGiver);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.CareGiverRepository#findByAllConstraints(org
       * .medicmobile.dto.CareGiverSearch)
       */
      public Map<String, List<CareGiver>> findByAllConstraints(
                  CareGiverSearch careGiverSearch) {
            Map<String, List<CareGiver>> careGiverMap = new HashMap<String, List<CareGiver>>();
            if (careGiverSearch.getCareGiverID() != null) {
                  careGiverMap.put(MedicMobileConstants.CAREGIVER_ID, this
                              .findByCareGiverIDS(careGiverSearch
                                          .getCareGiverID()));
                  return careGiverMap;
            } else if (careGiverSearch.getIdCardNumber() != null) {
                  careGiverMap.put(MedicMobileConstants.ID_CARD_NUMBER, this
                              .findByIDCardNumber(careGiverSearch
                                          .getIdCardNumber()));
                  return careGiverMap;
            } else if (careGiverSearch.getCareGiverName() != null) {
                  careGiverMap.put(MedicMobileConstants.CARE_GIVER_NAME, this
                              .findByName(careGiverSearch.getCareGiverName()));
                  return careGiverMap;
            } else if (careGiverSearch.getChildName() != null) {
                  careGiverMap.put(MedicMobileConstants.CHILD_NAME, this
                              .findByChildName(careGiverSearch.getChildName()));
                  return careGiverMap;
            } else if ((careGiverSearch.getStartDate() != null)
                        && (careGiverSearch.getEndDate() != null)) {
                  careGiverMap.put(MedicMobileConstants.CHILD_BIRTH_DATE, this
                              .findByChildBirthDate(
                                          careGiverSearch.getStartDate(),
                                          careGiverSearch.getEndDate()));
                  return careGiverMap;
            } else if (careGiverSearch.getLocalVolunteerID() != null) {
                  careGiverMap.put(MedicMobileConstants.LOCAL_VOLUNTEER, this
                              .findByLocalVolunteerID(careGiverSearch
                                          .getLocalVolunteerID()));
                  return careGiverMap;
            } else if (careGiverSearch.getVillage() != null) {
                  careGiverMap.put(MedicMobileConstants.VILLAGE,
                              this.findByVillage(careGiverSearch.getVillage()));
                  return careGiverMap;
            } else if (careGiverSearch.getAnganwadiID() != null) {
                  careGiverMap.put(MedicMobileConstants.ANGANWADI_CENTER, this
                              .findByAnganwadiCenterID(careGiverSearch
                                          .getAnganwadiID()));
                  return careGiverMap;
            } else {
                  careGiverMap.put(MedicMobileConstants.ALL, this.getAll());
                  return careGiverMap;
            }

      }

      @Override
      public void deleteCareGiver(String careGiverID) {
            CareGiver careGiver = findByCareGiverID(careGiverID).get(0);
            safeRemove(careGiver);
      }
}
