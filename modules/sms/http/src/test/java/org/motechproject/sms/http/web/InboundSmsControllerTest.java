package org.motechproject.sms.http.web;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.EventRelay;
import org.motechproject.server.config.SettingsFacade;
import org.motechproject.sms.api.constants.EventDataKeys;
import org.motechproject.sms.api.constants.EventSubjects;
import org.motechproject.sms.http.TemplateReader;
import org.motechproject.sms.http.repository.AllInboundSMS;
import org.motechproject.sms.http.template.Incoming;
import org.motechproject.sms.http.template.SmsHttpTemplate;

public class InboundSmsControllerTest {


    @Mock
    private EventRelay eventRelay;
    @Mock
    private TemplateReader templateReader;
    @Mock
    private SettingsFacade settings;
    @Mock
    private AllInboundSMS inboundSms;

    @Before
    public void setup() {
        initMocks(this);
        when(settings.getProperty("sms.details.save")).thenReturn("false");
        when(settings.getProperty("sms.timestamp.pattern")).thenReturn("dd MMM yyyy");
    }

    @Test
    public void handleIncomingSms() {
        SmsHttpTemplate template = new SmsHttpTemplate();
        Incoming incoming = new Incoming();
        incoming.setMessageKey("message");
        incoming.setSenderKey("sender");
        incoming.setTimestampKey("timestamp");
        template.setIncoming(incoming);
        when(templateReader.getTemplate()).thenReturn(template);

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getParameter(template.getIncoming().getMessageKey())).thenReturn("some text message");
        when(httpServletRequest.getParameter(template.getIncoming().getSenderKey())).thenReturn("1234567890");
        when(httpServletRequest.getParameter(template.getIncoming().getTimestampKey())).thenReturn("22 Jul 2013");

        new InboundSmsController(templateReader, eventRelay,inboundSms,settings).handle(httpServletRequest);

        ArgumentCaptor<MotechEvent> eventCaptor = ArgumentCaptor.forClass(MotechEvent.class);
        verify(eventRelay).sendEventMessage(eventCaptor.capture());

        MotechEvent event = eventCaptor.getValue();
        assertEquals(EventSubjects.INBOUND_SMS, event.getSubject());
        assertEquals("some text message", event.getParameters().get(EventDataKeys.INBOUND_MESSAGE));
        assertEquals("1234567890", event.getParameters().get(EventDataKeys.SENDER));
        assertEquals("22 Jul 2013", event.getParameters().get(EventDataKeys.TIMESTAMP));
    }

    @Test
    public void timestampIsOptionalForIncomingMessage() {
        SmsHttpTemplate template = new SmsHttpTemplate();
        Incoming incoming = new Incoming();
        incoming.setMessageKey("message");
        incoming.setSenderKey("sender");
        template.setIncoming(incoming);
        when(templateReader.getTemplate()).thenReturn(template);

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getParameter(template.getIncoming().getMessageKey())).thenReturn("some text message");
        when(httpServletRequest.getParameter(template.getIncoming().getSenderKey())).thenReturn("1234567890");

        new InboundSmsController(templateReader, eventRelay,inboundSms,settings).handle(httpServletRequest);

        ArgumentCaptor<MotechEvent> eventCaptor = ArgumentCaptor.forClass(MotechEvent.class);
        verify(eventRelay).sendEventMessage(eventCaptor.capture());

        MotechEvent event = eventCaptor.getValue();
        assertEquals(null, event.getParameters().get(EventDataKeys.TIMESTAMP));
    }
}
