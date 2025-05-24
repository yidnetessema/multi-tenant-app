package com.itsoftwaretech.demo.tenant.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "test1")
@Data
@Builder
public class Test1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_description")
    private String testDescription;
}
