package io.github.collinsigbiks.sensorops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sensorOpsOpenApi() {
        return new OpenAPI().info(new Info()
                .title("SensorOps Telemetry API")
                .version("0.1.0")
                .description("""
                        Collects IoT sensor readings, stores per-device limits and \
                        (from Milestone 3) raises alerts when a reading breaks its limit.

                        Errors use RFC 9457 Problem Details (`application/problem+json`).""")
                .contact(new Contact()
                        .name("CollinsIgbiks")
                        .url("https://github.com/CollinsIgbiks/sensorops-api"))
                .license(new License()
                        .name("MIT")
                        .url("https://opensource.org/licenses/MIT")));
    }
}