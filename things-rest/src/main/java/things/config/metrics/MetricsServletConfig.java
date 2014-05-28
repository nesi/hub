package things.config.metrics;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.logback.InstrumentedAppender;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by markus on 21/05/14.
 */
@Configuration
@EnableMetrics
public class MetricsServletConfig extends MetricsConfigurerAdapter {

    private MetricRegistry registry;

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        JmxReporter.forRegistry(metricRegistry).build().start();
    }

    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return new HealthCheckRegistry();
    }

    @Override
    public MetricRegistry getMetricRegistry() {

        if ( this.registry == null ) {
            registry = new MetricRegistry();

            registry.registerAll(new GarbageCollectorMetricSet());
            registry.registerAll(new MemoryUsageGaugeSet());
            registry.registerAll(new ThreadStatesGaugeSet());

            LoggerContext factory = (LoggerContext) LoggerFactory.getILoggerFactory();
            Logger root = factory.getLogger(Logger.ROOT_LOGGER_NAME);

            InstrumentedAppender metrics = new InstrumentedAppender(registry);
            metrics.setContext(root.getLoggerContext());
            metrics.start();
            root.addAppender(metrics);
        }

        return registry;
    }

    @Bean
    @Autowired
    public ServletRegistrationBean servletRegistrationBean(MetricRegistry metricRegistry) {
        MetricsServlet ms = new MetricsServlet(metricRegistry);

        ServletRegistrationBean srb = new ServletRegistrationBean(ms, "/metrics/*");
        srb.setLoadOnStartup(1);
        return srb;

    }


}
