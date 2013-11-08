package org.medicmobile.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.medicmobile.model.Child;
import org.medicmobile.model.ChildVaccine;

// : Auto-generated Javadoc
/**
 * The Interface ChildRepository.
 */
public interface ChildRepository {

      /**
       * Adds the child.
       * 
       * @param child
       *              the child
       */
      void addChild(Child child);

      /**
       * Find by child id.
       * 
       * @param childID
       *              the child id
       * @return the list
       */
      List<Child> findByChildID(String childID);

      /**
       * Find by name.
       * 
       * @param childName
       *              the child name
       * @return the list
       */
      List<Child> findByName(String childName);

      /**
       * Update.
       * 
       * @param child
       *              the child
       */
      void update(Child child);

      /**
       * Find by date of birth.
       * 
       * @param dateOfBirth
       *              the date of birth
       * @return the list
       */
      List<Child> findByDateOfBirth(Date dateOfBirth);

      /**
       * Adds the child vaccine.
       * 
       * @param childVaccine
       *              the child vaccine
       */
      void addChildVaccine(ChildVaccine childVaccine);

      /**
       * Find by date of birth range.
       * 
       * @param startDate
       *              the start date
       * @param endDate
       *              the end date
       * @return the list
       */
      List<Child> findByDateOfBirthRange(Date startDate, Date endDate);

      /**
       * Find by care giver id.
       * 
       * @param careGiverID
       *              the care giver id
       * @return the list
       */
      List<Child> findByCareGiverID(String careGiverID);

      /**
       * Find by all constraints.
       * 
       * @param constraints
       *              the constraints
       * @return the list
       */
      List<Child> findByAllConstraints(Map<String, Object> constraints);

      /**
       * Find by care giver name.
       * 
       * @param careGiverName
       *              the care giver name
       * @return the list
       */
      List<Child> findByCareGiverName(String careGiverName);

      List<Child> findByChildSystemId();

      void deleteChild(String childID);

}
