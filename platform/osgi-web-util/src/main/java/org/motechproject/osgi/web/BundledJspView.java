package org.motechproject.osgi.web;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.motechproject.osgi.web.exception.RenderException;
import org.osgi.framework.BundleContext;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BundledJspView extends JstlView implements BundleContextAware {

    private static final String JSP_FILE = "org.apache.catalina.jsp_file";

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws RenderException {
        request.setAttribute(JSP_FILE, "/" + bundleContext.getBundle().getBundleId() + getUrl());
        try {
            super.render(model, request, response);
        } catch (Exception e) {
            throw new RenderException(e);
        }
    }
}
