package org.medicmobile.bundle;

import org.motechproject.osgi.web.ModuleRegistrationData;
import org.motechproject.osgi.web.MotechOsgiWebApplicationContext;
import org.motechproject.osgi.web.ServletRegistrationException;
import org.motechproject.osgi.web.UIFrameworkService;
import org.motechproject.osgi.web.UiHttpContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * The Class Activator.
 */
public class Activator implements BundleActivator {

      /** The logger. */
      private static Logger logger = LoggerFactory.getLogger(Activator.class);

      /** The Constant CONTEXT_CONFIG_LOCATION. */
      private static final String CONTEXT_CONFIG_LOCATION = "META-INF/spring/blueprint.xml";

      /** The Constant SERVLET_URL_MAPPING. */
      private static final String SERVLET_URL_MAPPING = "/medicmobile/api";

      /** The Constant RESOURCE_URL_MAPPING. */
      private static final String RESOURCE_URL_MAPPING = "/medicmobile";

      /** The Constant MODULE_NAME. */
      private static final String MODULE_NAME = "medicmobile";

      /** The http service tracker. */
      private ServiceTracker httpServiceTracker;

      /** The ui service tracker. */
      private ServiceTracker uiServiceTracker;

      /** The bundle context. */
      private static BundleContext bundleContext;

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
       * )
       */
      @Override
      public void start(final BundleContext context) {
            bundleContext = context;

            this.httpServiceTracker = new ServiceTracker(context,
                        HttpService.class.getName(), null) {

                  @Override
                  public Object addingService(final ServiceReference ref) {
                        Object service = super.addingService(ref);
                        serviceAdded((HttpService) service);
                        return service;
                  }

                  @Override
                  public void removedService(final ServiceReference ref,
                              Object service) {
                        serviceRemoved((HttpService) service);
                        super.removedService(ref, service);
                  }
            };
            this.httpServiceTracker.open();

            this.uiServiceTracker = new ServiceTracker(context,
                        UIFrameworkService.class.getName(), null) {

                  @Override
                  public Object addingService(final ServiceReference ref) {
                        Object service = super.addingService(ref);
                        serviceAdded((UIFrameworkService) service);
                        return service;
                  }

                  @Override
                  public void removedService(final ServiceReference ref,
                              Object service) {
                        serviceRemoved((UIFrameworkService) service);
                        super.removedService(ref, service);
                  }
            };
            this.uiServiceTracker.open();

            ServiceReference httpServiceReference = context
                        .getServiceReference(HttpService.class.getName());
            if (httpServiceReference != null) {
                  serviceAdded((HttpService) context
                              .getService(httpServiceReference));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext
       * )
       */
      public void stop(final BundleContext context) {
            this.httpServiceTracker.close();
            this.uiServiceTracker.close();
      }

      /**
       * The Class MedicMobileApplicationContext.
       */
      public static class MedicMobileApplicationContext extends
                  MotechOsgiWebApplicationContext {

            /**
             * Instantiates a new medic mobile application context.
             */
            public MedicMobileApplicationContext() {
                  super();
                  setBundleContext(Activator.bundleContext);
            }

      }

      /**
       * Service added.
       * 
       * @param service
       *              the service
       */
      private void serviceAdded(final HttpService service) {
            try {
                  DispatcherServlet dispatcherServlet = new DispatcherServlet();
                  dispatcherServlet
                              .setContextConfigLocation(CONTEXT_CONFIG_LOCATION);
                  dispatcherServlet
                              .setContextClass(MedicMobileApplicationContext.class);
                  ClassLoader old = Thread.currentThread()
                              .getContextClassLoader();

                  try {
                        Thread.currentThread().setContextClassLoader(
                                    getClass().getClassLoader());
                        UiHttpContext httpContext = new UiHttpContext(
                                    service.createDefaultHttpContext());

                        serviceRemoved(service);

                        service.registerServlet(SERVLET_URL_MAPPING,
                                    dispatcherServlet, null, httpContext);
                        service.registerResources(RESOURCE_URL_MAPPING,
                                    "/webapp", httpContext);
                        logger.debug("Servlet registered");
                  } finally {
                        Thread.currentThread().setContextClassLoader(old);
                  }
            } catch (Exception e) {
                  throw new ServletRegistrationException(e);
            }
      }

      /**
       * Service removed.
       * 
       * @param service
       *              the service
       */
      private void serviceRemoved(final HttpService service) {
            service.unregister(SERVLET_URL_MAPPING);
            service.unregister(RESOURCE_URL_MAPPING);
            logger.debug("Servlet unregistered");
      }

      /**
       * Service added.
       * 
       * @param service
       *              the service
       */
      private void serviceAdded(final UIFrameworkService service) {
            ModuleRegistrationData regData = new ModuleRegistrationData();
            regData.setModuleName(MODULE_NAME);
            regData.setUrl("../medicmobile/");
            service.registerModule(regData);
            logger.debug("Medicmobile registered in UI framework");
      }

      /**
       * Service removed.
       * 
       * @param service
       *              the service
       */
      private void serviceRemoved(final UIFrameworkService service) {
            service.unregisterModule(MODULE_NAME);
            logger.debug("Medicmobile unregistered from ui framework");
      }
}
