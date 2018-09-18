package org.apache.nifi.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.apache.nifi.controller.status.RemoteProcessGroupStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link MetricSet} for NiFi remote processor groups.
 *
 */
public class RemoteProcessGroupMetricSet implements MetricSet {

    private final AtomicReference<RemoteProcessGroupStatus> status;

    public RemoteProcessGroupMetricSet(AtomicReference<RemoteProcessGroupStatus> status) {
        this.status = status;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> metrics = new HashMap<>();

        if (status.get().isReportMetrics()) {
            metrics.put("activeThreads", (Gauge<Integer>) () -> status.get().getActiveThreadCount());
            metrics.put("flowFilesReceived", (Gauge<Integer>) () -> status.get().getReceivedCount());
            metrics.put("flowFilesSent", (Gauge<Integer>) () -> status.get().getSentCount());
        }

        return metrics;
    }
}
