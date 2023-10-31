package io.github.wesleyosantos91.metric.tags;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CommonMetricDetails {
    protected String name;
    protected MeterRegistry registry;
}
