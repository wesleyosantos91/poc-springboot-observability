package io.github.wesleyosantos91.metric.tags.timer;

import io.github.wesleyosantos91.metric.tags.CommonMetricDetails;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
                .publishPercentiles(0.5, 0.9, 0.95, 0.99)
                .register(registry);
    }
}
