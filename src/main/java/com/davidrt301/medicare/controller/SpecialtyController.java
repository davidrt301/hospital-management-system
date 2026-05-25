package com.davidrt301.medicare.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davidrt301.medicare.dto.request.SpecialtyRequest;
import com.davidrt301.medicare.dto.response.SpecialtyResponse;
import com.davidrt301.medicare.service.SpecialtyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
@Slf4j
public class SpecialtyController {

    private final SpecialtyService specialtyService;


    @Operation(summary = "Crear una nueva especialidad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Especialidad Creada"),
            @ApiResponse(responseCode = "400", description = "Request Inválido")
    })
    @PostMapping
    public ResponseEntity<SpecialtyResponse> createSpecialty(@Valid @RequestBody SpecialtyRequest request){
        log.info("Solicitud para crear especialidad recibida: {}", request);
        SpecialtyResponse response = specialtyService.createSpecialty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas las especialidades (paginadas)")
    @GetMapping
    public ResponseEntity<Page<SpecialtyResponse>> listSpecialties(@ParameterObject Pageable pageable){
        log.info("Listando todas las especialidades, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(specialtyService.getAllSpecialties(pageable));
    }

    @Operation(summary = "Buscar especialidades por nombre (paginadas)")
    @GetMapping("/name")
    public ResponseEntity<Page<SpecialtyResponse>> searchByName(
            @RequestParam String name,
            @ParameterObject Pageable pageable){
        log.info("Buscando especialidades con name={}, page={} size={}", 
                name, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(specialtyService.searchByName(name, pageable));
    }

    @Operation(summary = "Listar especialidades por estado (paginadas)")
    @GetMapping("/status/{estado}")
    public ResponseEntity<Page<SpecialtyResponse>> listByStatus(
            @PathVariable String status,
            @ParameterObject Pageable pageable){
        log.info("Listando especialidades con status={}, page={} size={}", 
                status, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(specialtyService.getByStatus(status, pageable));
    }

    @Operation(summary = "Obtener una especialidad por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyResponse> getSpecialtyById(@PathVariable Long id){
        log.info("Buscando especialidad con id={}", id);
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

    @Operation(summary = "Actualizar una especialidad")
    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyResponse> updateSpecialty(
            @PathVariable Long id,
            @Valid @RequestBody SpecialtyRequest request){
        log.info("Solicitud para actualizar especialidad con id={}: {}", id, request);
        SpecialtyResponse response = specialtyService.updateSpecialty(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar una especialidad")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id){
        log.info("Solicitud para eliminar especialidad con id={}", id);
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }

}
