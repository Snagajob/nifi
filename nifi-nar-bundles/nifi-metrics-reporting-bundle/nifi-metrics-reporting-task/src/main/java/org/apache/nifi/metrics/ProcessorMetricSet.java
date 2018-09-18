package org.apache.nifi.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.apache.nifi.controller.status.ProcessorStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link MetricSet} for NiFi processors.
 *
 */
public class ProcessorMetricSet implements MetricSet {

    private final AtomicReference<ProcessorStatus> status;

    public ProcessorMetricSet(AtomicReference<ProcessorStatus> status) {
        this.status = status;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> metrics = new HashMap<>();

        if (status.get().isReportMetrics()) {
            metrics.put("activeThreads", (Gauge<Integer>) () -> status.get().getActiveThreadCount());
            metrics.put("bytesRead", (Gauge<Long>) () -> status.get().getBytesRead());
            metrics.put("bytesReceived", (Gauge<Long>) () -> status.get().getBytesReceived());
            metrics.put("bytesSent", (Gauge<Long>) () -> status.get().getBytesSent());
            metrics.put("bytesWritten", (Gauge<Long>) () -> status.get().getBytesWritten());
            metrics.put("flowFilesReceived", (Gauge<Integer>) () -> status.get().getFlowFilesReceived());
            metrics.put("flowFilesSent", (Gauge<Integer>) () -> status.get().getFlowFilesSent());
            metrics.put("processingNanos", (Gauge<Long>) () -> status.get().getProcessingNanos());
        }

        return metrics;
    }
}
