package com.example.reentry.controller;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.dto.FamilyMemberResponse;
import com.example.reentry.service.FamilyMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Family Members", description = "Create and manage family members")
@RestController
@RequestMapping("/api/family-members")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    public FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @Operation(
            summary = "Create a family member",
            description = "Creates a new family member with a name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Family member created",
                    content = @Content(schema = @Schema(implementation = FamilyMemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<FamilyMemberResponse> createFamilyMember(@RequestBody @Valid CreateFamilyMemberRequest request) {

        FamilyMemberResponse savedFamilyMember = familyMemberService.createFamilyMember(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedFamilyMember.id())
                .toUri();

        return ResponseEntity.created(location).body(savedFamilyMember);
    }
}
