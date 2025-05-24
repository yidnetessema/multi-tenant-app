package com.itsoftwaretech.demo.service;

import jakarta.persistence.EntityManagerFactory;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LiquibaseForTenantService {

    @Autowired
    private DataSource masterDataSource;

    @Value("${spring.datasource.url}")
    private String baseUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    private final DataSource dataSource;

    private final EntityManagerFactory entityManagerFactory;

    public void runLiquibaseForTenant(String tenantId) {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/changelog/tenant-changelog.xml", new ClassLoaderResourceAccessor(), database);

            // Pass the tenant schema as parameter to liquibase
            liquibase.setChangeLogParameter("SCHEMA", tenantId);

            liquibase.update(new Contexts());
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error "+e.getMessage());
        }
    }



}
