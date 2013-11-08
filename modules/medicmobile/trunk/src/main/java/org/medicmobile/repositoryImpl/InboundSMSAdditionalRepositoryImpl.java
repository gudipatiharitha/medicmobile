package org.medicmobile.repositoryImpl;

import static org.ektorp.ComplexKey.of;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.medicmobile.model.InboundSMSAdditional;
import org.medicmobile.repository.InboundSMSAdditionalRepository;
import org.motechproject.commons.couchdb.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// : Auto-generated Javadoc
/**
 * The Class InboundSMSAdditionalRepositoryImpl.
 */
@Repository
public class InboundSMSAdditionalRepositoryImpl extends
            MotechBaseRepository<InboundSMSAdditional> implements
            InboundSMSAdditionalRepository {

      /**
       * Instantiates a new inbound sms additional repository impl.
       * 
       * @param db
       *              the db
       */
      @Autowired
      protected InboundSMSAdditionalRepositoryImpl(
                  @Qualifier("medicmobileDatabase") CouchDbConnector db) {

            super(InboundSMSAdditional.class, db);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedBetweenFrom(java.lang.String, org.joda.time.DateTime,
       * org.joda.time.DateTime)
       */
      @View(name = "by_staff_id_within_update_time_range", map = "function(doc) {  if (doc.type === 'InboundSMSAdditional') emit([doc.staffID, doc.updatedTime], doc) }")
      public List<InboundSMSAdditional> messagesReceivedBetweenFrom(
                  String staffID, DateTime from, DateTime to) {
            return db.queryView(
                        createQuery("by_staff_id_within_update_time_range")
                                    .startKey(of(staffID, from))
                                    .endKey(of(staffID, to)).includeDocs(true),
                        InboundSMSAdditional.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedBetween(org.joda.time.DateTime, org.joda.time.DateTime)
       */
      @View(name = "within_update_time_range", map = "function(doc) {  if (doc.type === 'InboundSMSAdditional') emit( doc.updatedTime, doc) }")
      public List<InboundSMSAdditional> messagesReceivedBetween(DateTime from,
                  DateTime to) {
            return db.queryView(createQuery("within_update_time_range")
                        .startKey(from).endKey(to).includeDocs(true),
                        InboundSMSAdditional.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedFrom(java.lang.String)
       */
      @View(name = "by_staff_id", map = "function(doc) {  if (doc.type === 'InboundSMSAdditional') emit(doc.staffID, doc) }")
      public List<InboundSMSAdditional> messagesReceivedFrom(String staffID) {
            return db.queryView(createQuery("by_staff_id").key(staffID)
                        .includeDocs(true), InboundSMSAdditional.class);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedFromSuccessful(java.lang.String)
       */
      @Override
      public List<InboundSMSAdditional> messagesReceivedFromSuccessful(
                  String staffID) {
            return removeUnsuccessful(messagesReceivedFrom(staffID));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedBetweenFromSuccessful(java.lang.String,
       * org.joda.time.DateTime, org.joda.time.DateTime)
       */
      @Override
      public List<InboundSMSAdditional> messagesReceivedBetweenFromSuccessful(
                  String staffID, DateTime from, DateTime to) {

            return removeUnsuccessful(messagesReceivedBetweenFrom(staffID,
                        from, to));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.medicmobile.repository.InboundSMSAdditionalRepository#
       * messagesReceivedBetweenSuccessful(org.joda.time.DateTime,
       * org.joda.time.DateTime)
       */
      @Override
      public List<InboundSMSAdditional> messagesReceivedBetweenSuccessful(
                  DateTime from, DateTime to) {
            return removeUnsuccessful(messagesReceivedBetween(from, to));
      }

      /**
       * Removes the unsuccessful.
       * 
       * @param inboundSMSList
       *              the inbound sms list
       * @return the list
       */
      private List<InboundSMSAdditional> removeUnsuccessful(
                  List<InboundSMSAdditional> inboundSMSList) {
            for (int i = inboundSMSList.size() - 1; i >= 0; i--) {
                  if (!inboundSMSList.get(i).getUpdateSuccess()) {
                        inboundSMSList.remove(i);
                  }
            }
            return inboundSMSList;
      }

}
