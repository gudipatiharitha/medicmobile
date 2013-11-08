package org.medicmobile.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.medicmobile.dto.ScheduleObject;
import org.medicmobile.model.BloodGroup;
import org.medicmobile.model.CareGiver;
import org.medicmobile.model.Child;
import org.medicmobile.model.Gender;
import org.medicmobile.model.IDCardType;
import org.medicmobile.util.UtilService;
import org.medicmobile.util.MedicMobileConstants;
import org.motechproject.appointments.api.service.contract.VisitResponse;

/**
 * The Class MapToCareGiver.
 */
public final class MapToCareGiver {

      /**
       * Instantiates a new map to care giver.
       */
      private MapToCareGiver() {

      }

      /**
       * Map to care giver.
       * 
       * @param careGiverMap
       *              the care giver map
       * @return the care giver
       */
      public static CareGiver mapToCareGiver(
                  final Map<Object, Object[]> careGiverMap) {

            CareGiver careGiver = new CareGiver();
            mapToCareGiverHelper(careGiverMap,careGiver);
                        if (careGiverMap
                        .containsKey(MedicMobileConstants.DISTANCE_FROM_ANGANWADI_CENTER_MINS)) {
                  String distance = (String) careGiverMap
                              .get(MedicMobileConstants.DISTANCE_FROM_ANGANWADI_CENTER_MINS)[0];
                  if (distance.equals("")) {
                        distance = "0";
                  }
                  careGiver.setDistanceFromAnganwadiCenterMins(Integer
                              .parseInt(distance));
            }
            if (careGiverMap.containsKey(MedicMobileConstants.PHONE_NUMBER)) {
                  careGiver.setPhoneNumber((String) careGiverMap
                              .get(MedicMobileConstants.PHONE_NUMBER)[0]);
            }
            if (careGiverMap
                        .containsKey(MedicMobileConstants.PHONE_NUMBER_OF_WHOM)) {
                  careGiver.setPhoneNumberOfWhom((String) careGiverMap
                              .get(MedicMobileConstants.PHONE_NUMBER_OF_WHOM)[0]);
            }
            if (careGiverMap.containsKey(MedicMobileConstants.ID_CARD_TYPE)
                        && !("".equalsIgnoreCase((String) careGiverMap
                                    .get(MedicMobileConstants.ID_CARD_TYPE)[0]))
                        && !("not_selected"
                                    .equalsIgnoreCase((String) careGiverMap
                                                .get(MedicMobileConstants.ID_CARD_TYPE)[0]))) {
                  careGiver.setIdCardType(IDCardType.valueOf((String) careGiverMap
                              .get(MedicMobileConstants.ID_CARD_TYPE)[0]));
            }
            if (careGiverMap.containsKey(MedicMobileConstants.ID_CARD_NUMBER)) {
                careGiver.setIdCardNumber((String) careGiverMap
                            .get(MedicMobileConstants.ID_CARD_NUMBER)[0]);
          }
          if (careGiverMap.containsKey(MedicMobileConstants.ASHA_WORKER)) {
                careGiver.setAshaWorker((String) careGiverMap
                            .get(MedicMobileConstants.ASHA_WORKER)[0]);
          }
          
            mapToCareGiverHelper(careGiverMap, careGiver);
            mapToCareGiverHelperAdditional(careGiverMap, careGiver);
            mapToCareGiverAdditionalHelper(careGiverMap, careGiver);
            return careGiver;
      }
      
      private static void mapToCareGiverHelperAdditional(final Map<Object, Object[]> careGiverMap, CareGiver inCareGiver) {
          CareGiver careGiver = inCareGiver;
        if (careGiverMap
                    .containsKey(MedicMobileConstants.IMAGE_PATH_OF_CARE_GIVER)) {
              careGiver.setImagePathOfCareGiver((String) careGiverMap
                          .get(MedicMobileConstants.IMAGE_PATH_OF_CARE_GIVER)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.PARA)) {
              careGiver.setPara((String) careGiverMap
                          .get(MedicMobileConstants.PARA)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.MODE_OF_TRAVEL)) {
              careGiver.setModeOfTravel((String) careGiverMap
                          .get(MedicMobileConstants.MODE_OF_TRAVEL)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.ANGANWADI_ID)) {
              careGiver.setAnganwadiID((String) careGiverMap
                          .get(MedicMobileConstants.ANGANWADI_ID)[0]);
        }
        if (careGiverMap
                    .containsKey(MedicMobileConstants.LOCAL_VOLUNTEER_ID)) {
              careGiver.setLocalVolunteerID((String) careGiverMap
                          .get(MedicMobileConstants.LOCAL_VOLUNTEER_ID)[0]);
        } 
        if (careGiverMap.containsKey(MedicMobileConstants.NAME)) {
            careGiver.setName((String) careGiverMap
                        .get(MedicMobileConstants.NAME)[0]);
      }
      if (careGiverMap
                  .containsKey(MedicMobileConstants.CAREGIVER_NAME_IN_HINDI)) {
            careGiver.setNameInHindi((String) careGiverMap
                        .get(MedicMobileConstants.CAREGIVER_NAME_IN_HINDI)[0]);
      }
      if (careGiverMap.containsKey(MedicMobileConstants.CAREGIVER_ID)) {
            String careGiverID = (String) careGiverMap
                        .get(MedicMobileConstants.CAREGIVER_ID)[0];
            if (careGiverID.isEmpty()) {
                  careGiverID = UtilService.generateUniqueID();
            }
            careGiver.setCareGiverID(careGiverID);

      }
      }
      private static void mapToCareGiverHelper(
              final Map<Object, Object[]> careGiverMap,CareGiver careGiver) {
          if (careGiverMap.containsKey(MedicMobileConstants.STATE)) {
              careGiver.setState((String) careGiverMap
                          .get(MedicMobileConstants.STATE)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.DISTRICT)) {
              careGiver.setDistrict((String) careGiverMap
                          .get(MedicMobileConstants.DISTRICT)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.BLOCK)) {
              careGiver.setBlock((String) careGiverMap
                          .get(MedicMobileConstants.BLOCK)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.JSY_BENEFICIARY)) {
              careGiver.setJsyBeneficiary(Boolean.valueOf((String) careGiverMap
                          .get(MedicMobileConstants.JSY_BENEFICIARY)[0]));
        }
          
        if (careGiverMap.containsKey(MedicMobileConstants.ADDRESS)) {
              careGiver.setAddress((String) careGiverMap
                          .get(MedicMobileConstants.ADDRESS)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.ALIVE)) {
              careGiver.setAlive(Boolean.valueOf((String) careGiverMap
                          .get(MedicMobileConstants.ALIVE)[0]));
        }
        if (careGiverMap.containsKey(MedicMobileConstants.GENDER)) {
              careGiver.setGender(Gender.valueOf((String) careGiverMap
                          .get(MedicMobileConstants.GENDER)[0]));
        }
        if (careGiverMap.containsKey(MedicMobileConstants.DATE_OF_BIRTH)) {
              careGiver.setDateOfBirth(UtilService.stringToDate((String) careGiverMap
                          .get(MedicMobileConstants.DATE_OF_BIRTH)[0]));
        }
        if (careGiverMap.containsKey(MedicMobileConstants.PHONE_NUMBER)) {
              careGiver.setPhoneNumber((String) careGiverMap
                          .get(MedicMobileConstants.PHONE_NUMBER)[0]);
        }
      }
      
      private static void mapToCareGiverAdditionalHelper(final Map<Object, Object[]> careGiverMap,CareGiver careGiver) {
          if (careGiverMap.containsKey(MedicMobileConstants.HUSBAND_NAME)) {
              careGiver.setHusbandName((String) careGiverMap
                          .get(MedicMobileConstants.HUSBAND_NAME)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.LOCAL_VOLUNTEER)) {
              careGiver.setLocalVolunteerID((String) careGiverMap
                          .get(MedicMobileConstants.LOCAL_VOLUNTEER)[0]);
        }
          if (careGiverMap.containsKey(MedicMobileConstants.RELIGION)) {
              careGiver.setReligion((String) careGiverMap
                          .get(MedicMobileConstants.RELIGION)[0]);
        }
        if (careGiverMap.containsKey(MedicMobileConstants.VILLAGE)) {
              careGiver.setVillage(((String) careGiverMap
                          .get(MedicMobileConstants.VILLAGE)[0]));
        }
        if (careGiverMap
                    .containsKey(MedicMobileConstants.WHETHER_TO_RECEIVE_SMS)) {
              careGiver.setWhetherToReceiveSMS(Boolean.valueOf((String) careGiverMap
                          .get(MedicMobileConstants.WHETHER_TO_RECEIVE_SMS)[0]));
        }
        if (careGiverMap.containsKey(MedicMobileConstants.CASTE)) {
              careGiver.setCaste((String) careGiverMap
                          .get(MedicMobileConstants.CASTE)[0]);
        }
        if (careGiverMap
                    .containsKey(MedicMobileConstants.DISTANCE_FROM_ANGANWADI_CENTER_HRS)) {
              String distance = (String) careGiverMap
                          .get(MedicMobileConstants.DISTANCE_FROM_ANGANWADI_CENTER_HRS)[0];
              if (distance.equals("")) {
                    distance = "0";
              }
              careGiver.setDistanceFromAnganwadiCenterHrs(Integer
                          .parseInt(distance));
        }
      }
      /**
       * Map to child list.
       * 
       * @param childMap
       *              the child map
       * @param careGiverID
       *              the care giver id
       * @return the list
       */
      public static List<Child> mapToChildList(
                  final Map<Object, Object[]> childMap, final String careGiverID) {
            List<Child> childList = new ArrayList<Child>();
            int childLength = 0;
            while (childMap.containsKey(MedicMobileConstants.CHILD_BIRTH_DATE
                        + childLength)) {
                  Child child = new Child();
                  child.setCareGiverID(careGiverID);
                  if (childMap.containsKey(MedicMobileConstants.CHILD_ID
                              + childLength)) {
                        child.setChildID((String) (childMap
                                    .get(MedicMobileConstants.CHILD_ID
                                                + childLength)[0]));
                  }
                  if (childMap.containsKey(MedicMobileConstants.CHILD_BIRTH_DATE
                              + childLength)) {
                        child.setDateOfBirth(UtilService.stringToDate((String) childMap
                                    .get(MedicMobileConstants.CHILD_BIRTH_DATE
                                                + childLength)[0]));
                  }
                  if (childMap.containsKey(MedicMobileConstants.CHILD_NAME
                              + childLength)) {

                        child.setName((String) (childMap
                                    .get(MedicMobileConstants.CHILD_NAME
                                                + childLength)[0]));
                  }
                  if (childMap.containsKey(MedicMobileConstants.CHILD_NAME_IN_HINDI
                              + childLength)) {
                        child.setNameInHindi((String) childMap
                                    .get(MedicMobileConstants.CHILD_NAME_IN_HINDI
                                                + childLength)[0]);
                  }
                  if (childMap.containsKey(MedicMobileConstants.CHILD_CALENDAR_ID
                              + childLength)) {
                        child.setChildCalendarID((String) (childMap
                                    .get(MedicMobileConstants.CHILD_CALENDAR_ID
                                                + childLength)[0]));
                  }
                  if (childMap.containsKey(MedicMobileConstants.CHILD_SYSTEM_ID
                              + childLength)) {
                        child.setChildSystemId((String) (childMap
                                    .get(MedicMobileConstants.CHILD_SYSTEM_ID
                                                + childLength)[0]));
                  }
                  if (childMap.containsKey(MedicMobileConstants.ALIVE)) {
                        child.setAlive(Boolean.valueOf((String) childMap
                                    .get(MedicMobileConstants.ALIVE)[0]));
                  }
                  mapToChildHelper(childMap, childLength, child);
                  childLength++;
                  childList.add(child);
            }
            return childList;
      }
      private static void mapToChildHelper(
              final Map<Object, Object[]> childMap, int childLength, Child child) {
          if (childMap.containsKey(MedicMobileConstants.CHILD_GENDER
                  + childLength)
                  && !((String) childMap
                              .get(MedicMobileConstants.CHILD_GENDER
                                          + childLength)[0])
                              .equals("")) {
            child.setGender(Gender.valueOf((String) childMap
                        .get(MedicMobileConstants.CHILD_GENDER
                                    + childLength)[0]));
      }
      if (childMap.containsKey(MedicMobileConstants.CHILD_MCTS_ID
                  + childLength)) {
            child.setMctsID((String) childMap
                        .get(MedicMobileConstants.CHILD_MCTS_ID
                                    + childLength)[0]);
      }
      if (childMap.containsKey(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY
                  + childLength)) {
            child.setPlaceOfDelivery((String) (childMap
                        .get(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY
                                    + childLength)[0]));
      }
      if (childMap.containsKey(MedicMobileConstants.CHILD_WEIGHT
                  + childLength)) {
      }
      if (childMap.containsKey(MedicMobileConstants.CHILD_HEIGHT
                  + childLength)) {
      }
      if (childMap.containsKey(MedicMobileConstants.CHILD_BLOOD_GROUP
                  + childLength)
                  && !((String) childMap
                              .get(MedicMobileConstants.CHILD_BLOOD_GROUP
                                          + childLength)[0])
                              .equals("")) {
            child.setBloodGroup(BloodGroup.valueOf((String) childMap
                        .get(MedicMobileConstants.CHILD_BLOOD_GROUP
                                    + childLength)[0]));
      }
      }
      /**
       * Map to child data.
       * 
       * @param childMap
       *              the child map
       * @return the child
       */
      public static Child mapToChildData(final Map<Object, Object[]> childMap) {
            Child child = new Child();
            if (childMap.containsKey(MedicMobileConstants.CHILD_SYSTEM_ID)) {
                  child.setChildSystemId((String) (childMap
                              .get(MedicMobileConstants.CHILD_SYSTEM_ID)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.ALIVE)) {
                  child.setAlive(Boolean.valueOf((String) childMap
                              .get(MedicMobileConstants.ALIVE)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_GENDER)) {
                  child.setGender(Gender.valueOf((String) childMap
                              .get(MedicMobileConstants.CHILD_GENDER)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_MCTS_ID)) {
                  child.setMctsID((String) childMap
                              .get(MedicMobileConstants.CHILD_MCTS_ID)[0]);
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY)) {
                  child.setPlaceOfDelivery((String) (childMap
                              .get(MedicMobileConstants.CHILD_PLACE_OF_DELIVERY)[0]));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_WEIGHT)) {
                  child.setWeight(Float.parseFloat((String) (childMap
                              .get(MedicMobileConstants.CHILD_WEIGHT)[0])));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_HEIGHT)) {
                  child.setHeight(Float.parseFloat((String) (childMap
                              .get(MedicMobileConstants.CHILD_HEIGHT)[0])));
            }
            if (childMap.containsKey(MedicMobileConstants.CHILD_BLOOD_GROUP)
                        && !((String) childMap
                                    .get(MedicMobileConstants.CHILD_BLOOD_GROUP)[0])
                                    .equals("")) {
                  child.setBloodGroup(BloodGroup.valueOf((String) childMap
                              .get(MedicMobileConstants.CHILD_BLOOD_GROUP)[0]));
            }
            mapToChildDataHelper(childMap, child);
            return child;
      }
      
      private static void mapToChildDataHelper(final Map<Object, Object[]> childMap, Child child) {
          if (childMap.containsKey(MedicMobileConstants.CHILD_CALENDAR_ID)) {
              child.setChildCalendarID((String) childMap
                          .get(MedicMobileConstants.CHILD_BLOOD_GROUP)[0]);
        }
          if (childMap.containsKey(MedicMobileConstants.CAREGIVER_ID)) {
              child.setCareGiverID((String) (childMap
                          .get(MedicMobileConstants.CHILD_ID)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_ID)) {
              child.setChildID((String) (childMap
                          .get(MedicMobileConstants.CHILD_ID)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_BIRTH_DATE)) {
              child.setDateOfBirth(UtilService.stringToDate((String) childMap
                          .get(MedicMobileConstants.CHILD_BIRTH_DATE)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CHILD_NAME)) {

              child.setName((String) (childMap
                          .get(MedicMobileConstants.CHILD_NAME)[0]));
        }
        if (childMap.containsKey(MedicMobileConstants.CALENDAR_ID)) {

              child.setChildCalendarID((String) (childMap
                          .get(MedicMobileConstants.CALENDAR_ID)[0]));
        }
      }

      /**
       * To schedule object.
       * 
       * @param visits
       *              the visits
       * @return the list
       */
      public static List<ScheduleObject> toScheduleObject(
                  final List<VisitResponse> visits) {
            List<ScheduleObject> schedule = new ArrayList<ScheduleObject>();
            for (VisitResponse response : visits) {
                  ScheduleObject object = new ScheduleObject();
                  object.setName(response.getName());
                  object.setOriginalAppointmentDueDate(response
                              .getOriginalAppointmentDueDate().toDate());
                  if (response.getVisitDate() != null) {
                        object.setVisitDate(response.getVisitDate().toDate());
                  }
                  object.setMissed(response.isMissed());
                  schedule.add(object);
            }
            return schedule;
      }
}