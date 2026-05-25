package com.davidrt301.medicare.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidrt301.medicare.dto.request.PatientRequest;
import com.davidrt301.medicare.dto.response.PatientResponse;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

private final PatientService patientService;

    @Operation(summary = "Crear un nuevo paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente Creado"),
            @ApiResponse(responseCode = "400", description = "Request Inválido"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest request){
        log.info("Solicitud para crear paciente recibida: {}", request);
        PatientResponse response = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos los pacientes (paginados)")
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> listPatients(@ParameterObject Pageable pageable){
        log.info("Listando todos los pacientes, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @Operation(summary = "Obtener un paciente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id){
        log.info("Buscando paciente con id={}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Listar pacientes activos")
    @GetMapping("/activos")
    public ResponseEntity<List<PatientResponse>> listActive(){
        log.info("Listando pacientes activos");
        List<PatientResponse> patient = patientService.getActivePatients();
        return ResponseEntity.ok(patient);
    }

    @Operation(summary = "Listar pacientes por estado (paginados)")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PatientResponse>> listPatientsByStatus(
            @PathVariable Status status,
            @ParameterObject Pageable pageable){
        log.info("Listando pacientes con status={}, page={} size={}", 
                status, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(patientService.getPatientsByStatus(status, pageable));
    }

    @Operation(summary = "Actualizar un paciente")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request){
        log.info("Solicitud para actualizar paciente con id={}: {}", id, request);
        PatientResponse response = patientService.updatePatient(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        log.info("Solicitud para eliminar paciente con id={}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
