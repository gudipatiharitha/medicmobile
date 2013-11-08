package org.medicmobile.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

// : Auto-generated Javadoc
/**
 * The Class DetailedPerson.
 */
public class DetailedPerson extends Person {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = -153995423698770542L;

      /** The religion. */
      @JsonProperty
      private String religion;

      /** The phone number. */
      @JsonProperty
      private String phoneNumber;

      /** The address. */
      @JsonProperty
      private String address;

      /** The whether to receive sms. */
      @JsonProperty
      private boolean whetherToReceiveSMS;

      /** The time of enrollment. */
      @JsonProperty
      private DateTime timeOfEnrollment;

      /** The state. */
      @JsonProperty
      private String state;

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
       * Gets the district.
       * 
       * @return the district
       */
      public String getDistrict() {
            return district;
      }

      /**
       * Sets the district.
       * 
       * @param district
       *              the new district
       */
      public void setDistrict(String district) {
            this.district = district;
      }

      /** The district. */
      @JsonProperty
      private String district;

      /**
       * Instantiates a new detailed person.
       */
      public DetailedPerson() {
            super();

      }

      /**
       * Gets the religion.
       * 
       * @return the religion
       */
      public String getReligion() {
            return religion;
      }

      /**
       * Sets the religion.
       * 
       * @param religion
       *              the religion
       * @return the person
       */
      public Person setReligion(String religion) {
            this.religion = religion;
            return this;
      }

      /**
       * Gets the phone number.
       * 
       * @return the phone number
       */
      public String getPhoneNumber() {
            return phoneNumber;
      }

      /**
       * Sets the phone number.
       * 
       * @param phoneNumber
       *              the phone number
       * @return the person
       */
      public Person setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
      }

      /**
       * Gets the address.
       * 
       * @return the address
       */
      public String getAddress() {
            return address;
      }

      /**
       * Sets the address.
       * 
       * @param address
       *              the address
       * @return the person
       */
      public Person setAddress(String address) {
            this.address = address;
            return this;
      }

      /**
       * Gets the whether to receive sms.
       * 
       * @return the whether to receive sms
       */
      public boolean getWhetherToReceiveSMS() {
            return whetherToReceiveSMS;
      }

      /**
       * Sets the whether to receive sms.
       * 
       * @param whetherToReceiveSMS
       *              the new whether to receive sms
       */
      public void setWhetherToReceiveSMS(boolean whetherToReceiveSMS) {
            this.whetherToReceiveSMS = whetherToReceiveSMS;
      }

      /**
       * Gets the time of enrollment.
       * 
       * @return the time of enrollment
       */
      public DateTime getTimeOfEnrollment() {
            return timeOfEnrollment;
      }

      /**
       * Sets the time of enrollment.
       * 
       * @param timeOfEnrollment
       *              the new time of enrollment
       */
      public void setTimeOfEnrollment(DateTime timeOfEnrollment) {
            this.timeOfEnrollment = timeOfEnrollment;
      }
}
