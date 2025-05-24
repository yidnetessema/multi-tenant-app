package com.itsoftwaretech.demo.payload.request;

import jakarta.validation.constraints.NotBlank;

public record Test1Request(
        @NotBlank(message = "Test name is required")   String testName,
        @NotBlank(message = "Test description is required")String testDescription
) {
}
