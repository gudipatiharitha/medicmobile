package org.medicmobile.util;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.medicmobile.dto.WeeklyReportAccordian;
import org.medicmobile.dto.WeeklyReportRow;

// : Auto-generated Javadoc
/**
 * The Class HindiTransliteratorHelper.
 */
public final class HindiTransliteratorHelper {
      
      /**
       * Instantiates a new hindi transliterator helper.
       */
      private HindiTransliteratorHelper() {
            
      }

      /** The Constant jan. */
      public static final String JAN = "जनवरी";

      /** The Constant feb. */
      public static final String FEB = "फ़रवरी";

      /** The Constant mar. */
      public static final String MAR = "मार्च";

      /** The Constant apr. */
      public static final String APR = "अप्रैल";

      /** The Constant may. */
      public static final String MAY = "मई";

      /** The Constant jun. */
      public static final String JUN = "जून";

      /** The Constant jul. */
      public static final String JUL = "जुलाई";

      /** The Constant aug. */
      public static final String AUG = "अगस्त";

      /** The Constant sep. */
      public static final String SEP = "सितंबर";

      /** The Constant oct. */
      public static final String OCT = "अक्टूबर";

      /** The Constant nov. */
      public static final String NOV = "नवंबर";

      /** The Constant dec. */
      public static final String DEC = "दिसम्बर";

      /** The Constant day. */
      public static final String DAY = "दिन";

      /** The Constant monthStringHindi. */
      public static final String MONTHSTRINGHINDI = "माह";

      /** The Constant yearStringHindi. */
      public static final String YEARSTRINGHINDI = "वर्ष";

      /** The Constant monthArray. */
      public static final String[] MONTHARRAY = { JAN, FEB, MAR, APR, MAY, JUN,
                  JUL, AUG, SEP, NOV, DEC };

      /** The Constant dayArray. */
      public static final String[] DAYARRAY = { "सोमवार", "मंगलवार", "बुधवार",
                  "बृहस्पतिवार", "शुक्रवार", "शनिवार", "रविवार" };

      /** The Constant doseInHindi. */
      public static final String DOSEINHINDI = "खुराक";


      /**
       * Gets the date in hindi.
       *
       * @param dateTime the date time
       * @return the date in hindi
       */
      public static String getDateInHindi(DateTime dateTime) {
            int year = dateTime.getYear();

            int month = dateTime.getMonthOfYear() - 1;

            int dayInWeek = dateTime.getDayOfWeek() - 1;

            String dayString = DAYARRAY[dayInWeek] + "";

            String monthString = MONTHARRAY[month];

            String yearString = year + "";

            String dateString = dayString + "-" + monthString + "-"
                        + yearString;

            // byte[] dateByteString =
            // dateString.getBytes(Charset.forName("UTF-8"));

            // return dateByteString.toString();
            return dateString;
      }

      /** The vaccine name hindi map. */
      public static final Map<String, String> VACCINENAMEINHINDIMAP = new HashMap<String, String>() {
            
            /** The Constant serialVersionUID. */
            private static final long serialVersionUID = 1L;

            {
                  put("OPV", "पोलियो");
                  put("DPT", "डी पी टी");
                  put("MEASLES", "खसरा");
                  put("VITA", "विटामिन - ए");
                  put("BCG", "बी सी जी");
                  put("MMR", "खसरा, कण्ठमाला का रोग और रूबेला");
                  put("HEPB", "हेपेटाइटिस बी");
                  put("DT", "डिप्थीरिया, टेटनस");
            }
      };

      /**
       * Vaccine name and dosage to hindi.
       *
       * @param vaccineName the vaccine name
       * @return the string
       */
      public static String vaccineNameAndDosageToHindi(String vaccineName) {
            int lengthOfVaccineName = vaccineName.length();
            String vaccineNameInHindi = "";
            String numberInHindiString = "";
            vaccineNameInHindi = VACCINENAMEINHINDIMAP.get(vaccineName.substring(
                        0, lengthOfVaccineName - 1)) + " का ";
            try {
                  if (vaccineName.startsWith("DPT")
                              || vaccineName.startsWith("DT")
                              || vaccineName.startsWith("VIT")) {
                        numberInHindiString = NUMBERINHINDI[Integer
                                    .parseInt((String) vaccineName.subSequence(
                                                lengthOfVaccineName - 1,
                                                lengthOfVaccineName)) - 1];
                  } else {
                        numberInHindiString = NUMBERINHINDI[Integer
                                    .parseInt((String) vaccineName.subSequence(
                                                lengthOfVaccineName - 1,
                                                lengthOfVaccineName))];
                  }
                  vaccineNameInHindi += numberInHindiString + " " + DOSEINHINDI;
            } catch (NumberFormatException exception) {
                  vaccineNameInHindi = VACCINENAMEINHINDIMAP.get(vaccineName
                              .substring(0, lengthOfVaccineName));
            }
            return vaccineNameInHindi;
      }

      /** The Constant numberInHindi. */
      public static final String[] NUMBERINHINDI = { "पहले", "दूसरा", "तृतीय",
                  "चौथा", "पांचवां", "छठवां", "सातवां", "आठवाँ" };


      /**
       * Gets the bulk vaccine name for child in hindi.
       *
       * @param accordion the accordion
       * @return the bulk vaccine name for child in hindi
       */
      public static Map<String, String> getBulkVaccineNameForChildInHindi(
                  WeeklyReportAccordian accordion) {
            String childId = null;
            Map<String, String> childVaccineMap = new HashMap<String, String>();
            for (WeeklyReportRow row : accordion.getRows()) {
                  if (row.getAdministeredDate() == null) {
                        childId = row.getChildID();
                        if (childVaccineMap.containsKey(childId)) {
                              childVaccineMap
                                          .put(childId,
                                                      childVaccineMap
                                                                  .get(childId)
                                                                  + ", "
                                                                  + vaccineNameAndDosageToHindi(row
                                                                              .getVaccineName()));
                        } else {
                              childVaccineMap.put(childId,
                                          vaccineNameAndDosageToHindi(row
                                                      .getVaccineName()));
                        }
                  }
            }
            return childVaccineMap;
      }
}
