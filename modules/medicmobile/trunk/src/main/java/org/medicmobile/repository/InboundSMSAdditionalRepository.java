package org.medicmobile.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.medicmobile.model.InboundSMSAdditional;

// : Auto-generated Javadoc
/**
 * The Interface InboundSMSAdditionalRepository.
 */
public interface InboundSMSAdditionalRepository {

      /**
       * Messages received between from.
       * 
       * @param staffID
       *              the staff id
       * @param from
       *              the from
       * @param to
       *              the to
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedBetweenFrom(String staffID,
                  DateTime from, DateTime to);

      /**
       * Messages received between.
       * 
       * @param from
       *              the from
       * @param to
       *              the to
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedBetween(DateTime from,
                  DateTime to);

      /**
       * Messages received from.
       * 
       * @param staffID
       *              the staff id
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedFrom(String staffID);

      /**
       * Messages received from successful.
       * 
       * @param staffID
       *              the staff id
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedFromSuccessful(String staffID);

      /**
       * Messages received between from successful.
       * 
       * @param staffID
       *              the staff id
       * @param from
       *              the from
       * @param to
       *              the to
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedBetweenFromSuccessful(
                  String staffID, DateTime from, DateTime to);

      /**
       * Messages received between successful.
       * 
       * @param from
       *              the from
       * @param to
       *              the to
       * @return the list
       */
      List<InboundSMSAdditional> messagesReceivedBetweenSuccessful(
                  DateTime from, DateTime to);

      /**
       * Adds the.
       * 
       * @param message
       *              the message
       */
      void add(InboundSMSAdditional message);
}
