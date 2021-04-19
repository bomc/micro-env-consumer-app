package de.bomc.poc.consumer.application.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.HashMap;
import java.util.Map;

public class TaggedTimer {
	private String name;
	private String tagName;
	private MeterRegistry registry;
	private final Map<String, Timer> timers = new HashMap<>();

	public TaggedTimer(final String name, final String tagName, final MeterRegistry registry) {
		this.name = name;
		this.tagName = tagName;
		this.registry = registry;
	}

	public Timer getTimer(final String tagValue) {
		Timer timer = timers.get(tagValue);
		
		if (timer == null) {
			timer = Timer.builder(name).tags(tagName, tagValue).register(registry);
			timers.put(tagValue, timer);
		}
		
		return timer;
	}

}
