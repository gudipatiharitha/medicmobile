package org.medicmobile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.medicmobile.dto.WeeklyReportAccordian;
import org.medicmobile.dto.WeeklyReportRow;
import org.medicmobile.model.Role;

/**
 * The Class UtilService.
 */
public final class UtilService {

      /**
       * Instantiates a new util service.
       */
      private UtilService() {
      }

      /**
       * Generate unique id.
       * 
       * @return the string
       */
      public static String generateUniqueID() {
            return UUID.randomUUID().toString();
            /*
             * Random random = new Random(); return "_id" + random.nextInt();
             */
      }

      /**
       * Generate child id.
       *
       * @return the string
       */
      public static String generateChildId() {
            return UUID.randomUUID().toString();
            /*
             * //Random random = new Random(); int randomNum = 10000001 +
             * (int)(Math.random()*99999999); return randomNum+"";
             */
      }

      /**
       * String to date.
       * 
       * @param dateString
       *              the date string
       * @return the date
       */
      public static Date stringToDate(String dateString) {
            Date date;
            if (!dateString.equalsIgnoreCase("")) {
                  try {
                        date = new SimpleDateFormat("dd MMM yyyy")
                                    .parse(dateString);
                        return date;
                  } catch (ParseException e) {

                  }
            }
            return null;
      }

      /**
       * Gets the roles as map.
       * 
       * @return the roles as map
       */
      public static Map<Role, String> getRolesAsMap() {
            Map<Role, String> rolesMap = new HashMap<Role, String>();
            for (Role role : Role.values()) {
                  rolesMap.put(role, role.getValue());
            }
            return rolesMap;
      }

      /**
       * Recorded file name.
       * 
       * @param recordedURL
       *              the recorded url
       * @return the string
       */
      public static String recordedFileName(String recordedURL) {
            String pattern = "/(\\d+).wav";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(recordedURL);
            if (m.find()) {
                  return m.group(1);
            }
            return "";
      }

      /**
       * Gets the child care giver i ds.
       *
       * @param accordion the accordion
       * @return the child care giver i ds
       */
      public static Map<String, String> getChildCareGiverIDs(
                  WeeklyReportAccordian accordion) {
            Map<String, String> childCareGiverMap = new HashMap<String, String>();
            for (WeeklyReportRow row : accordion.getRows()) {
                  if (row.getAdministeredDate() == null) {
                        childCareGiverMap.put(row.getChildID(),
                                    row.getCareGiverID());
                  }
            }
            return childCareGiverMap;
      }

      /**
       * Gets the child id child name in hindi.
       *
       * @param accordion the accordion
       * @return the child id child name in hindi
       */
      public static Map<String, String> getChildIdChildNameInHindi(
                  WeeklyReportAccordian accordion) {
            Map<String, String> childCareGiverMap = new HashMap<String, String>();
            for (WeeklyReportRow row : accordion.getRows()) {
                  if (row.getAdministeredDate() == null) {
                        childCareGiverMap.put(row.getChildID(),
                                    row.getChildNameInHindi());
                  }
            }
            return childCareGiverMap;
      }

      /**
       * Gets the care giver id care giver name in hindi.
       *
       * @param accordion the accordion
       * @return the care giver id care giver name in hindi
       */
      public static Map<String, String> getCareGiverIdCareGiverNameInHindi(
                  WeeklyReportAccordian accordion) {
            Map<String, String> childCareGiverMap = new HashMap<String, String>();
            for (WeeklyReportRow row : accordion.getRows()) {
                  if (row.getAdministeredDate() == null) {
                        childCareGiverMap.put(row.getCareGiverID(),
                                    row.getCareGiverNameInHindi());
                  }
            }
            return childCareGiverMap;
      }

}
