package com.itsoftwaretech.demo.tenant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "test2")
public class Test2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_description")
    private String testDescription;
}
