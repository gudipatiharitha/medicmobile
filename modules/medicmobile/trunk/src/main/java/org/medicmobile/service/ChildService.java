package org.medicmobile.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medicmobile.model.Child;
import org.medicmobile.model.ChildView;

// : Auto-generated Javadoc
/**
 * The Interface ChildService.
 */
public interface ChildService {

      /**
       * Adds the child.
       * 
       * @param child
       *              the child
       * @return the child
       */
      Child addChild(Child child);

      Child getChildByID(String childID);

      /**
       * Find by child id.
       * 
       * @param childID
       *              the child id
       * @return the list
       */
      List<ChildView> findByChildID(String childID);

      /**
       * Find by child name.
       * 
       * @param childName
       *              the child name
       * @return the list
       */
      List<ChildView> findByChildName(String childName);

      /**
       * Find by care giver name.
       * 
       * @param careGiverName
       *              the care giver name
       * @return the list
       */
      List<ChildView> findByCareGiverName(String careGiverName);

      /**
       * Find by date of birth.
       * 
       * @param dateOfBirth
       *              the date of birth
       * @return the list
       */
      List<ChildView> findByDateOfBirth(Date dateOfBirth);

      /**
       * Find by date of birth range.
       * 
       * @param startDate
       *              the start date
       * @param endDate
       *              the end date
       * @return the list
       */
      List<ChildView> findByDateOfBirthRange(Date startDate, Date endDate);

      /**
       * Find by all constraints.
       * 
       * @param constraints
       *              the constraints
       * @return the list
       */
      List<ChildView> findByAllConstraints(Map<String, Object> constraints);

      /**
       * Delete child.
       * 
       * @param child
       *              the child
       */
      List<Child> findByCareGiverID(String careGiverID);

      List<Child> findByChildSystemId();

      void deleteChild(Child child);

      void deleteChild(String childID);
}
