package com.example.reentry.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "A family member")
public record FamilyMemberResponse(

        @Schema(description = "Family member id, generated on creation")
        UUID id,

        @Schema(description = "Family member name", example = "Mom")
        String name) {
}
