package org.medicmobile.model;

import java.util.ArrayList;
import java.util.List;

// : Auto-generated Javadoc
/**
 * The Class AnganwadiCenterView.
 */
public class AnganwadiCenterView {

      /** The anganwadi center. */
      private AnganwadiCenter anganwadiCenter;

      /** The anganwadi worker name. */
      /*
       * private String anganwadiWorkerName;
       *//** The anganwadi worker id. */
      /*
       * private String anganwadiWorkerID;
       *//** The auxilary nurse name. */
      /*
       * private String auxilaryNurseName;
       *//** The auxilary nurse id. */
      /*
       * private String auxilaryNurseID;
       */

      private List<PersonIdName> auxilasryNurseNameIdList;

      private List<PersonIdName> anganwadiWorkerNameIdList;

      private List<PersonIdName> localVolunteerNameIdList;

      /**
       * Instantiates a new anganwadi center view.
       * 
       * @param anganwadiCenter
       *              the anganwadi center
       * @param anganwadiWorkerName
       *              the anganwadi worker name
       * @param auxilaryNurseName
       *              the auxilary nurse name
       * @param anganwadiWorkerID
       *              the anganwadi worker id
       * @param auxilaryNurseID
       *              the auxilary nurse id
       */
      /*
       * public AnganwadiCenterView(AnganwadiCenter anganwadiCenter, String
       * anganwadiWorkerName, String auxilaryNurseName, String
       * anganwadiWorkerID, String auxilaryNurseID) { this.anganwadiCenter =
       * anganwadiCenter; this.anganwadiWorkerName = anganwadiWorkerName;
       * this.auxilaryNurseName = auxilaryNurseName; this.anganwadiWorkerID =
       * anganwadiWorkerID; this.auxilaryNurseID = auxilaryNurseID; }
       */

      public AnganwadiCenterView(AnganwadiCenter anganwadiCenter2,
                  List<Staff> aNMList, List<Staff> aWWList,
                  List<Staff> localVolunteerList) {
            this.anganwadiCenter = anganwadiCenter2;
            this.auxilasryNurseNameIdList = new ArrayList<PersonIdName>();
            this.anganwadiWorkerNameIdList = new ArrayList<PersonIdName>();
            this.localVolunteerNameIdList = new ArrayList<PersonIdName>();
            for (Staff aNM : aNMList) {
                  this.auxilasryNurseNameIdList.add(new PersonIdName(aNM
                              .getName(), aNM.getStaffID()));
            }
            for (Staff aWW : aWWList) {
                  this.anganwadiWorkerNameIdList.add(new PersonIdName(aWW
                              .getName(), aWW.getStaffID()));
            }
            for (Staff lv : localVolunteerList) {
                  this.localVolunteerNameIdList.add(new PersonIdName(lv
                              .getName(), lv.getStaffID()));
            }
      }

      /**
       * Gets the anganwadi center.
       * 
       * @return the anganwadi center
       */
      public AnganwadiCenter getAnganwadiCenter() {
            return anganwadiCenter;
      }

      /**
       * Sets the anganwadi center.
       * 
       * @param anganwadiCenter
       *              the new anganwadi center
       */
      public void setAnganwadiCenter(AnganwadiCenter anganwadiCenter) {
            this.anganwadiCenter = anganwadiCenter;
      }

      public List<PersonIdName> getAuxilasryNurseNameIdList() {
            return auxilasryNurseNameIdList;
      }

      public void setAuxilasryNurseNameIdList(
                  List<PersonIdName> auxilasryNurseNameIdList) {
            this.auxilasryNurseNameIdList = auxilasryNurseNameIdList;
      }

      public List<PersonIdName> getAnganwadiWorkerNameIdList() {
            return anganwadiWorkerNameIdList;
      }

      public void setAnganwadiWorkerNameIdList(
                  List<PersonIdName> anganwadiWorkerNameIdList) {
            this.anganwadiWorkerNameIdList = anganwadiWorkerNameIdList;
      }

      public List<PersonIdName> getLocalVolunteerNameIdList() {
            return localVolunteerNameIdList;
      }

      public void setLocalVolunteerNameIdList(
                  List<PersonIdName> localVolunteerNameIdList) {
            this.localVolunteerNameIdList = localVolunteerNameIdList;
      }
      /*
       * public List<String> getAuxilasryNurseNameIdList() { return
       * auxilasryNurseNameIdList; }
       * 
       * public void setAuxilasryNurseNameIdList( List<String>
       * auxilasryNurseNameIdList) { this.auxilasryNurseNameIdList =
       * auxilasryNurseNameIdList; }
       * 
       * public List<String> getAnganwadiWorkerNameIdList() { return
       * anganwadiWorkerNameIdList; }
       * 
       * public void setAnganwadiWorkerNameIdList( List<String>
       * anganwadiWorkerNameIdList) { this.anganwadiWorkerNameIdList =
       * anganwadiWorkerNameIdList; }
       * 
       * public List<String> getLocalVolunteerNameIdList() { return
       * localVolunteerNameIdList; }
       * 
       * public void setLocalVolunteerNameIdList( List<String>
       * localVolunteerNameIdList) { this.localVolunteerNameIdList =
       * localVolunteerNameIdList; }
       *//**
       * Gets the anganwadi worker name.
       * 
       * @return the anganwadi worker name
       */
      /*
       * 
       * public String getAnganwadiWorkerName() { return anganwadiWorkerName; }
       *//**
       * Sets the anganwadi worker name.
       * 
       * @param anganwadiWorkerName
       *              the new anganwadi worker name
       */
      /*
       * 
       * public void setAnganwadiWorkerName(String anganwadiWorkerName) {
       * this.anganwadiWorkerName = anganwadiWorkerName; }
       *//**
       * Gets the auxilary nurse name.
       * 
       * @return the auxilary nurse name
       */
      /*
       * 
       * public String getAuxilaryNurseName() { return auxilaryNurseName; }
       *//**
       * Sets the auxilary nurse name.
       * 
       * @param auxilaryNurseName
       *              the new auxilary nurse name
       */
      /*
       * 
       * public void setAuxilaryNurseName(String auxilaryNurseName) {
       * this.auxilaryNurseName = auxilaryNurseName; }
       *//**
       * Gets the anganwadi worker id.
       * 
       * @return the anganwadi worker id
       */
      /*
       * 
       * public String getAnganwadiWorkerID() { return anganwadiWorkerID; }
       *//**
       * Sets the anganwadi worker id.
       * 
       * @param anganwadiWorkerID
       *              the new anganwadi worker id
       */
      /*
       * 
       * public void setAnganwadiWorkerID(String anganwadiWorkerID) {
       * this.anganwadiWorkerID = anganwadiWorkerID; }
       *//**
       * Gets the auxilary nurse id.
       * 
       * @return the auxilary nurse id
       */
      /*
       * 
       * public String getAuxilaryNurseID() { return auxilaryNurseID; }
       *//**
       * Sets the auxilary nurse id.
       * 
       * @param auxilaryNurseID
       *              the new auxilary nurse id
       */
      /*
       * 
       * public void setAuxilaryNurseID(String auxilaryNurseID) {
       * this.auxilaryNurseID = auxilaryNurseID; }
       */

}
