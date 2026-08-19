package com.example.reentry.controller;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.dto.FamilyMemberResponse;
import com.example.reentry.service.FamilyMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FamilyMemberController.class)
class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FamilyMemberService familyMemberService;

    @Test
    void shouldReturn201WithLocationHeaderAndBodyWhenFamilyMemberCreated() throws Exception {
        CreateFamilyMemberRequest request = new CreateFamilyMemberRequest("Mom");
        UUID savedId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FamilyMemberResponse savedFamilyMember = new FamilyMemberResponse(savedId, "Mom");

        when(familyMemberService.createFamilyMember(request)).thenReturn(savedFamilyMember);

        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/family-members/" + savedId))
                .andExpect(jsonPath("$.id").value(savedId.toString()))
                .andExpect(jsonPath("$.name").value("Mom"));
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        CreateFamilyMemberRequest request = new CreateFamilyMemberRequest("");

        mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(familyMemberService, never()).createFamilyMember(any(CreateFamilyMemberRequest.class));
    }
}
