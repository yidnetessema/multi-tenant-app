package com.itsoftwaretech.demo.master.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "signup")
@Data
@Builder
public class Signup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tenant_id",unique = true,nullable = false)
    private String tenantId;
    
}
