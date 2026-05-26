package com.davidrt301.medicare.controller;

import java.time.LocalDateTime;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davidrt301.medicare.dto.request.AttentionRequest;
import com.davidrt301.medicare.dto.response.AttentionResponse;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.service.AttentionService;
import com.davidrt301.medicare.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/attentions")
@RequiredArgsConstructor
@Slf4j
public class AttentionController {

    private final AttentionService attentionService;

    @Operation(summary = "Crear una nueva atención médica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atención Creado"),
            @ApiResponse(responseCode = "400", description = "Request Válido"),
            @ApiResponse(responseCode = "404", description = "Paciente o empleado no encontado")
    })
    @PostMapping
    public ResponseEntity<AttentionResponse> createAttention(@Valid @RequestBody AttentionRequest request) {
        log.info("Solicitud para crear atención recibida: {}", request);
        AttentionResponse response = attentionService.createAttention(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas las atenciones (paginadas)")
    @GetMapping
    public ResponseEntity<Page<AttentionResponse>> listAll(@ParameterObject Pageable pageable) {
        log.info("Listando todas las atenciones, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.getAll(pageable));
    }

    @Operation(summary = "Listar atenciones por rango de fechas (paginadas)")
    @GetMapping("/dates")
    public ResponseEntity<Page<AttentionResponse>> listByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @ParameterObject Pageable pageable) {
        log.info("Buscando atenciones entre {} y {}, page={} size={}",
                fechaInicio, fechaFin, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.getByDateRange(fechaInicio, fechaFin, pageable));
    }

    @Operation(summary = "Buscar atenciones por motivo (paginadas)")
    @GetMapping("/serch")
    public ResponseEntity<Page<AttentionResponse>> searchByReason(
            @RequestParam String reason,
            @ParameterObject Pageable pageable) {
        log.info("Buscando atenciones con reason='{}', page={} size={}",
                reason, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.searchByReason(reason, pageable));
    }

    @Operation(summary = "Listar atenciones por estado (paginadas)")
    @GetMapping("/status/{estado}")
    public ResponseEntity<Page<AttentionResponse>> listByStatus(
            @RequestParam Status estado,
            @ParameterObject Pageable pageable) {
        log.info("Listando atenciones con estado={}, page={} size={}",
                estado, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.getByStatus(estado, pageable));
    }

    @Operation(summary = "Listar atenciones de un paciente específico (paginadas)")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<AttentionResponse>> listByPatient(
            @PathVariable("pacienteId") Long patientId,
            @ParameterObject Pageable pageable) {
        log.info("Listando atenciones del paciente id={}, page={} size={}",
                patientId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.getByPatient(patientId, pageable));
    }

    @Operation(summary = "Listar atenciones de un empleado específico (paginadas)")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<AttentionResponse>> listByEmployee(
            @PathVariable Long employeeId,
            @ParameterObject Pageable pageable) {
        log.info("Listando atenciones del empleado id={}, page={} size={}",
                employeeId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(attentionService.getByEmployee(employeeId, pageable));
    }

    @Operation(summary = "Obtener una atención por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<AttentionResponse> getAttentionById(@PathVariable Long id) {
        log.info("Buscando atención con id={}", id);
        return ResponseEntity.ok(attentionService.getAttentionById(id));
    }

    @Operation(summary = "Actualizar una atención existente")
    @PutMapping("/{id}")
    public ResponseEntity<AttentionResponse> updateAttention(
            @PathVariable Long id,
            @Valid @RequestBody AttentionRequest request) {
        log.info("Solicitud para actualizar atención con id={}: {}", id, request);
        AttentionResponse response = attentionService.updateAttention(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar una atención")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttention(@PathVariable Long id) {
        log.info("Solicitud para eliminar atención con id={}", id);
        attentionService.deleteAttention(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar mis atenciones como paciente autenticado (paginadas)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atenciones obtenidas correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/mias")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Page<AttentionResponse>> listarAtencionesMias(@ParameterObject Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Listando atenciones del paciente autenticado: {}, page={} size={}",
                username, pageable.getPageNumber(), pageable.getPageSize());
        Page<AttentionResponse>  response= attentionService.getAuthenticatedPatientAttentions(username, pageable);
        return ResponseEntity.ok(response);
    }

}
