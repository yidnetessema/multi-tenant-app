package com.itsoftwaretech.demo.controller;

import com.itsoftwaretech.demo.payload.request.TenantRequest;
import com.itsoftwaretech.demo.service.TenantSchemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/signup")
@RequiredArgsConstructor
public class SignupController {

    private final TenantSchemaService tenantSchemaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerTenant(@Valid @RequestBody TenantRequest request) {
           return  ResponseEntity.ok(tenantSchemaService.signupAndCreateTenantSchema(request.tenantId()));
    }
}
