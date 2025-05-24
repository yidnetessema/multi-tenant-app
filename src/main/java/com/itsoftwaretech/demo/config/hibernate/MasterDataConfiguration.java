package com.itsoftwaretech.demo.config.hibernate;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.itsoftwaretech.demo.master",
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager"
)
@EntityScan(basePackages = "com.itsoftwaretech.demo.master.model")
public class MasterDataConfiguration {

    public static String dataSourcePrefix = "java:/";

    private static String systemStatus;

    @Value("${system.status}")
    public void setSystemStatus(String systemStatus) {
        MasterDataConfiguration.systemStatus = systemStatus;
    }

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource masterDataSource(){
        return new DriverManagerDataSource();
    }

    @Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("masterDataSource") DataSource masterDataSource
    ){
        return builder
                .dataSource(masterDataSource)
                .packages("com.itsoftwaretech.demo.master.model")
                .persistenceUnit(systemStatus)
                .build();
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory entityManagerFactoryBuilder
    ){
        return new JpaTransactionManager(entityManagerFactoryBuilder);
    }
}
