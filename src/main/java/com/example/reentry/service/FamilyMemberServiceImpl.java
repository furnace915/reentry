package com.example.reentry.service;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.dto.FamilyMemberResponse;
import com.example.reentry.model.FamilyMember;
import com.example.reentry.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberServiceImpl(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Override
    public FamilyMemberResponse createFamilyMember(CreateFamilyMemberRequest request) {
        FamilyMember savedFamilyMember = familyMemberRepository.save(new FamilyMember(request.name()));

        return new FamilyMemberResponse(savedFamilyMember.getId(), savedFamilyMember.getName());
    }
}
