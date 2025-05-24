package com.itsoftwaretech.demo.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    private static final String DEFAULT_TENANT = "public";
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantId(String tenantId){
        currentTenant.set(tenantId);
    }

    public static void clear(){
        currentTenant.remove();
    }

    @Override
    public Object resolveCurrentTenantIdentifier() {
        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
