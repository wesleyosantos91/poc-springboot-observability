package io.github.wesleyosantos91.metric.tag.timer;

import io.github.wesleyosantos91.metric.tag.CommonMetricDetails;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiTaggedTimer extends CommonMetricDetails {
    List<String> tagNames;

    public MultiTaggedTimer(String name, MeterRegistry registry, String ... tags) {
        super(name, registry);
        this.tagNames =  Arrays.asList(tags.clone());
    }

    public Timer getTimer(String ... tagValues) {
        List<String> adaptedValues = Arrays.asList(tagValues);
        if(adaptedValues.size() != tagNames.size()) {
            throw new IllegalArgumentException("Timer tag values mismatch the tag names! " +
                    "Expected args are " + tagNames.toString() + ", provided tags are " + adaptedValues);
        }
        int size = tagNames.size();
        List<Tag> tags = new ArrayList<>(size);
        for(int i = 0; i<size; i++) {
            tags.add(new ImmutableTag(tagNames.get(i), tagValues[i]));
        }

        return Timer.builder(name)
                .tags(tags)
                .publishPercentileHistogram()
                .publishPercentiles(0.5, 0.75, 0.9, 0.95, 0.99)
                .register(registry);
    }


}