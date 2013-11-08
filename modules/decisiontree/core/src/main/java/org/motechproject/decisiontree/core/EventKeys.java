package org.motechproject.decisiontree.core;

import org.motechproject.event.MotechEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EventKeys {

    private EventKeys() {
        // static utility class
    }

    public static final String END_OF_CALL_EVENT = "end_of_call";
    public static final String CALL_DETAIL_RECORD_PARAM = "call_detail_record";
      public static final String PHONE_KEY = "PhoneNumber";
      private static final Logger LOG = LoggerFactory
                  .getLogger("org.motechproject.decisiontree.core");
      public static final String BASE_SUBJECT = "org.motechproject.decisiontree.core.";
      public static final String RECORDED_AUDIO_IS_READY = "recordisready";
      public static final String RECORDED_URL = BASE_SUBJECT + "recordedURL";
      public static final String RECORDED_AUDIO = "recorded-audio";

      public static String getStringValue(MotechEvent event, String key) {
            String ret = null;
            try {
                  ret = (String) event.getParameters().get(key);
            } catch (ClassCastException e) {
                  LOG.warn("Event: " + event + " Key: " + key
                              + " is not a String");
            }

            return ret;
      }

      public static String getRecordedURL(MotechEvent event) {
            return getStringValue(event, EventKeys.RECORDED_URL);
      }

      public static String getPhoneNumber(MotechEvent event) {
            return getStringValue(event, EventKeys.PHONE_KEY);
      }
}
