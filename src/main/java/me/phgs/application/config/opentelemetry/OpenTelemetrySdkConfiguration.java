package me.phgs.application.config.opentelemetry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.sdk.OpenTelemetrySdk;

@Configuration
public class OpenTelemetrySdkConfiguration {
    @Bean
    public OpenTelemetrySdk autoConfiguredOpenTelemetrySdk() {
        return null;
    }
}
