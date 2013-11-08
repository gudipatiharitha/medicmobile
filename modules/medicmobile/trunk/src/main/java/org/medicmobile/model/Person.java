package org.medicmobile.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.medicmobile.util.UtilService;
import org.motechproject.commons.couchdb.model.MotechBaseDataObject;

/**
 * The Class Person.
 */
@TypeDiscriminator("doc.type ='Person'")
public class Person extends MotechBaseDataObject {

      /** The Constant serialVersionUID. */
      private static final long serialVersionUID = -4217599365149453725L;

      /** The name. */
      @JsonProperty
      private String name;

      /** The date of birth. */
      @JsonProperty
      private Date dateOfBirth;

      /** The gender. */
      @JsonProperty
      private Gender gender;

      @JsonProperty
      private String nameInHindi;
      /** The alive. */
      @JsonProperty
      private boolean alive;

      /** The date of birth string. */
      @JsonIgnore
      private String dateOfBirthString;

      /**
       * Instantiates a new person.
       */
      public Person() {
            super();

      }

      /**
       * Gets the name.
       * 
       * @return the name
       */
      public String getName() {
            return name;
      }

      /**
       * Sets the name.
       * 
       * @param name
       *              the new name
       */
      public void setName(String name) {
            this.name = name;
      }

      /**
       * Gets the date of birth.
       * 
       * @return the date of birth
       */
      public Date getDateOfBirth() {
            return dateOfBirth;
      }

      /**
       * Sets the date of birth.
       * 
       * @param dateOfBirth
       *              the new date of birth
       */
      public void setDateOfBirth(Date dateOfBirth) {

            this.dateOfBirth = dateOfBirth;
      }

      /**
       * Gets the gender.
       * 
       * @return the gender
       */
      public Gender getGender() {
            return gender;
      }

      /**
       * Sets the gender.
       * 
       * @param gender
       *              the new gender
       */
      public void setGender(Gender gender) {
            this.gender = gender;
      }

      /**
       * Gets the alive.
       * 
       * @return the alive
       */
      public boolean getAlive() {
            return alive;
      }

      /**
       * Sets the alive.
       * 
       * @param alive
       *              the new alive
       */
      public void setAlive(boolean alive) {
            this.alive = alive;
      }

      /**
       * Gets the date of birth string.
       * 
       * @return the date of birth string
       */
      public String getDateOfBirthString() {
            return dateOfBirthString;
      }

      /**
       * Sets the date of birth string.
       * 
       * @param dateOfBirthString
       *              the new date of birth string
       */
      public void setDateOfBirthString(String dateOfBirthString) {
            this.dateOfBirthString = dateOfBirthString;
            this.setDateOfBirth(UtilService.stringToDate(dateOfBirthString));
      }

      public String getNameInHindi() {
            return nameInHindi;
      }

      public void setNameInHindi(String nameInHindi) {
            this.nameInHindi = nameInHindi;
      }

}
