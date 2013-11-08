package org.medicmobile.repository;

import java.util.List;
import org.motechproject.appointments.api.model.Appointment;
import org.motechproject.appointments.api.model.AppointmentCalendar;

// : Auto-generated Javadoc
/**
 * The Interface ScheduleRepository.
 */
public interface ScheduleRepository {

      /**
       * Gets the by external id.
       * 
       * @param externalID
       *              the external id
       * @return the by external id
       */
      List<AppointmentCalendar> getByExternalID(String externalID);

      /**
       * Gets the by appointment id.
       * 
       * @param appointmentID
       *              the appointment id
       * @return the by appointment id
       */
      Appointment getByAppointmentID(String appointmentID);

}
