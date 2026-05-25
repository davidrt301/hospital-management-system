package com.davidrt301.medicare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.davidrt301.medicare.dto.request.SpecialtyRequest;
import com.davidrt301.medicare.dto.response.SpecialtyResponse;

public interface SpecialtyService {

    SpecialtyResponse createSpecialty(SpecialtyRequest request);

    SpecialtyResponse updateSpecialty(Long id, SpecialtyRequest request);

    void deleteSpecialty(Long id);

    SpecialtyResponse getSpecialtyById(Long id);

    Page<SpecialtyResponse> getAllSpecialties(Pageable pageable);

    Page<SpecialtyResponse> searchByName(String name, Pageable pageable);

    Page<SpecialtyResponse> getByStatus(String status, Pageable pageable);

}
