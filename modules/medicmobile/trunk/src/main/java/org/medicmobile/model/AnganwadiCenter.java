package org.medicmobile.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

// : Auto-generated Javadoc
//@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
/**
 * The Class AnganwadiCenter.
 */
@TypeDiscriminator("doc.type=='AnganwadiCenter'")
public class AnganwadiCenter extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = 3514311276866020818L;

      /** The village. */
      @JsonProperty
      private List<String> village;

      /** The anganwadi center name. */
      @JsonProperty
      private String anganwadiCenterName;

      /** The panchayat. */
      @JsonProperty
      private String panchayat;

      /** The sector. */
      @JsonProperty
      private String sector;

      /** The program. */
      @JsonProperty
      private String program;

      /** The zilla. */
      @JsonProperty
      private String zilla;

      /** The state. */
      @JsonProperty
      private String state;

      /** The para. */
      @JsonProperty
      private String para;

      /** The male population. */
      @JsonProperty
      private int malePopulation;

      /** The female population. */
      @JsonProperty
      private int femalePopulation;

      /** The child population. */
      @JsonProperty
      private int childPopulation;

      /** The anganwadi id. */
      @JsonProperty
      private String anganwadiID;

      /**
       * Gets the village.
       * 
       * @return the village
       */
      public List<String> getVillage() {
            return village;
      }

      /**
       * Sets the village.
       * 
       * @param villages
       *              the new village
       */
      public void setVillage(List<String> villages) {
            this.village = villages;
      }

      /**
       * Gets the panchayat.
       * 
       * @return the panchayat
       */
      public String getPanchayat() {
            return panchayat;
      }

      /**
       * Sets the panchayat.
       * 
       * @param panchayat
       *              the new panchayat
       */
      public void setPanchayat(String panchayat) {
            this.panchayat = panchayat;
      }

      /**
       * Gets the sector.
       * 
       * @return the sector
       */
      public String getSector() {
            return sector;
      }

      /**
       * Sets the sector.
       * 
       * @param sector
       *              the new sector
       */
      public void setSector(String sector) {
            this.sector = sector;
      }

      /**
       * Gets the program.
       * 
       * @return the program
       */
      public String getProgram() {
            return program;
      }

      /**
       * Sets the program.
       * 
       * @param program
       *              the new program
       */
      public void setProgram(String program) {
            this.program = program;
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
       * Gets the zilla.
       * 
       * @return the zilla
       */
      public String getZilla() {
            return zilla;
      }

      /**
       * Sets the zilla.
       * 
       * @param zilla
       *              the new zilla
       */
      public void setZilla(String zilla) {
            this.zilla = zilla;
      }

      /**
       * Gets the state.
       * 
       * @return the state
       */
      public String getState() {
            return state;
      }

      /**
       * Sets the state.
       * 
       * @param state
       *              the new state
       */
      public void setState(String state) {
            this.state = state;
      }

      /**
       * Gets the male population.
       * 
       * @return the male population
       */
      public int getMalePopulation() {
            return malePopulation;
      }

      /**
       * Sets the male population.
       * 
       * @param malePopulation
       *              the new male population
       */
      public void setMalePopulation(int malePopulation) {
            this.malePopulation = malePopulation;
      }

      /**
       * Gets the female population.
       * 
       * @return the female population
       */
      public int getFemalePopulation() {
            return femalePopulation;
      }

      /**
       * Sets the female population.
       * 
       * @param femalePopulation
       *              the new female population
       */
      public void setFemalePopulation(int femalePopulation) {
            this.femalePopulation = femalePopulation;
      }

      /**
       * Gets the child population.
       * 
       * @return the child population
       */
      public int getChildPopulation() {
            return childPopulation;
      }

      /**
       * Sets the child population.
       * 
       * @param childPopulation
       *              the new child population
       */
      public void setChildPopulation(int childPopulation) {
            this.childPopulation = childPopulation;
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
       * Gets the anganwadi center name.
       * 
       * @return the anganwadi center name
       */
      public String getAnganwadiCenterName() {
            return anganwadiCenterName;
      }

      /**
       * Sets the anganwadi center name.
       * 
       * @param anganwadiCenterName
       *              the new anganwadi center name
       */
      public void setAnganwadiCenterName(String anganwadiCenterName) {
            this.anganwadiCenterName = anganwadiCenterName;
      }
}
