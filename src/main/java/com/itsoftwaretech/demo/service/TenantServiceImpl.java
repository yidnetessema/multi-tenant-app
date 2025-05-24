package com.itsoftwaretech.demo.service;

import com.itsoftwaretech.demo.config.CurrentTenantIdentifierResolverImpl;
import com.itsoftwaretech.demo.master.model.Signup;
import com.itsoftwaretech.demo.master.repository.SignupRepository;
import com.itsoftwaretech.demo.payload.request.Test1Request;
import com.itsoftwaretech.demo.tenant.model.Test1;
import com.itsoftwaretech.demo.tenant.repository.Test1Repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TenantServiceImpl {

    private final SignupRepository signupRepository;

    private final Test1Repository test1RepositoryProvider;

    private final LiquibaseForTenantService liquibaseForTenantConfig;

    @Autowired
    public TenantServiceImpl(
            Test1Repository test1RepositoryProvider,
            LiquibaseForTenantService liquibaseForTenantConfig,
            SignupRepository signupRepository
    ) {
        this.test1RepositoryProvider = test1RepositoryProvider;
        this.signupRepository = signupRepository;
        this.liquibaseForTenantConfig = liquibaseForTenantConfig;
    }

    @Transactional
    public Map<String, Object> signupAndCreateTenantSchema(String tenantId) {

        Map<String, Object> response = new HashMap<>();
        try {
            Signup signup = Signup.builder()
                    .tenantId(tenantId)
                    .build();

            signupRepository.save(signup);

            liquibaseForTenantConfig.runLiquibaseForTenant(tenantId);


        } catch (Exception e) {
            throw new RuntimeException("Exception", e);
        }

        return response;

    }


    @Transactional
    public Map<String, Object> saveTest1ForTenant(Test1Request request, String tenantId) {
        Map<String, Object> response = new HashMap<>();
        try {

            CurrentTenantIdentifierResolverImpl.setTenantId(tenantId);

            Test1 test1 = Test1.builder()
                    .id(null)
                    .testName(request.testName())
                    .testDescription(request.testDescription())
                    .build();

            if (test1RepositoryProvider != null) {
                test1RepositoryProvider.save(test1);

                response.put("status", "SUCCESS");
                response.put("message", "Data saved successfully");

            }

        } finally {
            CurrentTenantIdentifierResolverImpl.clear();
        }

        return response;
    }

}
