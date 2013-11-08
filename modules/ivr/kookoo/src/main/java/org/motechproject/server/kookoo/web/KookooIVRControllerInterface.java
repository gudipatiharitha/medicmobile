package org.motechproject.server.kookoo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface KookooIVRControllerInterface {

      ModelAndView ivrCallback(HttpServletRequest request, HttpServletResponse response);

}
