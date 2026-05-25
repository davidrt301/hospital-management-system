package com.davidrt301.medicare.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.davidrt301.medicare.dto.request.PatientRequest;
import com.davidrt301.medicare.dto.response.PatientResponse;
import com.davidrt301.medicare.model.Status;

public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deletePatient(Long id);

    Page<PatientResponse> getAllPatients(Pageable pageable);

    Page<PatientResponse> getPatientsByStatus(Status status, Pageable pageable);

    List<PatientResponse> getActivePatients();

    Long getPatientIdByUsername(String username);
}
