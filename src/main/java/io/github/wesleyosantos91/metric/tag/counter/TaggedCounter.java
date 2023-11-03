package io.github.wesleyosantos91.metric.tag.counter;

import io.github.wesleyosantos91.metric.tag.CommonMetricDetails;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;

public class TaggedCounter extends CommonMetricDetails {
    private String tagName;

    public TaggedCounter(String name, String tagName, MeterRegistry registry) {
        super(name, registry);
        this.tagName = tagName;
    }

    public void increment(String tagValue) {
        Counter counter = registry.counter(name, tagName, tagValue);
        counter.getId().withTag(new ImmutableTag(tagName, tagValue));
        counter.increment();
    }
}
