package org.medicmobile.service;

import java.util.List;

import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;

// : Auto-generated Javadoc
/**
 * The Interface VaccineService.
 */
public interface VaccineService {

      /**
       * Creates the appointment schedule.
       * 
       * @param childList
       *              the child list
       */
      void createAppointmentSchedule(List<Child> childList);

      void changeVolunteerDetailsForChildSchedule(CareGiver careGiver,
                  String localVolunteerID, String angawadiID);

}
