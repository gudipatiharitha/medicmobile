package org.motechproject.sms.http.repository;

import static org.ektorp.ComplexKey.of;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.motechproject.sms.http.InboundSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllInboundSMS extends MotechBaseRepository<InboundSMS> {

    @Autowired
    protected AllInboundSMS(@Qualifier("httpSMSDBConnector") CouchDbConnector db) {
        super(InboundSMS.class, db);
    }

    @View(name = "by_phone_number_within_message_time_range", map = "function(doc) {  if (doc.type === 'InboundSMS') emit([doc.phoneNumber, doc.messageTime], doc) }")
    public List<InboundSMS> messagesReceivedBetween(String phoneNumber, DateTime from, DateTime to) {
        return db.queryView(createQuery("by_phone_number_within_message_time_range").startKey(of(phoneNumber, from))
                .endKey(of(phoneNumber, to)).includeDocs(true), InboundSMS.class);
    }

    @View(name = "within_message_time_range", map = "function(doc) {  if (doc.type === 'InboundSMS') emit(doc.messageTime, doc) }")
    public List<InboundSMS> messagesReceivedBetween(DateTime from, DateTime to) {
        return db.queryView(createQuery("within_message_time_range").startKey(from)
                .endKey(to).includeDocs(true), InboundSMS.class);
    }
}
