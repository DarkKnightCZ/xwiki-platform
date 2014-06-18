/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.mail.internal;

import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeBodyPart;

import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.util.DefaultParameterizedType;
import org.xwiki.mail.MimeBodyPartFactory;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.test.mockito.MockitoComponentMockingRule;
import org.xwiki.velocity.VelocityEngine;
import org.xwiki.velocity.VelocityManager;

import com.xpn.xwiki.api.Attachment;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TemplateMimeBodyPartFactory}.
 *
 * @version $Id$
 * @since 6.1RC1
 */
public class TemplateMimeBodyPartFactoryTest
{
    private DocumentReference documentReference = mock(DocumentReference.class);
    private Map<String, String> data = mock(HashMap.class);
    private VelocityEngine velocityEngine = mock(VelocityEngine.class);

    @Rule
    public MockitoComponentMockingRule<TemplateMimeBodyPartFactory> mocker =
            new MockitoComponentMockingRule<>(TemplateMimeBodyPartFactory.class);

    @Before
    public void setUp() throws Exception
    {
        // Mocking DocumentAccessBridge
        DocumentAccessBridge documentBridge = this.mocker.getInstance(DocumentAccessBridge.class);
        when(documentBridge.getProperty(same(documentReference), any(DocumentReference.class), eq("text"))).thenReturn(
                "Hello ${name}, ${email}");
        when(documentBridge.getProperty(same(documentReference), any(DocumentReference.class), eq("html"))).thenReturn(
                "Hello <b>${name}</b> <br />${email}");
        VelocityManager velocityManager = this.mocker.getInstance(VelocityManager.class);
        when(velocityManager.getVelocityEngine()).thenReturn(velocityEngine);

        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                Object[] args = invocation.getArguments();
                switch (((String) args[3])) {
                    case "Hello ${name}, ${email}":
                        ((Writer) args[1]).write("Hello John Doe, john@doe.com");
                        break;
                    case "Hello <b>${name}</b> <br />${email}":
                        ((Writer) args[1]).write("Hello <b>John Doe</b> <br />john@doe.com");
                        break;
                }
                return null;
            }
        }).when(velocityEngine).evaluate(any(VelocityContext.class), any(Writer.class),
                anyString(), anyString());
    }

    @Test
    public void createWithoutAttachment() throws Exception
    {
        MimeBodyPartFactory<String> htmlMimeBodyPartFactory = this.mocker.getInstance(
                new DefaultParameterizedType(null, MimeBodyPartFactory.class, String.class), "text/html");

        MimeBodyPart bodyPart = this.mocker.getComponentUnderTest().create(documentReference,
                Collections.<String, Object>singletonMap("velocityVariables", data));

        verify(htmlMimeBodyPartFactory).create("Hello <b>John Doe</b> <br />john@doe.com",
                Collections.<String, Object>singletonMap("alternate", "Hello John Doe, john@doe.com"));
    }

    @Test
    public void createWithAttachment() throws Exception
    {
        MimeBodyPartFactory<String> htmlMimeBodyPartFactory = this.mocker.getInstance(
                new DefaultParameterizedType(null, MimeBodyPartFactory.class, String.class), "text/html");

        Attachment attachment = mock(Attachment.class);
        List<Attachment> attachments = Collections.singletonList(attachment);

        Map<String, Object> bodyPartParameters = new HashMap<>();
        bodyPartParameters.put("velocityVariables", data);
        bodyPartParameters.put("attachments", attachments);

        MimeBodyPart bodyPart = this.mocker.getComponentUnderTest().create(documentReference, bodyPartParameters);

        Map<String, Object> htmlParameters = new HashMap<>();
        htmlParameters.put("alternate", "Hello John Doe, john@doe.com");
        htmlParameters.put("attachments", attachments);

        verify(htmlMimeBodyPartFactory).create("Hello <b>John Doe</b> <br />john@doe.com", htmlParameters);
    }
}