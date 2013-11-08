package org.motechproject.appointments.api.repository;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.appointments.api.mapper.VisitResponseMapper;
import org.motechproject.appointments.api.model.AppointmentCalendar;
import org.motechproject.appointments.api.model.Visit;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AllAppointmentCalendars extends MotechBaseRepository<AppointmentCalendar> {
    
    private static final String FUNCTION = "function(  doc) { \n" +
            "  if (doc.type === 'AppointmentCalendar') {\n" +
            "    for (var i = 0; i < doc.visits.length; i++) {\n";
    private static final String BRACE_NEWLINE = "}\n";
    private static final String BRACE = "}";

    @Autowired
    private VisitResponseMapper visitResponseMapper;

    @Autowired
    public AllAppointmentCalendars(@Qualifier("appointmentsDatabase") CouchDbConnector db) {
        super(AppointmentCalendar.class, db);
    }

    public void saveAppointmentCalendar(AppointmentCalendar appointmentCalendar) {
        addOrReplace(appointmentCalendar, "externalId", appointmentCalendar.getExternalId());
    }

    @GenerateView
    public AppointmentCalendar findByExternalId(String externalId) {
        return singleResult(queryView("by_externalId", externalId));
    }

    @View(name = "by_dueDate", map =  FUNCTION +
            "      emit(doc.visits[i].appointment.dueDate,{\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findVisitsWithDueDateInRange(DateTime start, DateTime end) {
        ViewQuery query = createQuery("by_dueDate").startKey(start).endKey(end);
        List<VisitQueryResult> visitQueryResults = db.queryView(query, VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }

    @View(name = "by_visitDate", map = FUNCTION +
            "      emit(doc.visits[i].visitDate,{\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findVisitsWithVisitDateInRange(DateTime start, DateTime end) {
        ViewQuery query = createQuery("by_visitDate").startKey(start).endKey(end);
        List<VisitQueryResult> visitQueryResults = db.queryView(query, VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }
    
    

    @View(name = "by_missed_visits", map = FUNCTION +
            "      if(doc.visits[i].visitDate == null) {\n" +
            "        emit(doc._id,{\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findMissedVisits() {
        List<VisitQueryResult> visitQueryResults = db.queryView(createQuery("by_missed_visits"), VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }

    @View(name = "by_property", map = FUNCTION +
            "       for (var prop in doc.visits[i].data)\n" +
            "          emit([prop, doc.visits[i].data[prop]], {\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findByMetadataProperty(String property, String value) {
        List<VisitQueryResult> visitQueryResults = db.queryView(createQuery("by_property").key(ComplexKey.of(property, value)).includeDocs(false), VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }
    @View(name = "by_originalDueDate", map = FUNCTION +
            "      emit(doc.visits[i].appointment.originalDueDate,{\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findVisitsWithOriginalDueDateInRange(DateTime start, DateTime end) {
        ViewQuery query = createQuery("by_originalDueDate").startKey(start).endKey(end);
        List<VisitQueryResult> visitQueryResults = db.queryView(query, VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }
    @View(name = "by_AnganwadiIDAndDueDate", map = FUNCTION +
            "   if(doc.visits[i].visitDate == null){" +
            "       for (var prop in doc.visits[i].data)\n" +
            "           emit([prop, doc.visits[i].data[prop],doc.visits[i].missed,doc.visits[i].appointment.dueDate], {\"externalId\" :doc.externalId,\"visit\" : doc.visits[i]});\n" +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE_NEWLINE +
            BRACE)
    public List<VisitResponse> findByMetadataPropertyAndDueDate(String property, String value, boolean missed, DateTime dueDate) {
        List<VisitQueryResult> visitQueryResults = db.queryView(createQuery("by_AnganwadiIDAndDueDate").startKey(ComplexKey.of(property, value, missed, dueDate.minusYears(10))).endKey(ComplexKey.of(property, value, missed, dueDate)).includeDocs(false), VisitQueryResult.class);
        return extractVisitResponse(visitQueryResults);
    }
    public List<VisitResponse> findVisitsByExternalId(String externalId) {
        AppointmentCalendar calendar = findByExternalId(externalId);
        List<VisitResponse> visitResponses = new ArrayList<VisitResponse>();

        if (calendar != null) {
            for (Visit visit : calendar.visits()) {
                VisitResponse visitResponse = transformVisitToResponse(visit, calendar.getExternalId());
                visitResponses.add(visitResponse);
            }
        }
        return visitResponses;
    }

    private VisitResponse transformVisitToResponse(Visit visit, String externalId) {
        VisitResponse visitResponse = visitResponseMapper.map(visit);
        visitResponse.setExternalId(externalId);
        return visitResponse;
    }

    private List<VisitResponse> extractVisitResponse(List<VisitQueryResult> visitQueryResults) {
        List<VisitResponse> visitResponses = new ArrayList<VisitResponse>();
        for (VisitQueryResult visitQueryResult : visitQueryResults) {
            visitResponses.add(transformVisitToResponse(visitQueryResult.getVisit(), visitQueryResult.getExternalId()));
        }
        return visitResponses;
    }
}

