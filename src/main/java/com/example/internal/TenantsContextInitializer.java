package com.example.internal;

import com.example.tenant.Tenant;
import com.example.tenant.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import java.util.HashSet;

public final class TenantsContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantsContextInitializer.class);

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        LOGGER.debug("Initializing tenants context");

        applicationContext.registerBean(Tenants.class, () -> {
            final var tenantRepository = applicationContext.getBean(TenantRepository.class);

            final var tenants = new HashSet<Tenant>();
            for (final var tenant : tenantRepository.findAll()) {
                tenants.add(tenant);
            }

            LOGGER.debug("Registering tenants bean (tenants={})", tenants);
            return new Tenants(tenants);
        });
    }
}
