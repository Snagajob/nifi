package org.apache.nifi.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.apache.nifi.controller.status.PortStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link MetricSet} for NiFi ports.
 *
 */
public class PortMetricSet implements MetricSet {

    private final AtomicReference<PortStatus> status;

    public PortMetricSet(AtomicReference<PortStatus> status)  {
        this.status = status;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> metrics = new HashMap<>();

        if (status.get().isReportMetrics()) {
            metrics.put("activeThreads", (Gauge<Integer>) () -> status.get().getActiveThreadCount());
            metrics.put("bytesReceived", (Gauge<Long>) () -> status.get().getBytesReceived());
            metrics.put("bytesSent", (Gauge<Long>) () -> status.get().getBytesSent());
            metrics.put("flowFilesReceived", (Gauge<Integer>) () -> status.get().getFlowFilesReceived());
            metrics.put("flowFilesSent", (Gauge<Integer>) () -> status.get().getFlowFilesSent());
        }

        return metrics;
    }
}
