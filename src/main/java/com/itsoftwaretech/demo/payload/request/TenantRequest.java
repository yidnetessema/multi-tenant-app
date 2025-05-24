package com.itsoftwaretech.demo.payload.request;

import jakarta.validation.constraints.NotBlank;

public record TenantRequest(
        @NotBlank(message = "Tenant Id is required") String tenantId

) {
}
