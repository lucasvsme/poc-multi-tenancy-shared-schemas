package com.example.product;

import com.example.tenant.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Iterable<Product> findAllByTenant(Tenant tenant);
}
