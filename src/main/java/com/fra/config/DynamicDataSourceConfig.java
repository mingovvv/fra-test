package com.fra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.fra.repository", // MariaDB Repository 패키지
        entityManagerFactoryRef = "mariaDbEntityManagerFactory",
        transactionManagerRef = "mariaDbTransactionManager"
)
public class DynamicDataSourceConfig {


    @Primary
    @Bean(name = "mariaDbHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.mariadb.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name = "mariaDbDataSource")
    public DataSource dataSource() {
        HikariConfig config = hikariConfig();

        // MariaDB 연결 정보 설정
        String jdbcUrl = String.format("jdbc:mariadb://%s:%s/%s", "localhost", "3306", "AI_SH");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("root");
        config.setPassword("root");
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "mariaDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mariaDbEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mariaDbDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.fra.entity") // 엔티티 클래스 패키지
                .persistenceUnit("mariaDb")
                .build();
    }

    @Primary
    @Bean(name = "mariaDbTransactionManager")
    public PlatformTransactionManager mariaDbTransactionManager(
            @Qualifier("mariaDbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
