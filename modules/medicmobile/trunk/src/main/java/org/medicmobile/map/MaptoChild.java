package org.medicmobile.map;

import java.util.Map;

import org.medicmobile.model.BloodGroup;
import org.medicmobile.model.Child;
import org.medicmobile.model.Gender;
import org.medicmobile.util.MedicMobileConstants;
import org.medicmobile.util.UtilService;

public final class MaptoChild {

      /**
       * Instantiates a new mapto child.
       */
      private MaptoChild() {

      }

      public static Child mapToChild(Map<String, String[]> childMap) {
            Child child = new Child();
            child.setCareGiverID(childMap
                        .get(MedicMobileConstants.CAREGIVER_ID)[0]);

            if (childMap.containsKey(MedicMobileConstants.CHILD_ID)) {
                  child.setChildID((String) (childMap
                              .get(MedicMobileConstants.CHILD_ID)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.DATE_OF_BIRTH)) {
                  child.setDateOfBirth(UtilService.stringToDate((String) childMap
                              .get(MedicMobileConstants.DATE_OF_BIRTH)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.NAME)) {
                  child.setName((String) (childMap
                              .get(MedicMobileConstants.NAME)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.NAME_IN_HINDI)) {
                  child.setNameInHindi((String) childMap
                              .get(MedicMobileConstants.NAME_IN_HINDI)[0]);
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_CALENDAR_ID)) {
                  child.setChildCalendarID((String) (childMap
                              .get(MedicMobileConstants.CHILD_CALENDAR_ID)[0]));
            }

            if (childMap.containsKey(MedicMobileConstants.ALIVE)) {
                  child.setAlive(Boolean.valueOf((String) childMap
                              .get(MedicMobileConstants.ALIVE)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.GENDER)) {
                  child.setGender(Gender.valueOf((String) childMap
                              .get(MedicMobileConstants.GENDER)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_MCTS_ID)) {
                  child.setMctsID((String) childMap
                              .get(MedicMobileConstants.CHILD_MCTS_ID)[0]);
            }
            mapToChildHelper(childMap, child);
            return child;
      }
      
      private static void mapToChildHelper(Map<String, String[]> childMap, Child child) {
          if (childMap.containsKey(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY)) {
              child.setPlaceOfDelivery((String) (childMap
                          .get(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_SYSTEM_ID)) {
              child.setChildSystemId((String) (childMap
                          .get(MedicMobileConstants.CHILD_SYSTEM_ID)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_WEIGHT)) {
              // child.setWeight(Float.parseFloat((String) (childMap
              // .get(MedicMobileConstants.CHILD_WEIGHT)[0])));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_HEIGHT)) {
              // child.setHeight(Float.parseFloat((String) (childMap
              // .get(MedicMobileConstants.CHILD_HEIGHT)[0])));
        }
        if (childMap.containsKey(MedicMobileConstants.BLOOD_GROUP)
                    && !((String) childMap
                                .get(MedicMobileConstants.BLOOD_GROUP)[0])
                                .equals("")) {
              child.setBloodGroup(BloodGroup.valueOf((String) childMap
                          .get(MedicMobileConstants.BLOOD_GROUP)[0]));
        }
      }
}
