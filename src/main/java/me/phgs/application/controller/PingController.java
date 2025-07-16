package me.phgs.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleGauge;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import me.phgs.application.Application;
import me.phgs.application.util.Random;

@RestController
public class PingController {
    private static final Logger LOGGER = LoggerFactory
    .getLogger(PingController.class);

    private final AttributeKey<String> ATTR_ENDPOINT = AttributeKey
        .stringKey("endpoint");

    private final Tracer tracer;
    private final LongCounter pageAccessCounter;
    private final DoubleGauge doWorkGauge;

    @Autowired
    PingController(OpenTelemetry openTelemetry) {
        tracer = openTelemetry.getTracer(Application.class.getName());
        Meter meter = openTelemetry.getMeter(Application.class.getName());
        pageAccessCounter = meter
            .counterBuilder("page-access.counter")
            .build();
        doWorkGauge = meter
            .gaugeBuilder("do-work.gauge.response.time")
            .setUnit("ms")
            .build();
    }

    @GetMapping("/ping")
    public String ping() throws InterruptedException {
        int sleepTime = Random.getRandomNumber(0, 200);
        doWork(sleepTime);
        doWorkGauge.set(sleepTime);
        pageAccessCounter.add(1, Attributes.of(ATTR_ENDPOINT, "/ping"));
        return "Pong!";
    }

    private void doWork(int sleepTime) throws InterruptedException {
        Span span = tracer.spanBuilder("doWork").startSpan();
        span.setAttribute("sleep time", sleepTime + " ms");
        try (Scope scope = span.makeCurrent()) {
            LOGGER.info("Starting sleep... (" + sleepTime + " ms)");
            Thread.sleep(sleepTime);
            LOGGER.info("Sleep finished!");
        } finally {
            span.end();
        }
    }
}