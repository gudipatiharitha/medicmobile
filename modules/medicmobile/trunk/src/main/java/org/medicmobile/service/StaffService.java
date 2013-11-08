package org.medicmobile.service;

import java.util.List;
import org.medicmobile.dto.StaffSearch;
import org.medicmobile.model.Role;
import org.medicmobile.model.Staff;

// : Auto-generated Javadoc
/**
 * The Interface StaffService.
 */
public interface StaffService {

      /**
       * Adds the staff.
       * 
       * @param staff
       *              the staff
       * @return the staff
       */
      Staff addStaff(Staff staff);

      /**
       * Update staff.
       * 
       * @param staff
       *              the staff
       */
      void updateStaff(Staff staff);

      /**
       * Find by name.
       * 
       * @param staffName
       *              the staff name
       * @return the list
       */
      List<Staff> findByName(String staffName);

      /**
       * Find by staff id.
       * 
       * @param staffID
       *              the staff id
       * @return the list
       */
      List<Staff> findByStaffID(String staffID);

      /**
       * Find all staff.
       * 
       * @return the list
       */
      List<Staff> findAllStaff();

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
       * @return the list
       */
      List<Staff> findByAllConstraints(StaffSearch staffSearch);

      /**
       * Find by phone number.
       * 
       * @param phoneNumber
       *              the phone number
       * @return the list
       */
      List<Staff> findByPhoneNumber(String phoneNumber);

      void deleteStaff(String staffID);
}
