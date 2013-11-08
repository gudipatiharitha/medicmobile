package org.medicmobile.sms;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

/**
 * The Class HttpParams.
 */
@Service(value = "getParams")
public class HttpParams {

      /** The Constant API_KEY. */
      private static final String API_KEY = " KK9d9fe27776831cee157df864f90a2a9f";

      /** The Constant URL_KOOKOO. */
      private static final String URL_KOOKOO = "http://www.kookoo.in/outbound/outbound.php?";

      /** The Constant OUTBOUND_VERSION. */
      private static final String OUTBOUND_VERSION = "2";

      /**
       * Gets the params.
       * 
       * @param recipient
       *              the recipient
       * @param message
       *              the message
       * @return the params
       */
      public HttpGet getParams(String recipient, String message) {
            HttpGet test;
            try {
                  URI url = new URI(urlBuilder(recipient, message));
                  test = new HttpGet(url);
                  return test;
            } catch (Exception ex) {
                  Logger.getLogger(HttpParams.class.getName()).log(
                              Level.SEVERE, null, ex);
                  return null;
            }
      }

      /**
       * Url builder.
       * 
       * @param recipient
       *              the recipient
       * @param message
       *              the message
       * @return the string
       */
      public String urlBuilder(String recipient, String message) {
            String sb = URL_KOOKOO + "phone_no=" + recipient + "&api_key="
                        + API_KEY + "&outbound_version=" + OUTBOUND_VERSION
                        + "&message=" + message;
            return sb;

      }
}
