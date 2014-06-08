package hub.config.connectors;

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
public class DimJobConfig {


    @Autowired
    private Environment env;


    // =============================================================================
    // Pan audit connector

    @Bean
    public DefaultConfiguration dimJobConfiguration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(dimJobConnectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(
                dimJobJooqToSpringExceptionTransformer()
        ));

        String sqlDialectName = env.getRequiredProperty("dim_job.jooq.sql.dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    @Bean(name = "dimJobContext")
    public DefaultDSLContext dimJobContext() {
        return new DefaultDSLContext(dimJobConfiguration());
    }

    @Bean(destroyMethod = "close", name = "dimJobDataSource")
    public DataSource dimJobDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty("dim_job.db.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("dim_job.db.url"));
        dataSource.setUsername(env.getRequiredProperty("dim_job.db.username"));
        dataSource.setPassword(env.getRequiredProperty("dim_job.db.password"));

        return dataSource;
    }

    @Bean
    public LazyConnectionDataSourceProxy dimJobLazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(dimJobDataSource());
    }

    @Bean
    public TransactionAwareDataSourceProxy dimJobTransactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dimJobLazyConnectionDataSource());
    }

    @Primary
    @Bean
    public DataSourceTransactionManager dimJobTransactionManager() {
        return new DataSourceTransactionManager(dimJobLazyConnectionDataSource());
    }

    @Bean
    public DataSourceConnectionProvider dimJobConnectionProvider() {
        return new DataSourceConnectionProvider(dimJobTransactionAwareDataSource());
    }

    @Bean
    public JOOQToSpringExceptionTransformer dimJobJooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }
}
