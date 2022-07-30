package com.example.internal;

import com.example.tenant.Tenant;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Tenants {

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<Tenant> tenants;

    @Setter(AccessLevel.NONE)
    private Set<String> tenantNames;

    public Tenants(Set<Tenant> tenants) {
        this.tenants = tenants;
        this.tenantNames = tenants.stream()
                .map(Tenant::getName)
                .collect(Collectors.toSet());
    }

    public Tenant getByTenantName(String tenantName) {
        for (final var tenant : tenants) {
            if (tenant.getName().equals(tenantName)) {
                return tenant;
            }
        }

        throw new RuntimeException("None tenant found with name " + tenantName);
    }
}
