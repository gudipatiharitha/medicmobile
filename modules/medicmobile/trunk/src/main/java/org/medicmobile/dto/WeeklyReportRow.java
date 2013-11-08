package org.medicmobile.dto;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

// : Auto-generated Javadoc
/**
 * The Class WeeklyReportRow.
 */
public final class WeeklyReportRow {

      /** The child name. */
      private String childName;

      private String childNameInHindi;

      /** The child id. */
      private String childID;

      /** The care giver name. */
      private String careGiverName;

      private String careGiverNameInHindi;

      /** The care giver id. */
      private String careGiverID;

      /** The vaccine name. */
      private String vaccineName;

      /** The vaccine id. */
      private String vaccineID;

      /** The due date string. */
      private String dueDateString;

      /** The administered date string. */
      private String administeredDateString;

      /** The due date. */
      private DateTime dueDate;

      /** The administered date. */
      private DateTime administeredDate;

      /** The vaccine taken. */
      private boolean vaccineTaken;

      /** The comments. */
      private String comments;

      /** The sms code. */
      private String smsCode;

      /** The updated by. */
      private String updatedBy;

      private boolean missed;

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
            this.childName = childName;
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
      public void setChildID(final String childID) {
            this.childID = childID;
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
            this.careGiverName = careGiverName;
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
            this.careGiverID = careGiverID;
      }

      /**
       * Gets the vaccine name.
       * 
       * @return the vaccine name
       */
      public String getVaccineName() {
            return vaccineName;
      }

      /**
       * Sets the vaccine name.
       * 
       * @param vaccineName
       *              the new vaccine name
       */
      public void setVaccineName(final String vaccineName) {
            this.vaccineName = vaccineName;
      }

      /**
       * Gets the vaccine id.
       * 
       * @return the vaccine id
       */
      public String getVaccineID() {
            return vaccineID;
      }

      /**
       * Sets the vaccine id.
       * 
       * @param vaccineID
       *              the new vaccine id
       */
      public void setVaccineID(final String vaccineID) {
            this.vaccineID = vaccineID;
      }

      /**
       * Gets the due date.
       * 
       * @return the due date
       */
      public DateTime getDueDate() {
            return dueDate;
      }

      /**
       * Sets the due date.
       * 
       * @param dueDate
       *              the new due date
       */
      public void setDueDate(final DateTime dueDate) {
            this.dueDate = dueDate;
            if (dueDate != null) {
                  this.setDueDateString(dueDate.toString(DateTimeFormat
                              .forPattern("dd MMM yyyy")));
            }
      }

      /**
       * Gets the administered date.
       * 
       * @return the administered date
       */
      public DateTime getAdministeredDate() {
            return administeredDate;
      }

      /**
       * Sets the administered date.
       * 
       * @param administeredDate
       *              the new administered date
       */
      public void setAdministeredDate(final DateTime administeredDate) {
            this.administeredDate = administeredDate;
            if (administeredDate != null) {
                  this.setAdministeredDateString(administeredDate
                              .toString(DateTimeFormat
                                          .forPattern("dd MMM yyyy")));
            }
      }

      /**
       * Checks if is vaccine taken.
       * 
       * @return true, if is vaccine taken
       */
      public boolean isVaccineTaken() {
            return vaccineTaken;
      }

      /**
       * Sets the vaccine taken.
       * 
       * @param vaccineTaken
       *              the new vaccine taken
       */
      public void setVaccineTaken(final boolean vaccineTaken) {
            this.vaccineTaken = vaccineTaken;
      }

      /**
       * Gets the comments.
       * 
       * @return the comments
       */
      public String getComments() {
            return comments;
      }

      /**
       * Sets the comments.
       * 
       * @param comments
       *              the new comments
       */
      public void setComments(final String comments) {
            this.comments = comments;
      }

      /**
       * Gets the due date string.
       * 
       * @return the due date string
       */
      public String getDueDateString() {
            return dueDateString;
      }

      /**
       * Sets the due date string.
       * 
       * @param dueDateString
       *              the new due date string
       */
      public void setDueDateString(final String dueDateString) {
            this.dueDateString = dueDateString;
      }

      /**
       * Gets the administered date string.
       * 
       * @return the administered date string
       */
      public String getAdministeredDateString() {
            return administeredDateString;
      }

      /**
       * Sets the administered date string.
       * 
       * @param administeredDateString
       *              the new administered date string
       */
      public void setAdministeredDateString(final String administeredDateString) {
            this.administeredDateString = administeredDateString;
      }

      /**
       * Gets the sms code.
       * 
       * @return the sms code
       */
      public String getSmsCode() {
            return smsCode;
      }

      /**
       * Sets the sms code.
       * 
       * @param smsCode
       *              the new sms code
       */
      public void setSmsCode(final String smsCode) {
            this.smsCode = smsCode;
      }

      /**
       * Gets the updated by.
       * 
       * @return the updated by
       */
      public String getUpdatedBy() {
            return updatedBy;
      }

      /**
       * Sets the updated by.
       * 
       * @param updatedBy
       *              the new updated by
       */
      public void setUpdatedBy(final String updatedBy) {
            this.updatedBy = updatedBy;
      }

      public String getChildNameInHindi() {
            return childNameInHindi;
      }

      public void setChildNameInHindi(String childNameInHindi) {
            this.childNameInHindi = childNameInHindi;
      }

      public String getCareGiverNameInHindi() {
            return careGiverNameInHindi;
      }

      public void setCareGiverNameInHindi(String careGiverNameInHindi) {
            this.careGiverNameInHindi = careGiverNameInHindi;
      }

      public boolean isMissed() {
            return missed;
      }

      public void setMissed(boolean missed) {
            this.missed = missed;
      }
}