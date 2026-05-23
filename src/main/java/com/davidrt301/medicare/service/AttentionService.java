package com.davidrt301.medicare.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.davidrt301.medicare.dto.request.AttentionRequest;
import com.davidrt301.medicare.dto.response.AttentionResponse;
import com.davidrt301.medicare.model.Status;

public interface AttentionService {

    AttentionResponse createAttention(AttentionRequest request);

    AttentionResponse getAttentionById(Long id);

    Page<AttentionResponse> getAll(Pageable pageable);

    Page<AttentionResponse> getByPatient(Long patientId, Pageable pageable);

    Page<AttentionResponse> getByEmployee(Long employeeId, Pageable pageable);

    Page<AttentionResponse> getByStatus(Status status, Pageable pageable);

    Page<AttentionResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<AttentionResponse> searchByReason(String reason, Pageable pageable);

    AttentionResponse updateAttention(Long id, AttentionRequest request);

    void deleteAttention(Long id);

    Page<AttentionResponse> getAuthenticatedPatientAttentions(String username, Pageable pageable);

}
