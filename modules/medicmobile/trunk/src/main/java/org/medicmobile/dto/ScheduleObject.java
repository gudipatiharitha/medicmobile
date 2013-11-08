package org.medicmobile.dto;

import java.util.Date;

// : Auto-generated Javadoc
/**
 * The Class ScheduleObject.
 */
public final class ScheduleObject {

      /** The name. */
      private String name;

      /** The missed. */
      private boolean missed;

      /** The original appointment due date. */
      private Date originalAppointmentDueDate;

      /** The appointment confirm date. */
      private Date appointmentConfirmDate;

      /** The visit date. */
      private Date visitDate;

      private int order;

      private boolean isBeforeCompulsory;

      private int gapInWeeks;

      /**
       * Gets the visit date.
       * 
       * @return the visit date
       */
      public Date getVisitDate() {
            return visitDate;
      }

      /**
       * Sets the visit date.
       * 
       * @param visitDate
       *              the new visit date
       */
      public void setVisitDate(final Date visitDate) {
            this.visitDate = visitDate;
      }

      /**
       * Gets the name.
       * 
       * @return the name
       */
      public String getName() {
            return name;
      }

      /**
       * Sets the name.
       * 
       * @param name
       *              the new name
       */
      public void setName(final String name) {
            this.name = name;
      }

      /**
       * Gets the original appointment due date.
       * 
       * @return the original appointment due date
       */
      public Date getOriginalAppointmentDueDate() {
            return originalAppointmentDueDate;
      }

      /**
       * Sets the original appointment due date.
       * 
       * @param originalAppointmentDueDate
       *              the new original appointment due date
       */
      public void setOriginalAppointmentDueDate(
                  final Date originalAppointmentDueDate) {
            this.originalAppointmentDueDate = originalAppointmentDueDate;
      }

      /**
       * Gets the appointment confirm date.
       * 
       * @return the appointment confirm date
       */
      public Date getAppointmentConfirmDate() {
            return appointmentConfirmDate;
      }

      /**
       * Sets the appointment confirm date.
       * 
       * @param appointmentConfirmDate
       *              the new appointment confirm date
       */
      public void setAppointmentConfirmDate(final Date appointmentConfirmDate) {
            this.appointmentConfirmDate = appointmentConfirmDate;
      }

      /**
       * Checks if is missed.
       * 
       * @return true, if is missed
       */
      public boolean isMissed() {
            return missed;
      }

      /**
       * Sets the missed.
       * 
       * @param missed
       *              the new missed
       */
      public void setMissed(final boolean missed) {
            this.missed = missed;
      }

      /**
       * gets the order of Vaccine for UI
       * 
       * @return the order
       */
      public int getOrder() {
            return order;
      }

      /**
       * 
       * sets the order of Vaccine for UI
       * 
       * @param order
       *              the order to set
       */
      public void setOrder(int order) {
            this.order = order;
      }

      public boolean getIsBeforeCompulsory() {
            return isBeforeCompulsory;
      }

      public void setIsBeforeCompulsory(boolean isBeforeCompulsory) {
            this.isBeforeCompulsory = isBeforeCompulsory;
      }

      public int getGapInWeeks() {
            return gapInWeeks;
      }

      public void setGapInWeeks(int gapInWeeks) {
            this.gapInWeeks = gapInWeeks;
      }

}
