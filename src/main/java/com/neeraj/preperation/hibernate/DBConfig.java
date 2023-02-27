package com.neeraj.preperation.hibernate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created on:  Jun 02, 2021
 * Ref:
 */

@Configuration
public class DBConfig {

    @Bean
    @Profile("local")
    public DataSource localDataSource() {
        var url = "jdbc:h2:mem:javaSample;DB_CLOSE_DELAY=-1";
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(100);
        config.setDriverClassName("org.h2.Driver");
        config.addDataSourceProperty("url", url);
        config.setJdbcUrl(url);
        config.addDataSourceProperty("user", "sa");
        config.addDataSourceProperty("password", "password");
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        return new HikariDataSource(config);
    }

}
