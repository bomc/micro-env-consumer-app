package de.bomc.poc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.bomc.poc.consumer.config.VersionConfig;

@SpringBootApplication
public class ConsumerApplication {

	/**
	 * <pre>
	 * - To compile the code: ./gradlew clean build
	 * - To run this app: ./gradlew bootRun
	 * </pre>
	 * @param args
	 */
	public static void main(final String[] args) {
		reactor.core.publisher.Hooks.onOperatorDebug();
		
		SpringApplication.run(ConsumerApplication.class, args);
	}

	/**
	 * Used by actuator to extend the info endpoint.
	 */
    @Bean
    public InfoContributor versionInfoContributor(final VersionConfig versionConfig) {
        return builder -> {
            builder.withDetail("bomc.consumer.version", versionConfig.getVersion());
        };
    }
}

