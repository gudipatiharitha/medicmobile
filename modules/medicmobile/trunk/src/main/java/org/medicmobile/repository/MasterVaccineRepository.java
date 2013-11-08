package org.medicmobile.repository;

import java.util.List;
import org.medicmobile.model.MasterVaccine;

/**
 * The Interface MasterVaccineRepository.
 */
public interface MasterVaccineRepository {

      /**
       * Gets the all.
       * 
       * @return the all
       */
      List<MasterVaccine> getAll();

      /**
       * Find by vaccine name.
       * 
       * @param vaccineName
       *              the vaccine name
       * @return the list
       */
      List<MasterVaccine> findByVaccineName(String vaccineName);

}
