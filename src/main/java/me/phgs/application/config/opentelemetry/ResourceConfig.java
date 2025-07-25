package me.phgs.application.config.opentelemetry;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.ServiceAttributes;

public class ResourceConfig {
    public static Resource create() {
        return Resource.getDefault().toBuilder()
            .put(ServiceAttributes.SERVICE_NAME, "dice-roll")
            .build();
    }
}
