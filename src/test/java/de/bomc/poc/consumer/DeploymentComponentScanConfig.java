package de.bomc.poc.consumer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class DeploymentComponentScanConfig {
	//
	// Adding everything from root 'de.bomb.poc.consumer' in this configuration.
	// Use this configuration with Import annotation. See PublishRouterTest for
	// using.
}
