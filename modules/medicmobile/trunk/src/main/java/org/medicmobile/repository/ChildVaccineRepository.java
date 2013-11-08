package org.medicmobile.repository;

import java.util.Date;
import java.util.List;

import org.medicmobile.model.ChildVaccine;
import org.medicmobile.model.VaccineSchedule;

// : Auto-generated Javadoc
/**
 * The Interface ChildVaccineRepository.
 */
public interface ChildVaccineRepository {

      /**
       * Adds the child vaccine.
       * 
       * @param childVaccine
       *              the child vaccine
       */
      void addChildVaccine(ChildVaccine childVaccine);

      /**
       * Find by child id.
       * 
       * @param childID
       *              the child id
       * @return the list
       */
      List<ChildVaccine> findByChildID(String childID);

      /**
       * Find by due date range.
       * 
       * @param dueStartDate
       *              the due start date
       * @param dueEndDate
       *              the due end date
       * @return the list
       */
      List<ChildVaccine> findByDueDateRange(Date dueStartDate, Date dueEndDate);

      /**
       * Update.
       * 
       * @param childVaccine
       *              the child vaccine
       */
      void update(ChildVaccine childVaccine);

      /**
       * Adds the vaccine schedule.
       * 
       * @param vaccineSchedule
       *              the vaccine schedule
       */
      void addVaccineSchedule(VaccineSchedule vaccineSchedule);

      /**
       * Find by administered date.
       * 
       * @param administeredDate
       *              the administered date
       * @return the list
       */
      List<ChildVaccine> findByAdministeredDate(Date administeredDate);

      /**
       * Find by child vaccine id.
       * 
       * @param childVaccineID
       *              the child vaccine id
       * @return the list
       */
      List<ChildVaccine> findByChildVaccineID(String childVaccineID);

      /**
       * Find by due start date.
       * 
       * @param dueStartDate
       *              the due start date
       * @return the list
       */
      List<ChildVaccine> findByDueStartDate(Date dueStartDate);

      /**
       * Find by due end date.
       * 
       * @param dueEndDate
       *              the due end date
       * @return the list
       */
      List<ChildVaccine> findByDueEndDate(Date dueEndDate);

}
