package me.phgs.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import me.phgs.application.Application;
import me.phgs.application.util.Random;

@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(DiceRollController.class);
    private final AttributeKey<String> ATTR_TEXT_MESSAGE = AttributeKey
        .stringKey("text.message");
    private final AttributeKey<String> ATTR_ENDPOINT = AttributeKey
        .stringKey("endpoint");

    private final Tracer tracer;
    private final LongHistogram textMessageHistogram;
    private final LongCounter pageAccessCounter;

    @Autowired
    MessageController(OpenTelemetry openTelemetry) {
        tracer = openTelemetry.getTracer(Application.class.getName());
        Meter meter = openTelemetry.getMeter(Application.class.getName());
        textMessageHistogram = meter
            .histogramBuilder("text-message.histogram")
            .ofLongs()
            .build();
        pageAccessCounter = meter
            .counterBuilder("page-access.counter")
            .build();
    }

    @GetMapping("/message")
    public String textMessage() {
        pageAccessCounter.add(1, Attributes.of(ATTR_ENDPOINT, "/message"));
        return getMessage();
    }

    private String getMessage() {
        String[] messages = {
            "Hello, World!",
            "Hello, Spring!",
            "Hello, OpenTelemetry!"
        };

        int messageIndex = Random.getRandomNumber(0, messages.length);
        String msg = messages[messageIndex];
        
        LOGGER.info("Message index: " + messageIndex);
        LOGGER.info("Message: " + msg);

        textMessageHistogram.record(1, Attributes.of(ATTR_TEXT_MESSAGE, msg));

        Span span = tracer.spanBuilder("getMessage").startSpan();
        span.setAttribute("message", msg);
        span.end();
        return msg;
    }
}
