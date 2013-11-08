package org.medicmobile.service;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.medicmobile.dao.DetailedPersonRepository;

// : Auto-generated Javadoc
/**
 * The Class DetailedPersonService.
 */
public class DetailedPersonService {

      /**
       * Instantiates a new detailed person service.
       */
      public DetailedPersonService() {
            DetailedPersonRepository repo;
            HttpClient httpClient = new StdHttpClient.Builder().build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
            // if the second parameter is true, the database will be created if
            // it
            // doesn't exists
            CouchDbConnector db = dbInstance.createConnector(
                        "my_first_database", true);
            repo = new DetailedPersonRepository(db);
            repo.toString();
      }

}
