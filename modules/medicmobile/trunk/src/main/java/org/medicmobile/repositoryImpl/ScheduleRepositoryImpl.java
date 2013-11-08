/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medicmobile.repositoryImpl;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.medicmobile.repository.ScheduleRepository;
import org.motechproject.appointments.api.model.Appointment;
import org.motechproject.appointments.api.model.AppointmentCalendar;
import org.motechproject.appointments.api.model.Visit;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class ScheduleRepositoryImpl.
 */
@Repository
public class ScheduleRepositoryImpl extends
            MotechBaseRepository<AppointmentCalendar> implements
            ScheduleRepository {

      /**
       * Instantiates a new schedule repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      public ScheduleRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {
            super(AppointmentCalendar.class, db);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ScheduleRepository#getByExternalID(java.lang
       * .String)
       */
      @View(name = "by_externalID", map = "function(doc){"
                  + "if(doc.type == 'AppointmentCalendar'){"
                  + "emit(doc.externalID , doc);}" + "}")
      public List<AppointmentCalendar> getByExternalID(String externalID) {
            ViewQuery query = createQuery("by_externalID").key(externalID)
                        .includeDocs(true);
            return db.queryView(query, AppointmentCalendar.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.medicmobile.repository.ScheduleRepository#getByAppointmentID(java
       * .lang.String)
       */
      @View(name = "by_AppointmentID", map = "function(doc){"
                  + "if(doc.type === 'AppointmentCalendar'){"
                  + " for(var i=0;i<doc.visits.length;i++) {"
                  + "emit(doc.visit[i].dueDate , doc.visit[i].appointment.id)}}"
                  + "}")
      public Appointment getByAppointmentID(String appointmentID) {
            ViewQuery query = createQuery("by_AppointmentID")
                        .key(appointmentID).includeDocs(true);
            return getAppointmentFromCalendar(
                        db.queryView(query, AppointmentCalendar.class),
                        appointmentID);
      }

      /**
       * Gets the appointment from calendar.
       * 
       * @param calendars
       *              the calendars
       * @param appointmentID
       *              the appointment id
       * @return the appointment from calendar
       */
      public Appointment getAppointmentFromCalendar(
                  List<AppointmentCalendar> calendars, String appointmentID) {
            if (!calendars.isEmpty()) {
                  AppointmentCalendar calendar = calendars.get(0);
                  for (Visit current : calendar.visits()) {
                        if (current.appointment().id() == appointmentID) {
                              return current.appointment();

                        }
                  }
            }
            return null;
      }
}
