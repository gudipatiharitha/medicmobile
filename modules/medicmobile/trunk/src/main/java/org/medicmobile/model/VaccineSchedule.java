package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;

// : Auto-generated Javadoc
/**
 * The Class VaccineSchedule.
 */
public class VaccineSchedule {

      /** The vaccine id. */
      @JsonProperty
      private String vaccineID;

      /** The start week. */
      @JsonProperty
      private int startDay;

      /** The end week. */
      @JsonProperty
      private int endDay;

      /** The gap in weeks. */
      @JsonProperty
      private int gapInWeeks;

      /** The vaccine before criteria. */
      @JsonProperty
      private boolean vaccineBeforeCriteria = false;

      /** The vaccines taken before. */
      @JsonProperty
      private int vaccinesTakenBefore;

      /** The vaccine taken before time in weeks. */
      @JsonProperty
      private int vaccineTakenBeforeTimeInWeeks;

      /** The is applicable after end week. */
      @JsonProperty
      private Boolean isApplicableAfterEndDay;

      @JsonProperty
      private Boolean isBeforeCompulsory;

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
      public void setVaccineID(String vaccineID) {
            this.vaccineID = vaccineID;
      }

      /**
       * Gets the gap in weeks.
       * 
       * @return the gap in weeks
       */
      public int getGapInWeeks() {
            return gapInWeeks;
      }

      /**
       * Sets the gap in weeks.
       * 
       * @param gapInWeeks
       *              the new gap in weeks
       */
      public void setGapInWeeks(int gapInWeeks) {
            this.gapInWeeks = gapInWeeks;
      }

      /**
       * Checks if is vaccine before criteria.
       * 
       * @return true, if is vaccine before criteria
       */
      public boolean isVaccineBeforeCriteria() {
            return vaccineBeforeCriteria;
      }

      /**
       * Sets the vaccine before criteria.
       * 
       * @param vaccineBeforeCriteria
       *              the new vaccine before criteria
       */
      public void setVaccineBeforeCriteria(boolean vaccineBeforeCriteria) {
            this.vaccineBeforeCriteria = vaccineBeforeCriteria;
      }

      /**
       * Gets the vaccines taken before.
       * 
       * @return the vaccines taken before
       */
      public int getVaccinesTakenBefore() {
            return vaccinesTakenBefore;
      }

      /**
       * Sets the vaccines taken before.
       * 
       * @param vaccinesTakenBefore
       *              the new vaccines taken before
       */
      public void setVaccinesTakenBefore(int vaccinesTakenBefore) {
            this.vaccinesTakenBefore = vaccinesTakenBefore;
      }

      /**
       * Gets the vaccine taken before time in weeks.
       * 
       * @return the vaccine taken before time in weeks
       */
      public int getVaccineTakenBeforeTimeInWeeks() {
            return vaccineTakenBeforeTimeInWeeks;
      }

      /**
       * Sets the vaccine taken before time in weeks.
       * 
       * @param vaccineTakenBeforeTimeInWeeks
       *              the new vaccine taken before time in weeks
       */
      public void setVaccineTakenBeforeTimeInWeeks(
                  int vaccineTakenBeforeTimeInWeeks) {
            this.vaccineTakenBeforeTimeInWeeks = vaccineTakenBeforeTimeInWeeks;
      }

      /**
       * Gets the start week.
       * 
       * @return the start week
       */
      public int getStartDay() {
            return startDay;
      }

      /**
       * Sets the start week.
       * 
       * @param startWeek
       *              the new start week
       */
      public void setStartDay(int startDay) {
            this.startDay = startDay;
      }

      /**
       * Gets the end week.
       * 
       * @return the end week
       */
      public int getEndDay() {
            return endDay;
      }

      /**
       * Sets the end week.
       * 
       * @param endWeek
       *              the new end week
       */
      public void setEndDay(int endDay) {
            this.endDay = endDay;
      }

      /**
       * Gets the checks if is applicable after end week.
       * 
       * @return the checks if is applicable after end week
       */
      public Boolean getIsApplicableAfterEndDay() {
            return isApplicableAfterEndDay;
      }

      /**
       * Sets the checks if is applicable after end week.
       * 
       * @param isApplicableAfterEndWeek
       *              the new checks if is applicable after end week
       */
      public void setIsApplicableAfterEndWeek(Boolean isApplicableAfterEndDay) {
            this.isApplicableAfterEndDay = isApplicableAfterEndDay;
      }

      /**
       * Gets the vaccine name.
       * 
       * @return the vaccine name
       */
      public String getVaccineName() {
            return vaccineID;
      }

      /**
       * Sets the vaccine name.
       * 
       * @param vaccineName
       *              the new vaccine name
       */
      public void setVaccineName(String vaccineName) {
            this.vaccineID = vaccineName;
      }

      /**
       * @return the isBeforeCompulsory
       */
      public Boolean getIsBeforeCompulsory() {
            return isBeforeCompulsory;
      }

      /**
       * @param isBeforeCompulsory
       *              the isBeforeCompulsory to set
       */
      public void setIsBeforeCompulsory(Boolean isBeforeCompulsory) {
            this.isBeforeCompulsory = isBeforeCompulsory;
      }
}
