package org.medicmobile.repository;

import java.util.List;
import java.util.Map;

import org.medicmobile.dto.StaffSearch;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;

// : Auto-generated Javadoc
/**
 * The Interface StaffRepository.
 */
public interface StaffRepository {

      /**
       * Adds the staff.
       * 
       * @param staff
       *              the staff
       */
      void addStaff(Staff staff);

      /**
       * Find by staff id.
       * 
       * @param staffID
       *              the staff id
       * @return the list
       */
      List<Staff> findByStaffID(String staffID);

      /**
       * Gets the all.
       * 
       * @return the all
       */
      List<Staff> getAll();

      /**
       * Find by name.
       * 
       * @param staffName
       *              the staff name
       * @return the list
       */
      List<Staff> findByName(String staffName);

      /**
       * Find by role.
       * 
       * @param role
       *              the role
       * @return the list
       */
      List<Staff> findByRole(Role role);

      /**
       * Find by role and anganwadi id.
       * 
       * @param role
       *              the role
       * @param anganwadiID
       *              the anganwadi id
       * @return the list
       */
      List<Staff> findByRoleAndAnganwadiID(Role role, String anganwadiID);

      /**
       * Find by anganwadi id.
       * 
       * @param anganwadiID
       *              the anganwadi id
       * @return the list
       */
      List<Staff> findByAnganwadiID(String anganwadiID);

      /**
       * Find by employee id.
       * 
       * @param employeeID
       *              the employee id
       * @return the list
       */
      List<Staff> findByEmployeeID(String employeeID);

      /**
       * Find by all constraints.
       * 
       * @param staffSearch
       *              the staff search
       * @return the map
       */
      Map<String, List<Staff>> findByAllConstraints(StaffSearch staffSearch);

      /**
       * Update.
       * 
       * @param staff
       *              the staff
       */
      void update(Staff staff);

      /**
       * Find staff by phone number.
       * 
       * @param phoneNumber
       *              the phone number
       * @return the list
       */
      List<Staff> findStaffByPhoneNumber(String phoneNumber);

      void deleteStaff(String staffID);
}
