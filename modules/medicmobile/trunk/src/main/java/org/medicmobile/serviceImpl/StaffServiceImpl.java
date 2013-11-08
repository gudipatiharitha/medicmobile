package org.medicmobile.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.medicmobile.dto.StaffSearch;
import org.medicmobile.model.Education;
import org.medicmobile.model.Gender;
import org.medicmobile.model.Language;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;
import org.medicmobile.repository.CareGiverRepository;
import org.medicmobile.repository.StaffRepository;
import org.medicmobile.service.StaffService;
import org.medicmobile.util.MedicMobileConstants;
import org.motechproject.commons.date.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class StaffServiceImpl.
 */
@Service(value = "staffService")
public class StaffServiceImpl implements StaffService {

      /** The staff repo. */
      @Resource(name = "staffRepo")
      private StaffRepository staffRepo;

      @Autowired
      private CareGiverRepository careGiverRepository;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#addStaff(org.medicmobile.model
       * .Staff )
       */
      public Staff addStaff(Staff staff) {

            staffRepo.addStaff(staff);
            return staff;

      }

      /**
       * Adds the staff.
       * 
       * @param name
       *              the name
       * @param dateOfBirth
       *              the date of birth
       * @param gender
       *              the gender
       * @param isAlive
       *              the is alive
       * @param staffID
       *              the staff id
       * @param villages
       *              the villages
       * @param religion
       *              the religion
       * @param phoneNumber
       *              the phone number
       * @param address
       *              the address
       * @param isWhetherToReceiveSMS
       *              the is whether to receive sms
       * @param role
       *              the role
       * @param time
       *              the time
       * @param highestEducationalLevel
       *              the highest educational level
       * @param languages
       *              the languages
       * @param annualSalary
       *              the annual salary
       */
      public void addStaff(String name, Date dateOfBirth, Gender gender,
                  boolean isAlive, String staffID, List<String> villages,
                  String religion, String phoneNumber, String address,
                  boolean isWhetherToReceiveSMS, Role role, Time time,
                  Education highestEducationalLevel, List<Language> languages,
                  double annualSalary) {
            Staff staff = new Staff();
            staff.setName(name);
            staff.setAddress(address);
            staff.setAlive(isAlive);
            staff.setGender(gender);
            staff.setDateOfBirth(dateOfBirth);
            staff.setPhoneNumber(phoneNumber);
            staff.setReligion(religion);
            staff.setWhetherToReceiveSMS(isWhetherToReceiveSMS);
            staff.setStaffID(staffID);
            staff.setHighestEducationalLevel(highestEducationalLevel);
            staff.setLanguages(languages);
            staff.setAnnualSalary(annualSalary);
            staff.setRole(role);
            staffRepo.addStaff(staff);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#updateStaff(org.medicmobile.model
       * .Staff)
       */
      public void updateStaff(Staff staff) {
            staffRepo.update(staff);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.service.StaffService#findByName(java.lang.String)
       */
      public List<Staff> findByName(String staffName) {
            return staffRepo.findByName(staffName);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#findByStaffID(java.lang.String)
       */
      public List<Staff> findByStaffID(String staffID) {
            return staffRepo.findByStaffID(staffID);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.service.StaffService#findAllStaff()
       */
      public List<Staff> findAllStaff() {
            return staffRepo.getAll();
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#findByRole(org.medicmobile.model
       * .Role)
       */
      public List<Staff> findByRole(Role role) {
            return staffRepo.findByRole(role);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.service.StaffService#findByRoleAndAnganwadiID(org.
       * medicmobile .model.Role, java.lang.String)
       */
      public List<Staff> findByRoleAndAnganwadiID(Role role, String anganwadiID) {
            return staffRepo.findByRoleAndAnganwadiID(role, anganwadiID);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#findByEmployeeID(java.lang.String)
       */
      public List<Staff> findByEmployeeID(String employeeID) {
            return staffRepo.findByEmployeeID(employeeID);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#findByAllConstraints(org.medicmobile
       * .dto.StaffSearch)
       */
      public List<Staff> findByAllConstraints(StaffSearch staffSearch) {
            Map<String, List<Staff>> staffMapFromRepo = staffRepo
                        .findByAllConstraints(staffSearch);
            if (staffMapFromRepo.containsKey(MedicMobileConstants.STAFF_ID)) {
                  return staffMapFromRepo.get(MedicMobileConstants.STAFF_ID);
            } else if (staffMapFromRepo
                        .containsKey(MedicMobileConstants.EMPLOYEE_ID)) {
                  return this.findByAllConstraintsStaffID(staffMapFromRepo,
                              staffSearch);
            } else if (staffMapFromRepo
                        .containsKey(MedicMobileConstants.ANGANWADI_ID)) {
                  return staffMapFromRepo
                              .get(MedicMobileConstants.ANGANWADI_ID);
            } else if (staffMapFromRepo
                        .containsKey(MedicMobileConstants.STAFF_NAME)) {
                  return this.findByAllConstraintsName(staffMapFromRepo,
                              staffSearch);
            } else if (staffMapFromRepo
                        .containsKey(MedicMobileConstants.STAFF_ROLE)) {
                  return this.findByAllConstraintsRole(staffMapFromRepo,
                              staffSearch);
            }
            return null;
      }

      /**
       * Find by all constraints staff id.
       * removeUnmatchedAnganwadiID(staffListFromRepo,
       * staffSearch.getAnganwadiID());
       * 
       * @param staffMapFromRepo
       *              the staff map from repo
       * @param staffSearch
       *              the staff search
       * @return the list
       */
      private List<Staff> findByAllConstraintsStaffID(
                  Map<String, List<Staff>> staffMapFromRepo,
                  StaffSearch staffSearch) {
            List<Staff> staffListFromRepo = staffMapFromRepo
                        .get(MedicMobileConstants.EMPLOYEE_ID);
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedName(staffListFromRepo,
                              staffSearch.getStaffName());
            }
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedRole(staffListFromRepo,
                              staffSearch.getStaffRole());
            }
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedAnganwadiID(
                              staffListFromRepo, staffSearch.getAnganwadiID());
            }
            return staffListFromRepo;
      }

      /**
       * Gets the empty staff.
       * 
       * @return the empty staff
       */
      public Staff getEmptyStaff() {
            return new Staff();
      }

      /**
       * Find by all constraints name.
       * 
       * @param staffMapFromRepo
       *              the staff map from repo
       * @param staffSearch
       *              the staff search
       * @return the list
       */
      public List<Staff> findByAllConstraintsName(
                  Map<String, List<Staff>> staffMapFromRepo,
                  StaffSearch staffSearch) {
            List<Staff> staffListFromRepo = staffMapFromRepo
                        .get(MedicMobileConstants.STAFF_NAME);
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedRole(staffListFromRepo,
                              staffSearch.getStaffRole());
            }
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedAnganwadiID(
                              staffListFromRepo, staffSearch.getAnganwadiID());
            }

            return staffListFromRepo;
      }

      /**
       * Find by all constraints role.
       * 
       * @param staffMapFromRepo
       *              the staff map from repo
       * @param staffSearch
       *              the staff search
       * @return the list
       */
      public List<Staff> findByAllConstraintsRole(
                  Map<String, List<Staff>> staffMapFromRepo,
                  StaffSearch staffSearch) {
            List<Staff> staffListFromRepo = staffMapFromRepo
                        .get(MedicMobileConstants.STAFF_ROLE);
            if (staffListFromRepo.size() > 0) {
                  staffListFromRepo = removeUnmatchedAnganwadiID(
                              staffListFromRepo, staffSearch.getAnganwadiID());
            }
            return staffListFromRepo;
      }

      /**
       * Removes the unmatched role.
       * 
       * @param staffListFromRepo
       *              the staff list from repo
       * @param staffRole
       *              the staff role
       * @return
       */
      private List<Staff> removeUnmatchedRole(List<Staff> staffListFromRepo,
                  String staffRole) {
            if (staffRole != null) {
                  for (int i = staffListFromRepo.size() - 1; i >= 0; i--) {
                        if (!staffListFromRepo.get(i).getRole()
                                    .equals(Role.valueOf(staffRole))) {
                              staffListFromRepo
                                          .remove(staffListFromRepo.get(i));
                        }
                  }
                  return staffListFromRepo;
            } else {
                  return staffListFromRepo;
            }
      }

      /**
       * Removes the unmatched anganwadi id.
       * 
       * @param staffListFromRepo
       *              the staff list from repo
       * @param anganwadiID
       *              the anganwadi id
       */
      private List<Staff> removeUnmatchedAnganwadiID(
                  List<Staff> staffListFromRepo, String anganwadiID) {
            if (anganwadiID != null) {
                  for (int i = staffListFromRepo.size() - 1; i >= 0; i--) {
                        boolean flag = false;
                        for (int j = 0; (j < staffListFromRepo.get(i)
                                    .getAnganwadiID().size())
                                    && (!flag); j++) {
                              if (staffListFromRepo.get(i).getAnganwadiID()
                                          .get(j).equals(anganwadiID)) {
                                    flag = true;
                              }
                        }
                        if (!flag) {
                              staffListFromRepo
                                          .remove(staffListFromRepo.get(i));
                        }
                  }
                  return staffListFromRepo;
            } else {
                  return staffListFromRepo;
            }
      }

      /**
       * Removes the unmatched name.
       * 
       * @param staffListFromRepo
       *              the staff list from repo
       * @param staffName
       *              the staff name
       */
      private List<Staff> removeUnmatchedName(List<Staff> staffListFromRepo,
                  String staffName) {
            if (staffName != null) {
                  for (int i = staffListFromRepo.size() - 1; i >= 0; i--) {
                        String name = staffListFromRepo.get(i).getName()
                                    .toLowerCase();
                        String searchName = staffName.toLowerCase();
                        if (!name.contains(searchName)) {
                              staffListFromRepo
                                          .remove(staffListFromRepo.get(i));
                        }
                  }
                  return staffListFromRepo;
            } else {
                  return staffListFromRepo;
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.service.StaffService#findByPhoneNumber(java.lang.String
       * )
       */
      @Override
      public List<Staff> findByPhoneNumber(String phoneNumber) {
            return staffRepo.findStaffByPhoneNumber(phoneNumber);
      }

      @Override
      public void deleteStaff(String staffID) {
            if (careGiverRepository.findByLocalVolunteerID(staffID).isEmpty()) {
                  staffRepo.deleteStaff(staffID);
            }
      }

}
