package org.medicmobile.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

/**
 * The Class Staff.
 */
@TypeDiscriminator("doc.type ==='Staff'")
public class Staff extends DetailedPerson {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 1589406128864326643L;

      /** The employee id. */
      @JsonProperty
      private String employeeID;

      /** The staff id. */
      @JsonProperty
      private String staffID;

      /** The anganwadi id. */
      @JsonProperty
      private List<String> anganwadiID;

      /** The role. */
      @JsonProperty
      private Role role;

      /** The work under. */
      @JsonProperty
      private Staff workUnder;

      /** The languages. */
      @JsonProperty
      private List<Language> languages;

      /** The highest educational level. */
      @JsonProperty
      private Education highestEducationalLevel;

      /** The alleducation. */
      @JsonIgnore
      private Education[] alleducation = Education.values();

      /** The annual salary. */
      @JsonProperty
      private double annualSalary;

      /** The image path of staff. */
      @JsonProperty
      private String imagePathOfStaff;

      /**
       * Gets the alleducation.
       * 
       * @return the alleducation
       */
      public Education[] getAlleducation() {
            return alleducation.clone();
      }

      /**
       * Sets the alleducation.
       * 
       * @param alleducation
       *              the new alleducation
       */
      public void setAlleducation(Education[] alleducation) {
            this.alleducation = alleducation.clone();
      }

      /**
       * Instantiates a new staff.
       */
      public Staff() {
            super();
      }

      /**
       * Gets the role.
       * 
       * @return the role
       */
      public Role getRole() {
            return role;
      }

      /**
       * Sets the role.
       * 
       * @param role
       *              the new role
       */
      public void setRole(Role role) {
            this.role = role;
      }

      /**
       * Gets the work under.
       * 
       * @return the work under
       */
      public Staff getWorkUnder() {
            return workUnder;
      }

      /**
       * Gets the highest educational level.
       * 
       * @return the highest educational level
       */
      public Education getHighestEducationalLevel() {
            return highestEducationalLevel;
      }

      /**
       * Sets the highest educational level.
       * 
       * @param highestEducationalLevel
       *              the new highest educational level
       */
      public void setHighestEducationalLevel(Education highestEducationalLevel) {
            this.highestEducationalLevel = highestEducationalLevel;
      }

      /**
       * Gets the annual salary.
       * 
       * @return the annual salary
       */
      public double getAnnualSalary() {
            return annualSalary;
      }

      /**
       * Sets the annual salary.
       * 
       * @param annualSalary2
       *              the new annual salary
       */
      public void setAnnualSalary(double annualSalary2) {
            this.annualSalary = annualSalary2;
      }

      /**
       * Gets the languages.
       * 
       * @return the languages
       */
      public List<Language> getLanguages() {
            return languages;
      }

      /**
       * Sets the languages.
       * 
       * @param languages
       *              the new languages
       */
      public void setLanguages(List<Language> languages) {
            this.languages = languages;
      }

      /**
       * Gets the employee id.
       * 
       * @return the employee id
       */
      public String getEmployeeID() {
            return employeeID;
      }

      /**
       * Sets the employee id.
       * 
       * @param employeeID
       *              the new employee id
       */
      public void setEmployeeID(String employeeID) {
            this.employeeID = employeeID;
      }

      /**
       * Gets the staff id.
       * 
       * @return the staff id
       */
      public String getStaffID() {
            return staffID;
      }

      /**
       * Sets the staff id.
       * 
       * @param staffID
       *              the new staff id
       */
      public void setStaffID(String staffID) {
            this.staffID = staffID;
      }

      /**
       * Gets the image path of staff.
       * 
       * @return the image path of staff
       */
      public String getImagePathOfStaff() {
            return imagePathOfStaff;
      }

      /**
       * Sets the image path of staff.
       * 
       * @param imagePathOfStaff
       *              the new image path of staff
       */
      public void setImagePathOfStaff(String imagePathOfStaff) {
            this.imagePathOfStaff = imagePathOfStaff;
      }

      /**
       * Gets the anganwadi id.
       * 
       * @return the anganwadi id
       */
      public List<String> getAnganwadiID() {
            return anganwadiID;
      }

      /**
       * Sets the anganwadi id.
       * 
       * @param anganwadiID
       *              the new anganwadi id
       */
      public void setAnganwadiID(List<String> anganwadiID) {
            this.anganwadiID = anganwadiID;
      }

}
