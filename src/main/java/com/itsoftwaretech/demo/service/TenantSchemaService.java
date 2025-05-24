package com.itsoftwaretech.demo.service;

import com.itsoftwaretech.demo.master.model.Signup;
import com.itsoftwaretech.demo.master.repository.SignupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantSchemaService {

    private final SignupRepository signupRepository;


    @Value("${spring.datasource.url}")
    private String baseUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    DataSource dataSource;

    @Transactional
    public Map<String, Object> signupAndCreateTenantSchema(String tenantId) {

        Map<String, Object> response = new HashMap<>();
        try{
            Signup signup = Signup.builder()
                    .tenantId(tenantId)
                    .build();

            signupRepository.save(signup);

            try (Connection conn = createMasterDataSource().getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", tenantId));
            }

            runLiquibaseForTenant(tenantId);
            response.put("status","SUCCESS");
            response.put("message","Tenant successfully saved");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error "+e.getMessage());
            throw new RuntimeException("Exception",e);
        }

        return response;

    }

    private void runLiquibaseForTenant(String tenantId) throws Exception {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(createTenantDataSource(tenantId));
//        liquibase.setChangeLog("classpath:db/changelog/v1/002.sql");
//        liquibase.setDefaultSchema(tenantId);
//        liquibase.setShouldRun(true);
//        liquibase.afterPropertiesSet();
        dataSource = createTenantDataSource(tenantId);
        try (Connection connection = dataSource.getConnection()) {

            // Load and replace schema in SQL
            String sqlTemplate = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("db/changelog/v1/002.sql")))
            ).lines().collect(Collectors.joining("\n"));

            String replacedSql = sqlTemplate.replace("__SCHEMA__", tenantId);

            connection.createStatement().execute(replacedSql);
        }
    }

    private DataSource createTenantDataSource(String tenantId) {
        String urlWithSchema = baseUrl + "?currentSchema=" + tenantId;

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(urlWithSchema);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    private DataSource createMasterDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(baseUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }
}
