package com.example.internal;

import com.example.tenant.Tenant;

public class TenantReference {

    private static final ThreadLocal<Tenant> CURRENT_TENANT = new ThreadLocal<>();

    public static Tenant get() {
        return CURRENT_TENANT.get();
    }

    public static void set(Tenant tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static void unset() {
        CURRENT_TENANT.remove();
    }
}
