/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.microprofile.telemetry.internal_fat.apps.spi.customizer;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import componenttest.app.FATServlet;
import io.openliberty.microprofile.telemetry.internal_fat.apps.jaxrspropagation.InMemorySpanExporter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.data.SpanData;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

/**
 * Test that an AutoConfigurationCustomizerProvider can be provided by the SPI
 * <p>
 * The test customizer adds an attribute to a resource and also logs when each of the other customizations are called.
 */
@SuppressWarnings("serial")
@WebServlet("/customizer")
public class CustomizerTestServlet extends FATServlet {
    @Inject
    private Tracer tracer;

    @Inject
    private InMemorySpanExporter exporter;

    @Test
    public void testCustomizer() {
        Span span = tracer.spanBuilder("span").startSpan();
        span.end();

        SpanData spanData = exporter.getFinishedSpanItems(1).get(0);

        // Attributes added by TestCustomizer should have been merged into the default resource
        Resource resource = spanData.getResource();
        assertThat(resource.getAttribute(TestCustomizer.TEST_KEY), equalTo(TestCustomizer.TEST_VALUE));

        // Check that the other customizers added were called
        // Note: propagator listed twice since by default there are two propagators (W3C trace and W3C baggage)
        assertThat(TestCustomizer.loggedEvents, containsInAnyOrder("propagator", "propagator", "properties", "sampler", "exporter", "tracer"));
    }

}