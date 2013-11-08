package org.medicmobile.service;

import java.util.Date;
import java.util.List;

import org.medicmobile.dto.CareGiverSearch;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.CareGiverView;

// : Auto-generated Javadoc
/**
 * The Interface CareGiverService.
 * 
 * @author Beehyv Software Solutions {@link CareGiverService} is a service
 *         provided for {@link CareGiver} to interact with
 *         {@link CareGiverRepositoryImpl} which inturn interacts with database
 */
public interface CareGiverService {

      /**
       * This method is used to add/update {@link CareGiver} to the database via.
       *
       * @param careGiver - {@link CareGiver} object which will added/updated in the
       * database
       * @return - {@link CareGiverView} object which is saved/updated to the
       * database here {@link CareGiverView} is similar
       * {@link CareGiverRepositoryImpl}.
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc
       */
      CareGiverView addCareGiver(CareGiver careGiver);

      /**
       * this method is used to update {@link CareGiver} to the database via.
       *
       * @param careGiver - {@link CareGiver} object which will updated in the
       * database
       * {@link CareGiverRepositoryImpl}s.
       */
      void update(CareGiver careGiver);

      /**
       * Find by care giver id.
       *
       * @param careGiverID - (system generated id)used to search for
       * @return - {@link CareGiverView} object which is saved/updated to the
       * database here {@link CareGiverView} is similar
       * {@link CareGiver} with caregiver id as constraint
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc
       */
      CareGiverView findByCareGiverID(String careGiverID);

      /**
       * Find by id card number.
       *
       * @param idCardNumber - (given by government)used to search for
       * @return - list of {@link CareGiverView} object which is saved/updated
       * to the database here {@link CareGiverView} is similar
       * {@link CareGiver} with id card number of caregiver as
       * constraint
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByIDCardNumber(String idCardNumber);

      /**
       * Find by care giver name.
       * 
       * @param careGiverName
       *              - used to search for {@link CareGiver}s with caregiver
       *              name as constraint
       * @return - list of {@link CareGiverView} object which is saved/updated
       *         to the database here {@link CareGiverView} is similar
       *         {@link CareGiver} but contains some extra information like
       *         local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByCareGiverName(String careGiverName);

      /**
       * This method is used to search for {@link CareGiver}s with village as
       * constraint.
       * 
       * @param village
       *              - used to search for {@link CareGiver} whose village is
       *              this
       * @return - list of {@link CareGiverView} object which is saved/updated
       *         to the database here {@link CareGiverView} is similar
       *         {@link CareGiver} but contains some extra information like
       *         local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByVillage(String village);

      /**
       * This method is used to search for {@link CareGiver}s with child date of
       * birth as constraint.
       * 
       * @param startDate
       *              - starting date of birth date range
       * @param endDate
       *              - ending date of birth date range
       * @return - list of {@link CareGiverView} object which is saved/updated
       *         to the database here {@link CareGiverView} is similar
       *         {@link CareGiver} but contains some extra information like
       *         local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByChildBirthDate(Date startDate, Date endDate);

      /**
       * This method is used to search {@link CareGiver}s with child name as
       * constraint.
       * 
       * @param childName
       *              - used to search for {@link CareGiver} whose child name is
       *              this
       * @return - list of {@link CareGiverView} object which is saved/updated
       *         to the database here {@link CareGiverView} is similar
       *         {@link CareGiver} but contains some extra information like
       *         local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByChildName(String childName);

      /**
       * This method us used to search for {@link CareGiver}s with multiple
       * constraints like name, village, etc.
       *
       * @param careGiverSearch - {@link CareGiverSearch} object used to search for
       * @return -list of {@link CareGiverView} object which is saved/updated to
       * the database here {@link CareGiverView} is similar
       * {@link CareGiver} with this value as constraint
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc In this
       * method once we get list of {@link CareGiver} from
       * {@link CareGiverRepositoryImpl} with key as value having hight
       * ranking We further refine the results in this method with
       * properties having lower ranking
       */
      List<CareGiverView> findByAllConstraints(CareGiverSearch careGiverSearch);

      /**
       * This method is used to search for {@link CareGiver}s with.
       *
       * @param anganwadiID - used to search for {@link CareGiver} with this as his
       * @return - list of {@link CareGiverView} object which is saved/updated
       * to the database here {@link CareGiverView} is similar
       * {@link AnganwadiCenter}'s id as constraint.
       * {@link AnganwadiCenter}'s id
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByAnganwadiID(String anganwadiID);

      /**
       * This method is used to search for {@link CareGiver}s with {@link Staff}
       * (local volunteer)'s id as constraint.
       *
       * @param localVolunteerID - used to search for {@link CareGiver} with this as his
       * @return - list of {@link CareGiverView} object which is saved/updated
       * to the database here {@link CareGiverView} is similar
       * {@link Staff}(LocalVolunteer)'s id
       * {@link CareGiver} but contains some extra information like
       * local volunteer name instead of local volunteer id, etc
       */
      List<CareGiverView> findByLocalVolunteerID(String localVolunteerID);

      /**
       * Delete care giver.
       *
       * @param careGiverID the care giver id
       */
      void deleteCareGiver(String careGiverID);

      /**
       * Gets the by care giver id.
       *
       * @param careGiverID the care giver id
       * @return the by care giver id
       */
      CareGiver getByCareGiverID(String careGiverID);

}
