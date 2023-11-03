package io.github.wesleyosantos91.metric.tag;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CommonMetricDetails {
    protected String name;
    protected MeterRegistry registry;
}
