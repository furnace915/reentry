package com.example.reentry.service;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.dto.FamilyMemberResponse;
import com.example.reentry.model.FamilyMember;
import com.example.reentry.repository.FamilyMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FamilyMemberServiceImplTest {

    @InjectMocks
    private FamilyMemberServiceImpl familyMemberService;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @Test
    void shouldCreateFamilyMember() {
        CreateFamilyMemberRequest request = new CreateFamilyMemberRequest("Mom");
        FamilyMember savedFamilyMember = mock(FamilyMember.class);
        UUID savedId = UUID.randomUUID();

        when(familyMemberRepository.save(any(FamilyMember.class))).thenReturn(savedFamilyMember);
        when(savedFamilyMember.getId()).thenReturn(savedId);
        when(savedFamilyMember.getName()).thenReturn("Mom");

        FamilyMemberResponse actual = familyMemberService.createFamilyMember(request);

        assertThat(actual).isEqualTo(new FamilyMemberResponse(savedId, "Mom"));
    }
}
