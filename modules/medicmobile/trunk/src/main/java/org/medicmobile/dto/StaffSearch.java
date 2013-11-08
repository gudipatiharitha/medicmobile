package org.medicmobile.dto;

// : Auto-generated Javadoc
/**
 * The Class StaffSearch.
 */
public final class StaffSearch {

      /** The staff id. */
      private String employeeID;

      /** The staff role. */
      private String staffRole;

      /** The anganwadi id. */
      private String anganwadiID;

      /** The staff name. */
      private String staffName;

      private String staffID;

      /**
       * Gets the staff id.
       * 
       * @return the employee id
       */
      public String getEmployeeID() {
            return employeeID;
      }

      /**
       * Sets the staff id.
       * 
       * @param staffID
       *              the new employee id
       */
      public void setEmployeeID(final String employeeID) {
            this.employeeID = employeeID.toLowerCase();
      }

      /**
       * Gets the staff role.
       * 
       * @return the staff role
       */
      public String getStaffRole() {
            return staffRole;
      }

      /**
       * Sets the staff role.
       * 
       * @param staffRole
       *              the new staff role
       */
      public void setStaffRole(final String staffRole) {
            this.staffRole = staffRole;
      }

      /**
       * Gets the anganwadi id.
       * 
       * @return the anganwadi id
       */
      public String getAnganwadiID() {
            return anganwadiID;
      }

      /**
       * Sets the anganwadi id.
       * 
       * @param anganwadiID
       *              the new anganwadi id
       */
      public void setAnganwadiID(final String anganwadiID) {
            this.anganwadiID = anganwadiID.toLowerCase();
      }

      /**
       * Gets the staff name.
       * 
       * @return the staff name
       */
      public String getStaffName() {
            return staffName;
      }

      /**
       * Sets the staff name.
       * 
       * @param staffName
       *              the new staff name
       */
      public void setStaffName(final String staffName) {
            this.staffName = staffName.toLowerCase();
      }

      public String getStaffID() {
            return staffID;
      }

      public void setStaffID(String staffID) {
            this.staffID = staffID.toLowerCase();
      }
}
