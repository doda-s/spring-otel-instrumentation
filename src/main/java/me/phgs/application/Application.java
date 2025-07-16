package me.phgs.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
/* import io.pyroscope.http.Format;
import io.pyroscope.javaagent.EventType;
import io.pyroscope.javaagent.PyroscopeAgent;
import io.pyroscope.javaagent.config.Config;
import jakarta.annotation.PostConstruct; */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public OpenTelemetry openTelemetry() {
		return GlobalOpenTelemetry.get();
	}

	/* @PostConstruct
	public void init() {
		PyroscopeAgent.start(
			new Config.Builder()
				.setApplicationName("ride-sharing-app-java")
				.setProfilingEvent(EventType.ITIMER)
				.setFormat(Format.JFR)
				.setServerAddress("http://localhost:4318")
				.build()
		);
	} */

}
