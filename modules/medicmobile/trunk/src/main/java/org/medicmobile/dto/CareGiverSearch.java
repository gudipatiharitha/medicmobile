package org.medicmobile.dto;

import java.util.Date;

/**
 * The Class CareGiverSearch.
 */
public final class CareGiverSearch {

      /** The anganwadi id. */
      private String anganwadiID;

      /** The care giver name. */
      private String careGiverName;

      /** The child name. */
      private String childName;

      /** The end date. */
      private Date endDate;

      /** The id card number. */
      private String idCardNumber;

      /** The local volunteer id. */
      private String localVolunteerID;

      /** The start date. */
      private Date startDate;

      /** The village. */
      private String village;

      /** The care giver id. */
      private String careGiverID;

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
            this.anganwadiID = anganwadiID;
      }

      /**
       * Gets the care giver name.
       * 
       * @return the care giver name
       */
      public String getCareGiverName() {
            return careGiverName;
      }

      /**
       * Sets the care giver name.
       * 
       * @param careGiverName
       *              the new care giver name
       */
      public void setCareGiverName(final String careGiverName) {
            this.careGiverName = careGiverName.toLowerCase();
      }

      /**
       * Gets the child name.
       * 
       * @return the child name
       */
      public String getChildName() {
            return childName;
      }

      /**
       * Sets the child name.
       * 
       * @param childName
       *              the new child name
       */
      public void setChildName(final String childName) {
            this.childName = childName.toLowerCase();
      }

      /**
       * Gets the end date.
       * 
       * @return the end date
       */
      public Date getEndDate() {
            return endDate;
      }

      /**
       * Sets the end date.
       * 
       * @param endDate
       *              the new end date
       */
      public void setEndDate(final Date endDate) {
            this.endDate = endDate;
      }

      /**
       * Gets the id card number.
       * 
       * @return the id card number
       */
      public String getIdCardNumber() {
            return idCardNumber;
      }

      /**
       * Sets the id card number.
       * 
       * @param idCardNumber
       *              the new id card number
       */
      public void setIdCardNumber(final String idCardNumber) {
            this.idCardNumber = idCardNumber.toLowerCase();
      }

      /**
       * Gets the local volunteer id.
       * 
       * @return the local volunteer id
       */
      public String getLocalVolunteerID() {
            return localVolunteerID;
      }

      /**
       * Sets the local volunteer id.
       * 
       * @param localVolunteerID
       *              the new local volunteer id
       */
      public void setLocalVolunteerID(final String localVolunteerID) {
            this.localVolunteerID = localVolunteerID.toLowerCase();
      }

      /**
       * Gets the start date.
       * 
       * @return the start date
       */
      public Date getStartDate() {
            return startDate;
      }

      /**
       * Sets the start date.
       * 
       * @param startDate
       *              the new start date
       */
      public void setStartDate(final Date startDate) {
            this.startDate = startDate;
      }

      /**
       * Gets the village.
       * 
       * @return the village
       */
      public String getVillage() {
            return village;
      }

      /**
       * Sets the village.
       * 
       * @param village
       *              the new village
       */
      public void setVillage(final String village) {
            this.village = village.toLowerCase();
      }

      /**
       * Gets the care giver id.
       * 
       * @return the care giver id
       */
      public String getCareGiverID() {
            return careGiverID;
      }

      /**
       * Sets the care giver id.
       * 
       * @param careGiverID
       *              the new care giver id
       */
      public void setCareGiverID(final String careGiverID) {
            this.careGiverID = careGiverID.toLowerCase();
      }

      /*
       * (non-Javadoc)
       * 
       * @see java.lang.Object#toString()
       */
      @Override
      public String toString() {
            return "CareGiverName: " + careGiverName + " AnganwadiID: "
                        + anganwadiID + " ChildName: " + childName
                        + " StartDate: " + startDate + " EndDate: " + endDate
                        + " IDCardNumber: " + idCardNumber
                        + " LocalVolunteerID: " + localVolunteerID
                        + " Village " + village + " CareGiverID: "
                        + careGiverID;
      }
}
