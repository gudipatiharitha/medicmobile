package org.motechproject.decisiontree.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.motechproject.decisiontree.core.FlowSession;
import org.motechproject.decisiontree.server.domain.FlowSessionRecord;
import org.motechproject.decisiontree.server.repository.AllFlowSessionRecords;
import org.motechproject.decisiontree.server.service.FlowSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Service("flowSessionService")
public class FlowSessionServiceImpl implements FlowSessionService {

    private AllFlowSessionRecords allFlowSessionRecords;

    @Autowired
    public FlowSessionServiceImpl(AllFlowSessionRecords allFlowSessionRecords) {
        this.allFlowSessionRecords = allFlowSessionRecords;
    }

    @Override
    public FlowSession findOrCreate(String sessionId, String phoneNumber) {
        String flowSessionId = (StringUtils.isBlank(sessionId)) ? UUID.randomUUID().toString() : sessionId;
        return allFlowSessionRecords.findOrCreate(flowSessionId, phoneNumber);
    }

    @ResponseBody
    @Override
    public FlowSession getSession(String sessionId) {
        return allFlowSessionRecords.findBySessionId(sessionId);
    }

    @Override
    public void updateSession(FlowSession flowSession) {
        allFlowSessionRecords.update((FlowSessionRecord) flowSession);
    }

    @Override
    public void removeCallSession(String sessionId) {
        FlowSessionRecord flowSessionRecord = allFlowSessionRecords.findBySessionId(sessionId);
        if (flowSessionRecord != null) {
            allFlowSessionRecords.remove(flowSessionRecord);
        }
    }

    @Override
    public boolean isValidSession(String sessionId) {
        return allFlowSessionRecords.findBySessionId(sessionId) != null;
    }

    @Override
    public FlowSession updateSessionId(String sessionId, String newSessionId) {
        FlowSessionRecord flowSession = allFlowSessionRecords.findBySessionId(sessionId);
        flowSession.setSessionId(newSessionId);
        allFlowSessionRecords.update(flowSession);
        return flowSession;
    }

    @Override
    public FlowSession getSession(HttpServletRequest request, String language, String phoneNumber) {
        String sessionId = request.getParameter(FLOW_SESSION_ID_PARAM);
        return copyParameters(request, findOrCreate(sessionId, phoneNumber));
    }

    // TODO: session should not have provider specific params
    private FlowSession copyParameters(HttpServletRequest request, FlowSession session) {
        Map parameters = request.getParameterMap();
        for (Object key : parameters.keySet()) {
            session.set(key.toString(), ((String[]) parameters.get(key))[0]);
        }
        return session;
    }
}
