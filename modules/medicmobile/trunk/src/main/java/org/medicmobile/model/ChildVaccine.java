package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.LocalDate;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class ChildVaccine.
 */
@TypeDiscriminator("doc.type == 'ChildVaccine'")
public class ChildVaccine extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = -3861094532670176176L;

      /** The child vaccine id. */
      @JsonProperty
      private String childVaccineID;

      /** The dosage. */
      @JsonProperty
      private int dosage;

      /** The vaccine id. */
      @JsonProperty
      private String vaccineID;

      /** The child id. */
      @JsonProperty
      private String childID;

      /** The start date. */
      @JsonProperty
      private LocalDate startDate;

      /** The end date. */
      @JsonProperty
      private LocalDate endDate;

      /** The administered date. */
      @JsonProperty
      private LocalDate administeredDate;

      /** The batch no. */
      @JsonProperty
      private String batchNo;

      /** The manufacturer. */
      @JsonProperty
      private String manufacturer;

      /** The is emergency. */
      @JsonProperty
      private Boolean isEmergency;

      /**
       * Instantiates a new child vaccine.
       * 
       * @param childDateOfBirth
       *              the child date of birth
       * @param startWeek
       *              the start week
       * @param endWeek
       *              the end week
       */
      public ChildVaccine(LocalDate childDateOfBirth, int startWeek, int endWeek) {
            this.startDate = childDateOfBirth.plusWeeks(startWeek);
            this.endDate = childDateOfBirth.plusWeeks(endWeek);
      }

      /**
       * Gets the batch no.
       * 
       * @return the batch no
       */
      public String getBatchNo() {
            return batchNo;
      }

      /**
       * Sets the batch no.
       * 
       * @param batchNo
       *              the new batch no
       */
      public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
      }

      /**
       * Gets the manufacturer.
       * 
       * @return the manufacturer
       */
      public String getManufacturer() {
            return manufacturer;
      }

      /**
       * Sets the manufacturer.
       * 
       * @param manufacturer
       *              the new manufacturer
       */
      public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
      }

      /**
       * Gets the vaccine schedule id.
       * 
       * @return the vaccine schedule id
       */
      public String getVaccineScheduleID() {
            return vaccineID;
      }

      /**
       * Sets the vaccine schedule id.
       * 
       * @param vaccineScheduleID
       *              the new vaccine schedule id
       */
      public void setVaccineScheduleID(String vaccineScheduleID) {
            this.vaccineID = vaccineScheduleID;
      }

      /**
       * Gets the start date.
       * 
       * @return the start date
       */
      public LocalDate getStartDate() {
            return startDate;
      }

      /**
       * Sets the start date.
       * 
       * @param startDate
       *              the new start date
       */
      public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
      }

      /**
       * Gets the end date.
       * 
       * @return the end date
       */
      public LocalDate getEndDate() {
            return endDate;
      }

      /**
       * Sets the end date.
       * 
       * @param endDate
       *              the new end date
       */
      public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
      }

      /**
       * Gets the administered date.
       * 
       * @return the administered date
       */
      public LocalDate getAdministeredDate() {
            return administeredDate;
      }

      /**
       * Sets the administered date.
       * 
       * @param administeredDate
       *              the new administered date
       */
      public void setAdministeredDate(LocalDate administeredDate) {
            this.administeredDate = administeredDate;
      }

      /**
       * Gets the checks if is emergency.
       * 
       * @return the checks if is emergency
       */
      public Boolean getIsEmergency() {
            return isEmergency;
      }

      /**
       * Sets the checks if is emergency.
       * 
       * @param isEmergency
       *              the new checks if is emergency
       */
      public void setIsEmergency(Boolean isEmergency) {
            this.isEmergency = isEmergency;
      }

      /**
       * Gets the child id.
       * 
       * @return the child id
       */
      public String getChildID() {
            return childID;
      }

      /**
       * Sets the child id.
       * 
       * @param childID
       *              the new child id
       */
      public void setChildID(String childID) {
            this.childID = childID;
      }

      /**
       * Gets the child vaccine id.
       * 
       * @return the child vaccine id
       */
      public String getChildVaccineID() {
            return childVaccineID;
      }

      /**
       * Sets the child vaccine id.
       * 
       * @param childVaccineID
       *              the new child vaccine id
       */
      public void setChildVaccineID(String childVaccineID) {
            this.childVaccineID = childVaccineID;
      }

      /**
       * Gets the dosage.
       * 
       * @return the dosage
       */
      public int getDosage() {
            return dosage;
      }

      /**
       * Sets the dosage.
       * 
       * @param dosage
       *              the new dosage
       */
      public void setDosage(int dosage) {
            this.dosage = dosage;
      }

}
