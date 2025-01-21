package com.fra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//import demo.template.common.utils.FernetUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@Configuration
public class DynamicVerticaConfig {

    @Bean(name = "verticaHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.vertica.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "verticaDataSource")
    public DataSource dataSource() {
        HikariConfig config = hikariConfig();

        // MariaDB 연결 정보 설정
        String jdbcUrl = String.format("jdbc:vertica://%s:%s/%s", "localhost", "5433", "mydb");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("dbadmin");
        config.setPassword("password");
        config.setDriverClassName("com.vertica.jdbc.Driver");

        return new HikariDataSource(config);
    }

    @Bean(name = "verticaJdbcTemplate")
    public JdbcTemplate verticaJdbcTemplate(@Qualifier("verticaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
