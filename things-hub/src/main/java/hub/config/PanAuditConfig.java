package hub.config;

import com.jolbox.bonecp.BoneCPDataSource;
import hub.utils.JOOQToSpringExceptionTransformer;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

/**
 * Configuration to setup access to pan admin database.
 *
 * @author: Markus Binsteiner
 */
@Configuration
public class PanAuditConfig {


    @Autowired
    private Environment env;


    // =============================================================================
    // Pan audit connector

    @Bean
    public DefaultConfiguration panAuditConfiguration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(panAuditconnectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(
                panAuditjooqToSpringExceptionTransformer()
        ));

        String sqlDialectName = env.getRequiredProperty("pan.jooq.sql.dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    @Bean(name = "panAuditContext")
    public DefaultDSLContext panAuditContext() {
        return new DefaultDSLContext(panAuditConfiguration());
    }

    @Bean(destroyMethod = "close", name = "panAuditDataSource")
    public DataSource panAuditDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty("pan.db.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("pan.db.url"));
        dataSource.setUsername(env.getRequiredProperty("pan.db.username"));
        dataSource.setPassword(env.getRequiredProperty("pan.db.password"));

        return dataSource;
    }

    @Bean
    public LazyConnectionDataSourceProxy panAuditLazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(panAuditDataSource());
    }

    @Bean
    public TransactionAwareDataSourceProxy panAuditTransactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(panAuditLazyConnectionDataSource());
    }

    @Primary
    @Bean
    public DataSourceTransactionManager panAuditTransactionManager() {
        return new DataSourceTransactionManager(panAuditLazyConnectionDataSource());
    }

    @Bean
    public DataSourceConnectionProvider panAuditconnectionProvider() {
        return new DataSourceConnectionProvider(panAuditTransactionAwareDataSource());
    }

    @Bean
    public JOOQToSpringExceptionTransformer panAuditjooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }
}
