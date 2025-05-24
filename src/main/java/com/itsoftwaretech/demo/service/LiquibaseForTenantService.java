package com.itsoftwaretech.demo.service;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class LiquibaseForTenantService {

    private final DataSource dataSource;


    public void runLiquibaseForTenant(String tenantId) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/changelog/tenant-changelog.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.setChangeLogParameter("SCHEMA", tenantId);

            liquibase.update(new Contexts());
        } catch (Exception e){
            throw new Exception(e);
        }
    }



}
