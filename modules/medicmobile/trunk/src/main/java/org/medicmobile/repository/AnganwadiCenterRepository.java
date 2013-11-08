package org.medicmobile.repository;

import java.util.List;
import org.medicmobile.model.AnganwadiCenter;

// : Auto-generated Javadoc
/**
 * The Interface AnganwadiCenterRepository.
 */
public interface AnganwadiCenterRepository {

      /**
       * Adds the anganwadi center.
       * 
       * @param anganwadiCenter
       *              the anganwadi center
       */
      void addAnganwadiCenter(AnganwadiCenter anganwadiCenter);

      /**
       * Gets the all.
       * 
       * @return the all
       */
      List<AnganwadiCenter> getAll();

      /**
       * Find by anganwadi id.
       * 
       * @param anganwadiID
       *              the anganwadi id
       * @return the list
       */
      List<AnganwadiCenter> findByAnganwadiID(String anganwadiID);

      /**
       * Delete anganwadi center.
       * 
       * @param anganwadiID
       *              the anganwadi id
       */
      void deleteAnganwadiCenter(String anganwadiID);

      /**
       * View anganwadi center.
       * 
       * @param anganwadiCenter
       *              the anganwadi center
       * @return the anganwadi center
       */
      AnganwadiCenter viewAnganwadiCenter(AnganwadiCenter anganwadiCenter);
}
