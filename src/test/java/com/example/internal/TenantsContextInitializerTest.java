package com.example.internal;

import com.example.tenant.Tenant;
import com.example.tenant.TenantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = TenantsContextInitializer.class)
@Testcontainers
class TenantsContextInitializerTest {

    @Container
    private static final PostgreSQLContainer<?> DATABASE_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DATABASE_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DATABASE_CONTAINER::getPassword);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TenantRepository tenantRepository;

    @Test
    void registeringTenants() {
        final var tenantsPersisted = toSet(tenantRepository.findAll());

        final var tenants = applicationContext.getBean(Tenants.class);

        assertThat(tenantsPersisted)
                .isNotEmpty();
        assertThat(tenantsPersisted)
                .extracting(Tenant::getName)
                .containsExactlyElementsOf(tenants.getTenantNames());
        assertThat(tenants.getTenantNames())
                .hasSize(tenantsPersisted.size());
    }

    private <T> Set<T> toSet(Iterable<T> iterable) {
        final var elements = new HashSet<T>();

        for (final var element : iterable) {
            elements.add(element);
        }

        return elements;
    }
}