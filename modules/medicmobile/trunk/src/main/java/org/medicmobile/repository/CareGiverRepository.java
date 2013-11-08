package org.medicmobile.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.medicmobile.dto.CareGiverSearch;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;

// : Auto-generated Javadoc

/**
 * The Interface CareGiverRepository.
 * 
 * @author Beehyv Software Solutions This class acts as the DAO layer for the
 *         CareGiver Object. It is used to add/update/search caregiver objects.
 *         It receives objects from service layer
 */
public interface CareGiverRepository {

      /**
       * It is is used to add/update {@link CareGiver} object.
       * 
       * @param careGiver
       *              - CareGiver object
       * @return - returns {@link CareGiver} which is saved to the database
       */
      CareGiver addCareGiver(CareGiver careGiver);

      /**
       * It is returns all the {@link CareGiver} objects in the database.
       * 
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> getAll();

      /**
       * It is is used to search the database for the {@link CareGiver} objects
       * with caregiver name as constraint.
       * 
       * @param careGiverName
       *              -used to find {@link CareGiver} whose name equals to this
       *              value
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByName(String careGiverName);

      /**
       * It is is used to search the database for the {@link CareGiver} objects
       * with id card number as constraint.
       * 
       * @param idCardNumber
       *              - used to find {@link CareGiver} whose id card number
       *              equals to this value
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByIDCardNumber(String idCardNumber);

      /**
       * It is is used to search the database for the {@link CareGiver} objects
       * with village as constraint.
       * 
       * @param village
       *              - used to find {@link CareGiver} whose village equals to
       *              this value
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByVillage(String village);

      /**
       * It is is used to search the database for the {@link CareGiver} objects
       * with child name as constraint.
       * 
       * @param childName
       *              - child name of the {@link CareGiver} to search for
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByChildName(String childName);

      /**
       * It is is used to search the database for the {@link CareGiver} objects
       * with children date of birth as constraint startDate and endDate.
       * 
       * @param startDate
       *              - startDate of the child birth date
       * @param endDate
       *              - end date of the child birth date
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByChildBirthDate(Date startDate, Date endDate);

      /**
       * It is used to search the database for the {@link CareGiver} objects.
       * 
       * @param careGiverID
       *              - this is used to search for {@link CareGiver} who has the
       *              careGiverID equal to this
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByCareGiverIDS(String careGiverID);

      /**
       * It is used to search the database for the {@link CareGiver} objects
       * with caregiver id as constraint.
       * 
       * @param careGiverIDS
       *              - used to search {@link CareGiver}s who has the
       *              careGiverID equal to these values
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByCareGiverIDS(List<String> careGiverIDS);

      /**
       * This method is used to add child to caregiver, here child contains id
       * of the caregiver. We will search for the caregiver id in the database
       * which will give caregiver object, then we add child to the caregiver
       * object
       * 
       * @param child
       *              - {@link Child} object to be added to {@link CareGiver}
       *              Object
       */
      void addChild(Child child);

      /**
       * This method updates the {@link CareGiver} object.
       * 
       * @param careGiver
       *              - {@link CareGiver} object to be updated
       */
      void update(CareGiver careGiver);

      /**
       * This method is used to search for {@link CareGiver} objects which fall
       * into all the constraints mentioned in the {@link CareGiverSearch}
       * object Here due to the limitation of the couchdb in searching wild
       * cards, we used searching based on ranking. The property which has high
       * rank is one which gives minimum results. Then results are further
       * refined from other properties in {@link CareGiverSearch} object. This
       * is done inside java not using couchdb. Couchdb is used to get only to
       * get intial set Example: We have one view which have {key1, key2, key3}
       * in that order I cannot search for {key1, *, key3}
       * 
       * @param careGiverSearch
       *              - {@link CareGiverSearch} object - contains all the
       *              constraints to search for
       * @return - Map which contains string as key (on which search is based
       *         on) and list of {@link CareGiver} objects as the value Here we
       *         are passing even the property on which search is done from
       *         couchdb, so that further refining can be done on the properties
       *         which are having less rank
       */
      Map<String, List<CareGiver>> findByAllConstraints(
                  CareGiverSearch careGiverSearch);

      /**
       * This method is used to search for {@link CareGiver}s with
       * {@link AnganwadiCenter} id as the constraint.
       * 
       * @param anganwadiID
       *              - used to search {@link CareGiver} whose
       *              {@link AnganwadiCenter} id equals to this value
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByAnganwadiCenterID(String anganwadiID);

      /**
       * This method is used to search for caregivers which are under
       * {@link Staff} (localvolunteer).
       * 
       * @param localVolunteerID
       *              - used to search {@link CareGiver} whose {@link Staff}
       *              (localvolunteer) id equals to this value
       * @return - list of {@link CareGiver} objects
       */
      List<CareGiver> findByLocalVolunteerID(String localVolunteerID);

      void deleteCareGiver(String careGiverID);
}
