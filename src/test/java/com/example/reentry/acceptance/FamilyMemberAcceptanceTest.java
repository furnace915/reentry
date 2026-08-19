package com.example.reentry.acceptance;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.model.FamilyMember;
import com.example.reentry.repository.FamilyMemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FamilyMemberAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    void shouldCreateFamilyMember() throws Exception {
        CreateFamilyMemberRequest request = new CreateFamilyMemberRequest("Mom");

        String response = mockMvc.perform(post("/api/family-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Mom"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID savedId = UUID.fromString(objectMapper.readTree(response).get("id").asText());

        assertThat(familyMemberRepository.findById(savedId))
                .isPresent()
                .map(FamilyMember::getName)
                .contains("Mom");
    }
}
