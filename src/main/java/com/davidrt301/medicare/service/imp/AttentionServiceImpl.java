package com.davidrt301.medicare.service.imp;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidrt301.medicare.dto.request.AttentionRequest;
import com.davidrt301.medicare.dto.response.AttentionResponse;
import com.davidrt301.medicare.exception.BusinessException;
import com.davidrt301.medicare.exception.InvalidRequestException;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.mapper.AttentionMapper;
import com.davidrt301.medicare.model.Attention;
import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Patient;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.repository.AttentionRepository;
import com.davidrt301.medicare.repository.EmployeeRepository;
import com.davidrt301.medicare.repository.PatientRepository;
import com.davidrt301.medicare.service.AttentionService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttentionServiceImpl implements AttentionService {

    private final AttentionRepository attentionRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    private final AttentionMapper attentionMapper;

    


    @Override
    @Transactional
    public AttentionResponse createAttention(AttentionRequest request) {
        if(request.date()==null){
            throw new InvalidRequestException("La fecha de la atención es obligatoria");
        }

        if(request.date().isBefore(LocalDateTime.now().minusMinutes(1))){
            throw new InvalidRequestException("La fecha de la atención no puede ser anterior a la fecha actual");
        }

        Patient patient = patientRepository.findById(request.patientId()).
                orElseThrow(() -> new ResourceNotFoundException("Peciente no encontrado con id: " + request.patientId()));

        Employee employee = employeeRepository.findById(request.employeeId()).
                orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + request.employeeId()));

        Attention attention = attentionMapper.toEntity(request);
        attention.setPatient(patient);
        attention.setEmployee(employee);

        attention = attentionRepository.save(attention);

        log.info("Atención creada. id={}", attention.getId());
        return attentionMapper.toResponse(attention);

        
    }

    @Override
    @Transactional(readOnly = true)
    public AttentionResponse getAttentionById(Long id) {
        return attentionRepository.findById(id)
                .map(attentionMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getAll(Pageable pageable) {
        return attentionRepository.findAll(pageable)
                .map(attentionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getByPatient(Long patientId, Pageable pageable) {
        Patient patient = patientRepository.findById(patientId)
                        .orElseThrow(()-> new ResourceNotFoundException("Paciente no encontrado con id: " + patientId));
        return attentionRepository.findByPatient(patient,pageable)
                .map(attentionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getByEmployee(Long employeeId, Pageable pageable) {
        Employee employee = employeeRepository.findById(employeeId)
                            .orElseThrow(()-> new ResourceNotFoundException("Empleado no encontrado con id: " + employeeId));
        return attentionRepository.findByEmployee(employee,pageable)
                .map(attentionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getByStatus(Status status, Pageable pageable) {
        return attentionRepository.findByStatus(status, pageable)
                .map(attentionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
    if(startDate == null || endDate == null){
        throw new InvalidRequestException("Las fechas de inicio y fin son obligatorias");
    }
    if(endDate.isBefore(startDate)){
        throw new InvalidRequestException("La fecha de fin no puede ser anterior a la fecha de inicio");
    }

    return attentionRepository.findByDateBetween(startDate, endDate, pageable)
            .map(attentionMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> searchByReason(String reason, Pageable pageable) {
        if(reason == null || reason.trim().isEmpty()){
            throw new InvalidRequestException("El motivo de la busqueda no puede estar vacio");
        }
        return attentionRepository.searchByDescription(reason, pageable)
                .map(attentionMapper::toResponse);
    }

    @Override
    @Transactional
    public AttentionResponse updateAttention(Long id, AttentionRequest request) {
        Attention attention = attentionRepository.findById(id)
                            .orElseThrow(()-> new ResourceNotFoundException("Atención no encontrada con id: "+ id));

        if(attention.getStatus() == Status.FINALIZADO){
            throw new BusinessException("No se puede actualizar una atención con estado finalizada");
        }

        if(request.date() == null){
            throw new InvalidRequestException("La fecha de atención es obligatoria");
        }

        attention.setDate(request.date());
        attention.setDescription(request.description());
        attention.setStatus(request.status());

        if(request.patientId() != null && !request.employeeId().equals(attention.getPatient().getId())){
            attention.setPatient(patientRepository.findById(request.patientId())
                .orElseThrow(()-> new ResourceNotFoundException("Paciente no encontrado con id: " + request.patientId())));
        }

        if(request.employeeId() != null && request.employeeId().equals(attention.getEmployee().getId())){
            attention.setEmployee(employeeRepository.findById(request.employeeId())
                .orElseThrow(()-> new ResourceNotFoundException("Empleado no encontrado con id: " + request.employeeId())));
        }

        attention = attentionRepository.save(attention);

        log.info("Atención actualizada. id={}", attention.getId());

        return attentionMapper.toResponse(attention);
    }

    @Override
    @Transactional
    public void deleteAttention(Long id) {
        if(!attentionRepository.existsById(id)){
            throw new ResourceNotFoundException("Atención no encontrada con ID:" + id);
        }
        attentionRepository.deleteById(id);
        log.info("Atención eliminada. id={}", id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponse> getAuthenticatedPatientAttentions(String username, Pageable pageable) {
        Patient patient = patientRepository.findByUserUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado para el usuario autenticado"));

    return attentionRepository.findByPatient(patient, pageable)
            .map(attentionMapper::toResponse);
    }

}
