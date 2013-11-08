package org.medicmobile.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
/**
 * The Class MasterVaccine.
 */
@TypeDiscriminator("doc.type=='MasterVaccine'")
public class MasterVaccine extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = -1468587009646070456L;

      /** The vaccine name. */
      @JsonProperty
      private String vaccineName;

      /** The vaccine schedule. */
      @JsonProperty
      private List<VaccineSchedule> vaccineSchedule;

      /** The vaccine id. */
      @JsonProperty
      private String vaccineID;

      /**
       * Gets the vaccine name.
       * 
       * @return the vaccine name
       */
      public String getVaccineName() {
            return vaccineName;
      }

      /**
       * Sets the vaccine name.
       * 
       * @param vaccineName
       *              the new vaccine name
       */
      public void setVaccineName(String vaccineName) {
            this.vaccineName = vaccineName;
      }

      /**
       * Gets the vaccine schedule.
       * 
       * @return the vaccine schedule
       */
      public List<VaccineSchedule> getVaccineSchedule() {
            return vaccineSchedule;
      }

      /**
       * Sets the vaccine schedule.
       * 
       * @param vaccineSchedule
       *              the new vaccine schedule
       */
      public void setVaccineSchedule(List<VaccineSchedule> vaccineSchedule) {
            this.vaccineSchedule = vaccineSchedule;
      }

      /**
       * Gets the vaccine id.
       * 
       * @return the vaccine id
       */
      public String getVaccineID() {
            return vaccineID;
      }

      /**
       * Sets the vaccine id.
       * 
       * @param vaccineID
       *              the new vaccine id
       */
      public void setVaccineID(String vaccineID) {
            this.vaccineID = vaccineID;
      }

}
