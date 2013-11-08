package org.medicmobile.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.medicmobile.dto.ScheduleObject;
import org.motechproject.server.config.SettingsFacade;

/**
 * The Class ChildView.
 */
public class ChildView {

      /** The child. */
      private Child child;

      /** The care giver view. */
      private CareGiverView careGiverView;

      /** The visit responses. */
      private List<ScheduleObject> visitResponses;

      private SettingsFacade vaccineOrder;

      /**
       * Instantiates a new child view.
       * 
       * @param careGiverView
       *              the care giver view
       * @param child
       *              the child
       * @param visitResponses
       *              the visit responses
       */
      public ChildView(CareGiverView careGiverView, Child child,
                  List<ScheduleObject> visitResponses,
                  SettingsFacade vaccineOrder) {
            this.careGiverView = careGiverView;
            this.child = child;
            this.vaccineOrder = vaccineOrder;
            this.setVisitResponses(visitResponses);
      }

      /**
       * Gets the child.
       * 
       * @return the child
       */
      public Child getChild() {
            return child;
      }

      /**
       * Sets the child.
       * 
       * @param child
       *              the new child
       */
      public void setChild(Child child) {
            this.child = child;
      }

      /**
       * Gets the care giver view.
       * 
       * @return the care giver view
       */
      public CareGiverView getCareGiverView() {
            return careGiverView;
      }

      /**
       * Sets the care giver view.
       * 
       * @param careGiverView
       *              the new care giver view
       */
      public void setCareGiverView(CareGiverView careGiverView) {
            this.careGiverView = careGiverView;
      }

      /**
       * Gets the visit responses.
       * 
       * @return the visit responses
       */
      public List<ScheduleObject> getVisitResponses() {
            return visitResponses;
      }

      /**
       * Sets the visit responses.
       * 
       * @param visitResponses
       *              the new visit responses
       */
      public final void setVisitResponses(List<ScheduleObject> visitResponses) {
            this.visitResponses = visitResponses;
            for (ScheduleObject itObject : visitResponses) {
                  itObject.setOrder(Integer.parseInt(vaccineOrder
                              .getProperty(itObject.getName())));
            }
            Collections.sort(visitResponses, new OrderComparator());
      }

      /*
       * public final List<ScheduleObject> orderedList() {
       * 
       * return visitResponses; }
       */

      private class OrderComparator implements Comparator<ScheduleObject> {

            @Override
            public int compare(ScheduleObject o1, ScheduleObject o2) {
                  return o1.getOrder() - o2.getOrder();
            }
      }
}
