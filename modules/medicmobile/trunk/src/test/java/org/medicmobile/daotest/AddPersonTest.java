package org.medicmobile.daotest;

import org.ektorp.ComplexKey;
import org.joda.time.DateTime;


// : Auto-generated Javadoc
/**
 * The Class AddPersonTest.
 */
public final class AddPersonTest {

      /**
       * Instantiates a new adds the person test.
       */
      private AddPersonTest() {
            
      }
      
      /**
       * The main method.
       *
       * @param args the arguments
       */
      public static void main(String[] args) {
          System.out.println("Complex key: "+ComplexKey.of("09989136940",new DateTime()).toJson());
      }

}
