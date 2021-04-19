package de.bomc.poc.consumer.application.metrics;

import java.util.HashMap;
import java.util.Map;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class TaggedCounter {
	private String name;
	private String tagName;
	private MeterRegistry registry;
	private final Map<String, Counter> counters = new HashMap<>();

	public TaggedCounter(final String name, final String tagName, final MeterRegistry registry) {
		this.name = name;
		this.tagName = tagName;
		this.registry = registry;
	}

	public void increment(final String tagValue) {
		Counter counter = counters.get(tagValue);
		
		if (counter == null) {
			counter = Counter.builder(name).tags(tagName, tagValue).register(registry);
			counters.put(tagValue, counter);
		}
		
		counter.increment();
	}

}