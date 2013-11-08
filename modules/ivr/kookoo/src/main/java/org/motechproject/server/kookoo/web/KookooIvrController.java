package org.motechproject.server.kookoo.web;

import static java.util.Arrays.asList;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.motechproject.decisiontree.core.FlowSession;
import org.motechproject.decisiontree.core.model.CallStatus;
import org.motechproject.decisiontree.core.model.DialStatus;
import org.motechproject.decisiontree.server.service.DecisionTreeServer;
import org.motechproject.decisiontree.server.service.FlowSessionService;
import org.motechproject.ivr.service.SessionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequestMapping("/kookoo")
public class KookooIvrController implements KookooIVRControllerInterface {
    
    private Logger logger = LoggerFactory.getLogger((this.getClass()));

    private DecisionTreeServer decisionTreeServer;
    private FlowSessionService flowSessionService;

    @Autowired
      public KookooIvrController(DecisionTreeServer decisionTreeServer,
                  FlowSessionService flowSessionService) {
        this.decisionTreeServer = decisionTreeServer;
        this.flowSessionService = flowSessionService;
    }

      /* (non-Javadoc)
       * @see org.motechproject.server.kookoo.web.KookooIVRControllerInterface#ivrCallback(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
       */
      @Override
      public ModelAndView ivrCallback(HttpServletRequest request,
                  HttpServletResponse response) {
        String motechCallId = request.getParameter("motech_call_id");
        String kookooSid = request.getParameter("sid");
        String phoneNumber = request.getParameter("cid");
            String tree = request.getParameter("tree");
        FlowSession session = null;
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            boolean isRecorded = false;
        if (motechCallId == null) {
                  session = flowSessionService.findOrCreate(kookooSid,
                              phoneNumber);
        } else {
                  session = updateOutgoingCallSessionIdWithKookooSid(
                              motechCallId, kookooSid);
        }
            String language = request.getParameter("ln");
        String transitionKey = null;
        String event = request.getParameter("event");
            if ("NewCall".equals(event)) {
                  motechCallId = UUID.randomUUID().toString();
                  language = "en";
                  if (tree == null || tree.length() == 0) {
                        tree = (String)request.getAttribute("tree");
                  }
            } else if ("GotDTMF".equals(event)) {
            transitionKey = request.getParameter("data");
        } else if ("Hangup".equals(event)) {
            transitionKey = CallStatus.Hangup.toString();
        } else if ("Disconnect".equals(event)) {
            transitionKey = CallStatus.Disconnect.toString();
        } else if ("Dial".equals(event)) {
            transitionKey = getDialStatus(request);
            } else if ("Record".equalsIgnoreCase(event)) {
                isRecorded = true;
                transitionKey = request.getParameter("data");
        }
        session.setLanguage(language);
        session = setCustomParams(session, request);
        flowSessionService.updateSession(session);
            ModelAndView view = decisionTreeServer.getResponse(kookooSid,
                        phoneNumber, "kookoo", tree, transitionKey, language, isRecorded);
        view.addObject("contextPath", request.getContextPath());
        view.addObject("servletPath", request.getServletPath());
        view.addObject("host", request.getHeader("Host"));
        view.addObject("scheme", request.getScheme());
        return view;
           /* String output = this.getOutPutString(view);
            return output;*/
    }

    @RequestMapping("/status")
      public String statusCallback(HttpServletRequest request,
                  HttpServletResponse response) {
        return "";
    }

    @RequestMapping(value = "/ivr/callstatus", method = RequestMethod.POST)
    public void handleMissedCall(HttpServletRequest request) {
        String status = request.getParameter("status_details");
            if (status == null || status.trim().isEmpty()
                        || !status.equals("NoAnswer")) {
            return;
        }
        String motechCallId = request.getParameter("motech_call_id");
        FlowSession session = flowSessionService.getSession(motechCallId);
        if (session == null) {
                  throw new SessionNotFoundException(
                              "No session found! [Session Id " + motechCallId
                                          + "]");
        }
        String kookooSid = request.getParameter("sid");
            session = flowSessionService.updateSessionId(motechCallId,
                        kookooSid);
        decisionTreeServer.handleMissedCall(session.getSessionId());
    }

      private FlowSession setCustomParams(FlowSession session,
                  HttpServletRequest request) {
        Map params = request.getParameterMap();
        Set<String> keys = params.keySet();
        for (String key : keys) {
                  if (!asList("sid", "cid", "called_number", "event", "data",
                              "duration", "status", "tree", "ln").contains(key)) {
                session.set(key, (Serializable) params.get(key));
            }
        }
        return session;
    }

      private FlowSession updateOutgoingCallSessionIdWithKookooSid(
                  String callId, String kookooSid) {
        FlowSession flowSession = flowSessionService.getSession(callId);
            return flowSessionService.updateSessionId(
                        flowSession.getSessionId(), kookooSid);
    }

    private String getDialStatus(HttpServletRequest request) {
        String status = request.getParameter("status");
        if ("answered".equals(status)) {
            return DialStatus.completed.toString();
        } else if ("not_answered".equals(status)) {
            return DialStatus.noAnswer.toString();
        }
        return status;
    }

      public String getOutPutString(ModelAndView view) {
            try {
                  VelocityContext context = new VelocityContext();
                  context.put("filename", new Random().nextInt());
                  Map<String, Object> map = view.getModel();
                  if (map != null) {
                        Iterator<Entry<String, Object>> iter = map.entrySet()
                                    .iterator();
                        while (iter.hasNext()) {
                              Entry<String, Object> curr = iter.next();
                              context.put(curr.getKey(), curr.getValue());
                        }
                  }
                  InputStream s = KookooIvrController.class
                              .getResourceAsStream(view.getViewName() + ".vm");

                  StringWriter writer = new StringWriter();
                  IOUtils.copy(s, writer, "UTF-8");
                  String vmContent = writer.toString();

                  Writer outwriter = new StringWriter();
                  String logC = "";
                  Velocity.evaluate(context, outwriter, logC,
                              vmContent.toString());
                  return outwriter.toString();
            } catch (ResourceNotFoundException e) {
                logger.debug(e.getMessage());
            } catch (ParseErrorException e) {
                logger.debug(e.getMessage());
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
            return "";
}
}
