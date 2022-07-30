
package com.example.testing;

import com.example.internal.TenantInterceptor;
import com.example.internal.Tenants;
import com.example.tenant.Tenant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@TestConfiguration
public class TenantInterceptorTestConfiguration {

    @Bean
    Tenants tenants() {
        final var tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName("company-a");

        return new Tenants(Set.of(tenant));
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer(ObjectMapper objectMapper, Tenants tenants) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new TenantInterceptor(objectMapper, tenants));
            }
        };
    }
}