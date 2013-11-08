package org.medicmobile.service;

import java.util.List;

import org.medicmobile.model.AnganwadiCenter;
import org.medicmobile.model.AnganwadiCenterView;

// : Auto-generated Javadoc
/**
 * Interface providing APIs to add, edit and search Anganwadi Centers.
 * 
 * @author AKumar
 */
public interface AnganwadiCenterService {

      /**
       * Adds the anganwadi center.
       * 
       * @param anganwadiCenter
       *              the anganwadi center
       * @return the anganwadi center
       */
      AnganwadiCenter addAnganwadiCenter(AnganwadiCenter anganwadiCenter);

      /**
       * Find all anganwadi center.
       * 
       * @return the list
       */
      List<AnganwadiCenterView> findAllAnganwadiCenter();

      /**
       * Find anganwadi center by id.
       * 
       * @param anganwadiID
       *              the anganwadi id
       * @return the anganwadi center
       */
      AnganwadiCenter findAnganwadiCenterByID(String anganwadiID);

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
       * @param anganwadiCenterSent
       *              the anganwadi center sent
       * @return the anganwadi center
       */
      AnganwadiCenter viewAnganwadiCenter(AnganwadiCenter anganwadiCenterSent);

}
