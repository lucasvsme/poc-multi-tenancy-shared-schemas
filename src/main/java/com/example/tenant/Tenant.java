package com.example.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TENANT")
@Data
@NoArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TENANT_ID_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "TENANT_ID_SEQUENCE_GENERATOR", sequenceName = "TENANT_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "TENANT_ID")
    private Long id;

    @Column(name = "TENANT_NAME")
    private String name;
}
