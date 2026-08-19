package com.example.reentry.service;

import com.example.reentry.dto.CreateFamilyMemberRequest;
import com.example.reentry.dto.FamilyMemberResponse;

public interface FamilyMemberService {

    FamilyMemberResponse createFamilyMember(CreateFamilyMemberRequest request);
}
