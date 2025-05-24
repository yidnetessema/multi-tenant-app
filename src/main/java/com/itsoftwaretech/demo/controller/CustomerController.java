package com.itsoftwaretech.demo.controller;

import com.itsoftwaretech.demo.payload.request.Test1Request;
import com.itsoftwaretech.demo.service.TenantServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final TenantServiceImpl tenantService;

    @Operation(summary = "Add test data")
    @PostMapping
    public ResponseEntity<Map<String, Object>> addTestData(@Valid @RequestBody Test1Request request, @RequestHeader("X-Tenant-ID") String tenantId) {
        return  ResponseEntity.ok(tenantService.saveTest1ForTenant(request,tenantId));
    }

}
