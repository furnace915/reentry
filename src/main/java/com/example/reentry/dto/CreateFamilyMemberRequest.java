package com.example.reentry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for creating a family member")
public record CreateFamilyMemberRequest(

        @Schema(description = "Family member name", example = "Mom", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name) {
}
