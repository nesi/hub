/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

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
public class ProjectDbConfig {


    @Autowired
    private Environment env;


    // =============================================================================
    // Pan audit connector

    @Bean
    public DefaultConfiguration projectDbConfiguration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(projectDbConnectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(
                projectDbJooqToSpringExceptionTransformer()
        ));

        String sqlDialectName = env.getRequiredProperty("projectDB.jooq.sql.dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    @Bean
    public DataSourceConnectionProvider projectDbConnectionProvider() {
        return new DataSourceConnectionProvider(projectDbTransactionAwareDataSource());
    }

    @Bean(name = "projectDbContext")
    public DefaultDSLContext projectDbContext() {
        return new DefaultDSLContext(projectDbConfiguration());
    }

    @Bean(destroyMethod = "close", name = "projectDbDataSource")
    public DataSource projectDbDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty("projectDB.db.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("projectDB.db.url"));
        dataSource.setUsername(env.getRequiredProperty("projectDB.db.username"));
        dataSource.setPassword(env.getRequiredProperty("projectDB.db.password"));

        return dataSource;
    }

    @Bean
    public JOOQToSpringExceptionTransformer projectDbJooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }

    @Bean
    public LazyConnectionDataSourceProxy projectDbLazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(projectDbDataSource());
    }

    @Bean
    public TransactionAwareDataSourceProxy projectDbTransactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(projectDbLazyConnectionDataSource());
    }

    @Bean
    public DataSourceTransactionManager projectDbTransactionManager() {
        return new DataSourceTransactionManager(projectDbLazyConnectionDataSource());
    }
}
