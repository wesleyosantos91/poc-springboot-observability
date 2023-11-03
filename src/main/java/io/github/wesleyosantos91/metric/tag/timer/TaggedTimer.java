package io.github.wesleyosantos91.metric.tag.timer;

import io.github.wesleyosantos91.metric.tag.CommonMetricDetails;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class TaggedTimer extends CommonMetricDetails {

    private final String tagName;

    public TaggedTimer(String identifier, String tagName, MeterRegistry registry) {
        super(identifier, registry);
        this.tagName = tagName;
    }

    public Timer getTimer(String tagValue){
        return Timer.builder(name)
                .tag(tagName, tagValue)
                .publishPercentileHistogram()
                .publishPercentiles(0.5, 0.75, 0.9, 0.95, 0.99)
                .register(registry);
    }
}
