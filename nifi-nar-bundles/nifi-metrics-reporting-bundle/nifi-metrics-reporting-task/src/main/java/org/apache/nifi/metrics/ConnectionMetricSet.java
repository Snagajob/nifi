package org.apache.nifi.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.apache.nifi.controller.status.ConnectionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link MetricSet} for NiFi connections.
 *
 */
public class ConnectionMetricSet implements MetricSet {

    private AtomicReference<ConnectionStatus> status;

    public ConnectionMetricSet(AtomicReference<ConnectionStatus> status) {
        this.status = status;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> metrics = new HashMap<>();

        if (status.get().isReportMetrics()) {
            metrics.put("bytesQueued", (Gauge<Long>) () -> status.get().getQueuedBytes());
            metrics.put("flowFilesQueued", (Gauge<Integer>) () -> status.get().getQueuedCount());
        }

        return metrics;
    }
}
