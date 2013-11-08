package org.motechproject.sms.http.repository;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static java.util.Collections.reverseOrder;
import static org.ektorp.ComplexKey.of;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.motechproject.sms.api.DeliveryStatus;
import org.motechproject.sms.http.OutboundSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AllOutboundSMS extends MotechBaseRepository<OutboundSMS> implements AllOutboundSMSInterface {

    @Autowired
    protected AllOutboundSMS(@Qualifier("httpSMSDBConnector") CouchDbConnector db) {
        super(OutboundSMS.class, db);
    }

    /* (non-Javadoc)
     * * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#updateDeliveryStatus(java.lang.String, java.lang.String, java.lang.String)
     * */
    @Override
    public void updateDeliveryStatus(String recipient, String refNo, String deliveryStatus) {
        OutboundSMS outboundSMS = findLatestBy(refNo, recipient);
        outboundSMS.setStatus(DeliveryStatus.valueOf(deliveryStatus));
        update(outboundSMS);
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#findLatestBy(java.lang.String, java.lang.String)
     */
    @Override
    @View(name = "find_latest_by_recipient_and_ref_no", map = "function(doc) {  if (doc.type === 'OutboundSMS') emit([doc.messageTime, doc.refNo, doc.phoneNumber], doc) }")
    public OutboundSMS findLatestBy(String refNo, String phoneNumber) {
        final List<OutboundSMS> outboundSMSes = findAllBy(refNo, phoneNumber);
        return CollectionUtils.isEmpty(outboundSMSes) ? null : (OutboundSMS) sort(outboundSMSes, on(OutboundSMS.class).getMessageTime(), reverseOrder()).get(0);
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#findAllBy(java.lang.String, java.lang.String)
     */
    @Override
    @View(name = "by_recipient_and_ref_no", map = "function(doc) {  if (doc.type === 'OutboundSMS') emit([doc.refNo, doc.phoneNumber], doc) }")
    public List<OutboundSMS> findAllBy(String refNo, String phoneNumber) {
        return queryView("by_recipient_and_ref_no", of(refNo, phoneNumber));
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#findBy(org.joda.time.DateTime, java.lang.String, java.lang.String)
     */
    @Override
    @View(name = "by_message_time_and_recipient_and_ref_no_", map = "function(doc) {  if (doc.type === 'OutboundSMS') emit([doc.messageTime, doc.refNo, doc.phoneNumber], doc) }")
    public OutboundSMS findBy(DateTime deliveryTime, String refNo, String phoneNumber) {
        List<OutboundSMS> smses = queryView("by_message_time_and_recipient_and_ref_no_", of(deliveryTime, refNo, phoneNumber));
        return CollectionUtils.isEmpty(smses) ? null : smses.get(0);
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#messagesSentBetween(java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime)
     */
    @Override
    @View(name = "by_phone_number_within_message_time_range", map = "function(doc) {  if (doc.type === 'OutboundSMS') emit([doc.phoneNumber, doc.messageTime], doc) }")
    public List<OutboundSMS> messagesSentBetween(String phoneNumber, DateTime from, DateTime to) {
        return db.queryView(createQuery("by_phone_number_within_message_time_range").startKey(ComplexKey.of(phoneNumber, from))
                .endKey(ComplexKey.of(phoneNumber, to)).includeDocs(true), OutboundSMS.class);
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#messagesSentBetween(org.joda.time.DateTime, org.joda.time.DateTime)
     */
    @Override
    @View(name = "within_message_time_range", map = "function(doc) {  if (doc.type === 'OutboundSMS') emit(doc.messageTime, doc) }")
    public List<OutboundSMS> messagesSentBetween(DateTime from, DateTime to) {
        return db.queryView(createQuery("within_message_time_range").startKey(from)
                .endKey(to).includeDocs(true), OutboundSMS.class);
    }

    /* (non-Javadoc)
     * @see org.motechproject.sms.http.repository.AllOutboundSMSInterface#createOrReplace(org.motechproject.sms.http.OutboundSMS)
     */
    @Override
    public void createOrReplace(OutboundSMS outboundSMS) {
        OutboundSMS sms = findBy(outboundSMS.getMessageTime(), outboundSMS.getRefNo(), outboundSMS.getPhoneNumber());
        if (null == sms) {
            add(outboundSMS);
        } else {
            update(copyDocumentIdInfo(sms, outboundSMS));
        }
    }

    private OutboundSMS copyDocumentIdInfo(OutboundSMS source, OutboundSMS target) {
        target.setId(source.getId());
        target.setRevision(source.getRevision());
        return target;
    }
}
