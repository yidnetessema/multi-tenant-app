package com.itsoftwaretech.demo.tenant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "test1")
public class Test1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_description")
    private String testDescription;
}
