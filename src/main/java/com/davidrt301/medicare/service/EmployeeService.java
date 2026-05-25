package com.davidrt301.medicare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.davidrt301.medicare.dto.request.EmployeeRequest;
import com.davidrt301.medicare.dto.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    Page<EmployeeResponse> getByStatus(String status, Pageable pageable);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);

    Optional<EmployeeResponse> findById(Long id);
}
