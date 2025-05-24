package com.itsoftwaretech.demo.tenant.repository;

import com.itsoftwaretech.demo.tenant.model.Test1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Test1Repository extends JpaRepository<Test1,Long> {


}
