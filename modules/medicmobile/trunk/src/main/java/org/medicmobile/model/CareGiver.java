package org.medicmobile.model;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;
import org.ektorp.support.TypeDiscriminator;

/**
 * The Class CareGiver.
 */
@TypeDiscriminator("doc.type=='CareGiver'")
public class CareGiver extends DetailedPerson {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 8420309505121588895L;

      /** The care giver id. */
      @JsonProperty
      private String careGiverID;

      /** The husband name. */
      @JsonProperty
      private String husbandName;

      /** The phone number of whom. */
      @JsonProperty
      private String phoneNumberOfWhom;

      /** The caste. */
      @JsonProperty
      private String caste;

      /** The name of sub center. */
      @JsonProperty
      private String nameOfSubCenter;

      /** The distance from anganwadi center hrs. */
      @JsonProperty
      private int distanceFromAnganwadiCenterHrs;

      /** The distance from anganwadi center mins. */
      @JsonProperty
      private int distanceFromAnganwadiCenterMins;

      /** The distance from anganwadi center mins string. */
      @JsonIgnore
      private String distanceFromAnganwadiCenterMinsString;

      /** The distance from anganwadi center hrs string. */
      @JsonIgnore
      private String distanceFromAnganwadiCenterHrsString;

      /** The local volunteer id. */
      @JsonProperty
      private String localVolunteerID;

      /** The village. */
      @JsonProperty
      private String village;

      /** The mode of travel. */
      @JsonProperty
      private String modeOfTravel;

      /** The id card type. */
      @JsonProperty
      private IDCardType idCardType;

      /** The id card type string. */
      @JsonIgnore
      private String idCardTypeString;

      /** The id card number. */
      @JsonProperty
      private String idCardNumber;

      /** The block. */
      @JsonProperty
      private String block;

      /** The asha worker. */
      @JsonProperty
      private String ashaWorker;

      /** The jsy beneficiary. */
      @JsonProperty
      private Boolean jsyBeneficiary;

      /** The image path of care giver. */
      @JsonProperty
      private String imagePathOfCareGiver;

      /** The para. */
      @JsonProperty
      private String para;

      /** The children. */
      @DocumentReferences(fetch = FetchType.LAZY, backReference = "careGiverID")
      @JsonProperty
      private Set<Child> children;

      /** The anganwadi id. */
      @JsonProperty
      private String anganwadiID;

      /**
       * Gets the asha worker.
       * 
       * @return the asha worker
       */
      public String getAshaWorker() {
            return ashaWorker;
      }

      /**
       * Sets the asha worker.
       * 
       * @param ashaWorker
       *              the new asha worker
       */
      public void setAshaWorker(String ashaWorker) {
            this.ashaWorker = ashaWorker;
      }

      /**
       * Gets the block.
       * 
       * @return the block
       */
      public String getBlock() {
            return block;
      }

      /**
       * Sets the block.
       * 
       * @param block
       *              the new block
       */
      public void setBlock(String block) {
            this.block = block;
      }

      /**
       * Gets the jsy beneficiary.
       * 
       * @return the jsy beneficiary
       */
      public Boolean getJsyBeneficiary() {
            return jsyBeneficiary;
      }

      /**
       * Sets the jsy beneficiary.
       * 
       * @param jsyBeneficiary
       *              the new jsy beneficiary
       */
      public void setJsyBeneficiary(Boolean jsyBeneficiary) {
            this.jsyBeneficiary = jsyBeneficiary;
      }

      /**
       * Instantiates a new care giver.
       */
      public CareGiver() {
            super();
      }

      /**
       * Gets the mode of travel.
       * 
       * @return the mode of travel
       */
      public String getModeOfTravel() {
            return modeOfTravel;
      }

      /**
       * Sets the mode of travel.
       * 
       * @param modeOfTravel
       *              the new mode of travel
       */
      public void setModeOfTravel(String modeOfTravel) {
            this.modeOfTravel = modeOfTravel;
      }

      /**
       * Gets the husband name.
       * 
       * @return the husband name
       */
      public String getHusbandName() {
            return husbandName;
      }

      /**
       * Sets the husband name.
       * 
       * @param husbandName
       *              the new husband name
       */
      public void setHusbandName(String husbandName) {
            this.husbandName = husbandName;
      }

      /**
       * Gets the phone number of whom.
       * 
       * @return the phone number of whom
       */
      public String getPhoneNumberOfWhom() {
            return phoneNumberOfWhom;
      }

      /**
       * Sets the phone number of whom.
       * 
       * @param phoneNumberOfWhom
       *              the new phone number of whom
       */
      public void setPhoneNumberOfWhom(String phoneNumberOfWhom) {
            this.phoneNumberOfWhom = phoneNumberOfWhom;
      }

      /**
       * Gets the caste.
       * 
       * @return the caste
       */
      public String getCaste() {
            return caste;
      }

      /**
       * Sets the caste.
       * 
       * @param caste
       *              the new caste
       */
      public void setCaste(String caste) {
            this.caste = caste;
      }

      /**
       * Gets the name of sub center.
       * 
       * @return the name of sub center
       */
      public String getNameOfSubCenter() {
            return nameOfSubCenter;
      }

      /**
       * Sets the name of sub center.
       * 
       * @param nameOfSubCenter
       *              the new name of sub center
       */
      public void setNameOfSubCenter(String nameOfSubCenter) {
            this.nameOfSubCenter = nameOfSubCenter;
      }

      /**
       * Gets the distance from anganwadi center hrs.
       * 
       * @return the distance from anganwadi center hrs
       */
      public int getDistanceFromAnganwadiCenterHrs() {
            return distanceFromAnganwadiCenterHrs;
      }

      /**
       * Sets the distance from anganwadi center hrs.
       * 
       * @param distanceFromAnganwadiCenterHrs
       *              the new distance from anganwadi center hrs
       */
      public void setDistanceFromAnganwadiCenterHrs(
                  int distanceFromAnganwadiCenterHrs) {
            this.distanceFromAnganwadiCenterHrs = distanceFromAnganwadiCenterHrs;
      }

      /**
       * Gets the distance from anganwadi center mins.
       * 
       * @return the distance from anganwadi center mins
       */
      public int getDistanceFromAnganwadiCenterMins() {
            return distanceFromAnganwadiCenterMins;
      }

      /**
       * Sets the distance from anganwadi center mins.
       * 
       * @param distanceFromAnganwadiCenterMins
       *              the new distance from anganwadi center mins
       */
      public void setDistanceFromAnganwadiCenterMins(
                  int distanceFromAnganwadiCenterMins) {
            this.distanceFromAnganwadiCenterMins = distanceFromAnganwadiCenterMins;
      }

      /**
       * Gets the local volunteer id.
       * 
       * @return the local volunteer id
       */
      public String getLocalVolunteerID() {
            return localVolunteerID;
      }

      /**
       * Sets the local volunteer id.
       * 
       * @param localVolunteerID
       *              the new local volunteer id
       */
      public void setLocalVolunteerID(String localVolunteerID) {
            this.localVolunteerID = localVolunteerID;
      }

      /**
       * Gets the village.
       * 
       * @return the village
       */
      public String getVillage() {
            return village;
      }

      /**
       * Sets the village.
       * 
       * @param village
       *              the new village
       */
      public void setVillage(String village) {
            this.village = village;
      }

      /**
       * Gets the care giver id.
       * 
       * @return the care giver id
       */
      public String getCareGiverID() {
            return careGiverID;
      }

      /**
       * Sets the care giver id.
       * 
       * @param careGiverID
       *              the new care giver id
       */
      public void setCareGiverID(String careGiverID) {
            this.careGiverID = careGiverID;
      }

      /**
       * Gets the children.
       * 
       * @return the children
       */
      public Set<Child> getChildren() {
            return children;
      }

      /**
       * Sets the children.
       * 
       * @param children
       *              the new children
       */
      public void setChildren(final Set<Child> children) {
            this.children = children;
            for (int i = 0; i < 15 - this.children.size(); i++) {
                  this.children.add(new Child());
            }
      }

      /**
       * Gets the id card type.
       * 
       * @return the id card type
       */
      public IDCardType getIdCardType() {
            return idCardType;
      }

      /**
       * Sets the id card type.
       * 
       * @param idCardType
       *              the new id card type
       */
      public void setIdCardType(final IDCardType idCardType) {
            this.idCardType = idCardType;
      }

      /**
       * Gets the id card number.
       * 
       * @return the id card number
       */
      public String getIdCardNumber() {
            return idCardNumber;
      }

      /**
       * Sets the id card number.
       * 
       * @param idCardNumber
       *              the new id card number
       */
      public void setIdCardNumber(String idCardNumber) {
            this.idCardNumber = idCardNumber;
      }

      /**
       * Gets the image path of care giver.
       * 
       * @return the image path of care giver
       */
      public String getImagePathOfCareGiver() {
            return imagePathOfCareGiver;
      }

      /**
       * Sets the image path of care giver.
       * 
       * @param imagePathOfCareGiver
       *              the new image path of care giver
       */
      public void setImagePathOfCareGiver(String imagePathOfCareGiver) {
            this.imagePathOfCareGiver = imagePathOfCareGiver;
      }

      /**
       * Gets the para.
       * 
       * @return the para
       */
      public String getPara() {
            return para;
      }

      /**
       * Sets the para.
       * 
       * @param para
       *              the new para
       */
      public void setPara(String para) {
            this.para = para;
      }

      /**
       * Gets the anganwadi id.
       * 
       * @return the anganwadi id
       */
      public String getAnganwadiID() {
            return anganwadiID;
      }

      /**
       * Sets the anganwadi id.
       * 
       * @param anganwadiID
       *              the new anganwadi id
       */
      public void setAnganwadiID(String anganwadiID) {
            this.anganwadiID = anganwadiID;
      }

      /**
       * Gets the id card type string.
       * 
       * @return the id card type string
       */
      public String getIdCardTypeString() {
            return idCardTypeString;
      }

      /**
       * Sets the id card type string.
       * 
       * @param idCardTypeString
       *              the new id card type string
       */
      public void setIdCardTypeString(String idCardTypeString) {
            if (!idCardTypeString.equals("")) {
                  this.setIdCardType(IDCardType.valueOf(idCardTypeString));
            }
      }

      /**
       * Gets the distance from anganwadi center mins string.
       * 
       * @return the distance from anganwadi center mins string
       */
      public String getDistanceFromAnganwadiCenterMinsString() {
            return distanceFromAnganwadiCenterMinsString;
      }

      /**
       * Sets the distance from anganwadi center mins string.
       * 
       * @param distanceFromAnganwadiCenterMinsString
       *              the new distance from anganwadi center mins string
       */
      public void setDistanceFromAnganwadiCenterMinsString(
                  String distanceFromAnganwadiCenterMinsString) {
            this.distanceFromAnganwadiCenterMinsString = distanceFromAnganwadiCenterMinsString;
            if (distanceFromAnganwadiCenterMinsString.length() > 0) {
                  this.setDistanceFromAnganwadiCenterHrs(Integer
                              .parseInt(distanceFromAnganwadiCenterMinsString));
            } else {
                  this.setDistanceFromAnganwadiCenterMins(0);
            }
      }

      /**
       * Gets the distance from anganwadi center hrs string.
       * 
       * @return the distance from anganwadi center hrs string
       */
      public String getDistanceFromAnganwadiCenterHrsString() {
            return distanceFromAnganwadiCenterHrsString;
      }

      /**
       * Sets the distance from anganwadi center hrs string.
       * 
       * @param distanceFromAnganwadiCenterHrsString
       *              the new distance from anganwadi center hrs string
       */
      public void setDistanceFromAnganwadiCenterHrsString(
                  String distanceFromAnganwadiCenterHrsString) {
            if (distanceFromAnganwadiCenterHrsString.length() > 0) {
                  this.setDistanceFromAnganwadiCenterHrs(Integer
                              .parseInt(distanceFromAnganwadiCenterHrsString));
            } else {
                  this.setDistanceFromAnganwadiCenterHrs(0);
            }
      }

      @Override
      public int hashCode() {
            return this.getCareGiverID().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
            CareGiver careGiverObj = (CareGiver) obj;
            if (this.getCareGiverID().equals(careGiverObj.getCareGiverID())) {
                  return true;
            } else {
                  return false;
            }
      }

      /**
       * Copy care giver.
       * 
       * @param careGiver
       *              the care giver
       * @return the care giver
       */
      public CareGiver copyCareGiver(CareGiver careGiver) {
            this.setName(careGiver.getName());
            this.setAddress(careGiver.getAddress());
            this.setAlive(careGiver.getAlive());
            this.setGender(careGiver.getGender());
            this.setDateOfBirth(careGiver.getDateOfBirth());
            this.setAddress(careGiver.getAddress());
            this.setCaste(careGiver.getCaste());
            this.setDistanceFromAnganwadiCenterHrs(careGiver
                        .getDistanceFromAnganwadiCenterHrs());
            this.setDistanceFromAnganwadiCenterMins(careGiver
                        .getDistanceFromAnganwadiCenterMins());
            this.setHusbandName(careGiver.getHusbandName());
            this.setJsyBeneficiary(careGiver.getJsyBeneficiary());
            this.setLocalVolunteerID(careGiver.getLocalVolunteerID());
            this.setPhoneNumber(careGiver.getPhoneNumber());
            this.setPhoneNumberOfWhom(careGiver.getPhoneNumberOfWhom());
            this.setReligion(careGiver.getReligion());
            this.setVillage(careGiver.getVillage());
            this.setWhetherToReceiveSMS(careGiver.getWhetherToReceiveSMS());
            this.setAshaWorker(careGiver.getAshaWorker());
            this.setBlock(careGiver.getBlock());
            this.setState(careGiver.getState());
            this.setDistrict(careGiver.getDistrict());
            this.setModeOfTravel(careGiver.getModeOfTravel());
            this.setIdCardType(careGiver.getIdCardType());
            this.setAnganwadiID(careGiver.getAnganwadiID());
            return this;
      }
}
