package com.davidrt301.medicare.service.imp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidrt301.medicare.dto.request.PatientRequest;
import com.davidrt301.medicare.dto.response.PatientResponse;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.mapper.PatientMapper;
import com.davidrt301.medicare.model.Patient;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.repository.PatientRepository;
import com.davidrt301.medicare.repository.PersonRepository;
import com.davidrt301.medicare.service.PatientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        Person person = personRepository.findById(request.personId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + request.personId()));

        Patient patient = patientMapper.toEntity(request);
        patient.setPerson(person);
        patient = patientRepository.save(patient);

        log.info("Paciente creado. id={}", patient.getId());
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        log.info("Obteniendo paciente con id={}", id);
        return patientRepository.findById(id)
                .map(patientMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        log.info("Actualizando paciente con id={}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));


        Person person = personRepository.findById(request.personId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + request.personId()));

        patientMapper.updateEntity(request, patient);
        patient.setPerson(person);

        Patient update = patientRepository.save(patient);

        return patientMapper.toResponse(update);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        patientRepository.deleteById(id);
        log.info("Paciente eliminado. id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        log.info("Listando pacientes paginados, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return patientRepository.findAll(pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getPatientsByStatus(Status status, Pageable pageable) {
        log.info("Listando pacientes por estado={} paginados", status);
        return patientRepository.findByStatus(status, pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getActivePatients() {
        log.info("Listando todos los pacientes activos");
        return patientRepository.findByStatus(Status.ACTIVO)
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getPatientIdByUsername(String username) {
        log.info("Obteniendo ID del paciente para el usuario: {}", username);
        Patient patient = patientRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado para el usuario: " + username));

        return patient.getId();
    }

}
