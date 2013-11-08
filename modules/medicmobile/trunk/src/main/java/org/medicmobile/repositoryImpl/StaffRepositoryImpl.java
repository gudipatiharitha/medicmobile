package org.medicmobile.repositoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.medicmobile.dto.StaffSearch;
import org.medicmobile.model.CareGiverView;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.StaffRepository;
import org.medicmobile.serviceImpl.CareGiverServiceImpl;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * The Class StaffRepositoryImpl.
 */
@Repository(value = "staffRepo")
public class StaffRepositoryImpl extends MotechBaseRepository<Staff> implements
            StaffRepository {

      private static Logger logger = Logger.getLogger(StaffRepositoryImpl.class);
      @Autowired
      private CareGiverServiceImpl careGiverServiceImpl;

      /**
       * Instantiates a new staff repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      public StaffRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {

            super(Staff.class, db);
            initStandardDesignDocument();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#addStaff(org.medicmobile
       * .model .Staff)
       */
      public void addStaff(Staff staff) {
            if (staff.getStaffID().equals("")) {
                  staff.setStaffID(UtilService.generateUniqueID());
            }
            List<Staff> staffListFromSearch = this.findByStaffID(staff
                        .getStaffID());
            if (!staffListFromSearch.isEmpty()) {
                  Staff staffFromSearch = staffListFromSearch.get(0);
                  this.updateFields(staff, staffFromSearch);
                  this.update(staffFromSearch);
                  return;
            }
            try {
                  super.add(staff);
            } catch (IllegalArgumentException e) {
                logger.debug(e.getMessage());
            }

      }

      /**
       * Update fields.
       * 
       * @param staff
       *              the staff
       * @param staffFromSearch
       *              the staff from search
       */
      private void updateFields(Staff staff, Staff staffFromSearch) {
            staffFromSearch.setName(staff.getName());
            staffFromSearch.setAddress(staff.getAddress());
            staffFromSearch.setAlive(staff.getAlive());
            staffFromSearch.setGender(staff.getGender());
            staffFromSearch.setRole(staff.getRole());
            staffFromSearch.setDateOfBirth(staff.getDateOfBirth());
            staffFromSearch.setPhoneNumber(staff.getPhoneNumber());
            staffFromSearch.setReligion(staff.getReligion());
            staffFromSearch.setAnganwadiID(staff.getAnganwadiID());
            staffFromSearch.setWhetherToReceiveSMS(staff
                        .getWhetherToReceiveSMS());
            staffFromSearch.setEmployeeID(staff.getEmployeeID());
            staffFromSearch.setHighestEducationalLevel(staff
                        .getHighestEducationalLevel());
            staffFromSearch.setLanguages(staff.getLanguages());
            staffFromSearch.setAnnualSalary(staff.getAnnualSalary());
            staffFromSearch.setState(staff.getState());
            staffFromSearch.setDistrict(staff.getDistrict());
            if (staff.getRole().equals(Role.LOCAL_VOLUNTEER)) {
                  List<CareGiverView> careGiverList = careGiverServiceImpl
                              .findByLocalVolunteerID(staff.getStaffID());
                  for (CareGiverView careGiverView : careGiverList) {
                        careGiverView.getCareGiver().setAnganwadiID(
                                    staff.getAnganwadiID().get(0));
                        careGiverServiceImpl.addCareGiver(careGiverView
                                    .getCareGiver());
                  }
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByStaffID(java.lang.
       * String )
       */
      @View(name = "by_staffID", map = MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='Staff') && doc.staffID)"
                  + "{"
                  + "var j;var i; var staffID = doc.staffID.toLowerCase().replace(/\\s/g, '');for(j=1;j <= staffID.length;j++)"
                  + "{" + "for(i = 0; i <= (staffID.length - j); i++)" + "{"
                  + "emit([staffID.slice(i, i+j), doc._id], doc._id);" + "}"
                  + "}" + "}" + "}", reduce = MedicMobileConstants.REDUCE_FUNCTION + "{"
                  + MedicMobileConstants.RETURN + "}")
      public List<Staff> findByStaffID(String staffID) {
            ViewQuery query = createQuery("by_staffID")
                        .startKey(ComplexKey.of(staffID))
                        .endKey(ComplexKey.of(staffID, ComplexKey.emptyObject()))
                        .reduce(true).group(true);
            List<String> staffIDList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  staffIDList.add(row.getValue());
            }
            return this.findByStaffIDS(staffIDList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.motechproject.commons.couchdb.dao.MotechBaseRepository#getAll()
       */
      @GenerateView
      public List<Staff> getAll() {
            ViewQuery query = createQuery("all").includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByName(java.lang.String)
       */
      @View(name = "by_name", map = MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='Staff') && doc.name)"
                  + "{"
                  + "var j;var i; var name = doc.name.toLowerCase().replace(/\\s/g, '');for(j=1;j <= name.length;j++)"
                  + "{" + "for(i = 0; i <= (name.length - j); i++)" + "{"
                  + "emit([name.slice(i, i+j), doc._id], doc._id);" + "}" + "}"
                  + "}" + "}", reduce = MedicMobileConstants.REDUCE_FUNCTION + "{"
                  + MedicMobileConstants.RETURN + "}")
      public List<Staff> findByName(String staffName) {
            ViewQuery query = createQuery("by_name")
                        .startKey(ComplexKey.of(staffName))
                        .endKey(ComplexKey.of(staffName,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true);
            List<String> staffIDList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  staffIDList.add(row.getValue());
            }
            return this.findByStaffIDS(staffIDList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByRole(org.medicmobile
       * .model.Role)
       */
      @View(name = "by_role", map = MedicMobileConstants.FUNCTION + "{"
                  + "if((doc.type == 'Staff') && doc.role)" + "{"
                  + "emit(doc.role, doc._id);" + "}" + "}")
      public List<Staff> findByRole(Role role) {
            ViewQuery query = createQuery("by_role").key(role)
                        .includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByRoleAndAnganwadiID(org
       * .medicmobile.model.Role, java.lang.String)
       */
      @View(name = "by_role_anganwadiID", map = "function(doc) {if(doc.type=='Staff'){for(var i in doc.anganwadiID){emit([doc.role,doc.anganwadiID[i]], doc._id);}}}")
      public List<Staff> findByRoleAndAnganwadiID(Role role, String anganwadiID) {
            ViewQuery query = createQuery("by_role_anganwadiID")
                        .startKey(ComplexKey.of(role, anganwadiID))
                        .endKey(ComplexKey.of(role, anganwadiID,
                                    ComplexKey.emptyObject()))
                        .includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      /**
       * Find by village and role.
       * 
       * @param village
       *              the village
       * @param role
       *              the role
       * @return the list
       */
      @View(name = "by_village", map = "function(doc){if((doc.type=='Staff') && doc.villages){emit(doc.village, doc._id);}}")
      public List<Staff> findByVillageAndRole(String village, String role) {
            ViewQuery query = createQuery("by_village")
                        .startKey(village)
                        .endKey(ComplexKey.of(village, ComplexKey.emptyObject()))
                        .includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByAnganwadiID(java.lang
       * .String)
       */
      @View(name = "by_anganwadiID", map = MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='Staff') && doc.anganwadiID)"
                  + "{"
                  + "for(var k in doc.anganwadiID)"
                  + "{"
                  + "var j;var i; var anganwadiID = doc.anganwadiID[k].toLowerCase().replace(/\\s/g, '');"
                  + "for(j=1;j <= anganwadiID.length;j++)" + "{"
                  + "for(i = 0; i <= (anganwadiID.length - j); i++)" + "{"
                  + "emit([anganwadiID.slice(i, i+j), doc._id], doc._id);"
                  + "}" + "}" + "}" + "}" + "}", reduce = MedicMobileConstants.REDUCE_FUNCTION
                  + "{" + MedicMobileConstants.RETURN + "}")
      public List<Staff> findByAnganwadiID(String anganwadiID) {
            ViewQuery query = createQuery("by_anganwadiID")
                        .startKey(ComplexKey.of(anganwadiID))
                        .endKey(ComplexKey.of(anganwadiID,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> staffIDList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  staffIDList.add(row.getValue());
            }
            return this.findByStaffIDS(staffIDList);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByEmployeeID(java.lang
       * .String)
       */
      @View(name = "by_employeeID", map = MedicMobileConstants.FUNCTION
                  + "{"
                  + "if((doc.type=='Staff') && doc.employeeID)"
                  + "{"
                  + "var j;var i; var employeeID = doc.employeeID.toLowerCase().replace(/\\s/g, '');for(j=1;j <= employeeID.length;j++)"
                  + "{" + "for(i = 0; i <= (employeeID.length - j); i++)" + "{"
                  + "emit([employeeID.slice(i, i+j), doc._id], doc._id);" + "}"
                  + "}" + "}" + "}", reduce = MedicMobileConstants.REDUCE_FUNCTION + "{"
                  + MedicMobileConstants.RETURN + "}")
      public List<Staff> findByEmployeeID(String employeeID) {
            ViewQuery query = createQuery("by_employeeID")
                        .startKey(ComplexKey.of(employeeID))
                        .endKey(ComplexKey.of(employeeID,
                                    ComplexKey.emptyObject())).reduce(true)
                        .group(true).groupLevel(2);
            List<String> staffIDList = new ArrayList<String>();
            for (Row row : db.queryView(query).getRows()) {
                  staffIDList.add(row.getValue());
            }
            return this.findByStaffIDS(staffIDList);
      }

      /**
       * Find by staff ids.
       * 
       * @param staffIDS
       *              the staff ids
       * @return the list
       */
      @View(name = "by_docIDS", map = "function(doc){if(doc.type=='Staff' && doc.staffID){emit(doc._id, doc._id)}}")
      private List<Staff> findByStaffIDS(List<String> staffIDS) {
            ViewQuery query = createQuery("by_docIDS").keys(staffIDS)
                        .includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findByAllConstraints(org.
       * medicmobile.dto.StaffSearch)
       */
      public Map<String, List<Staff>> findByAllConstraints(
                  StaffSearch staffSearch) {
            Map<String, List<Staff>> staffMap = new HashMap<String, List<Staff>>();
            if (staffSearch.getStaffID() != null) {
                  staffMap.put(MedicMobileConstants.STAFF_ID,
                              this.findByStaffID(staffSearch.getStaffID()));
                  return staffMap;
            }
            if (staffSearch.getEmployeeID() != null) {
                  staffMap.put(MedicMobileConstants.EMPLOYEE_ID, this
                              .findByEmployeeID(staffSearch.getEmployeeID()));
                  return staffMap;
            } else if (staffSearch.getStaffName() != null) {
                  staffMap.put(MedicMobileConstants.STAFF_NAME,
                              this.findByName(staffSearch.getStaffName()));
                  return staffMap;
            } else if (staffSearch.getStaffRole() != null) {
                  if (!staffSearch.getStaffRole().equals(
                              MedicMobileConstants.ALL)) {
                        staffMap.put(MedicMobileConstants.STAFF_ROLE, this
                                    .findByRole(Role.valueOf(staffSearch
                                                .getStaffRole())));
                  } else {
                        staffMap.put(MedicMobileConstants.STAFF_ROLE,
                                    this.getAll());
                  }
                  return staffMap;
            } else if (staffSearch.getAnganwadiID() != null) {
                  staffMap.put(MedicMobileConstants.ANGANWADI_ID, this
                              .findByAnganwadiID(staffSearch.getAnganwadiID()));
                  return staffMap;
            }
            Map<String, List<Staff>> allMap = new HashMap<String, List<Staff>>();
            allMap.put(MedicMobileConstants.STAFF_ID, this.getAll());
            return allMap;
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.StaffRepository#findStaffByPhoneNumber(java
       * .lang.String)
       */
      @View(name = "by_phoneNumber", map = "function(doc){"
                  + "if((doc.type=='Staff') && doc.phoneNumber)"
                  + "{emit(doc.phoneNumber , doc._id);}" + "}")
      public List<Staff> findStaffByPhoneNumber(String phoneNumber) {
            ViewQuery query = createQuery("by_phoneNumber").key(phoneNumber)
                        .includeDocs(true);
            return db.queryView(query, Staff.class);
      }

      @Override
      public void deleteStaff(String staffID) {
            Staff staff = findByStaffID(staffID).get(0);
            safeRemove(staff);
      }
}
