package com.davidrt301.medicare.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.davidrt301.medicare.dto.request.PersonRequest;
import com.davidrt301.medicare.dto.response.PersonResponse;
import com.davidrt301.medicare.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    @Operation(summary = "Crear una nueva persona")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona Creada"),
            @ApiResponse(responseCode = "400", description = "Request Inválido")
    })
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request){
        log.info("Solicitud para crear persona recibida: {}", request);
        PersonResponse response = personService.createPerson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas las personas (paginadas)")
    @GetMapping
    public ResponseEntity<Page<PersonResponse>> getAllPersons(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort){
            Pageable pageable = PageRequest.of(
                page, size, Sort.by(sort[0]).ascending());
        log.info("Listando todas las personas, page={} size={} sort={} ", 
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<PersonResponse> persons = personService.getAllPersons(pageable);
        return ResponseEntity.ok(persons);
    }

    @Operation(summary = "Buscar persona por email")
    @GetMapping("/email/{email}")
    public ResponseEntity<PersonResponse> findByEmail(@PathVariable String email){
        log.info("Buscando persona con email={}", email);
        Optional<PersonResponse> person = personService.findByEmail(email);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar personas por estado (paginadas)")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PersonResponse>> getByStatus(
            @PathVariable String status,
            @ParameterObject Pageable pageable){
        log.info("Listando personas con status={}, page={} size={}", 
                status, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(personService.getByStatus(status, pageable));
    }

    @Operation(summary = "Buscar personas por nombre (paginadas)")
    @GetMapping("/search")
    public ResponseEntity<Page<PersonResponse>> searchByName(
            @RequestParam String name,
            @ParameterObject Pageable pageable){
        log.info("Buscando personas por nombre={}, page={} size={}", 
                name, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(personService.searchByName(name, pageable));
    }

    @Operation(summary = "Actualizar una persona")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona Actualizada"),
            @ApiResponse(responseCode = "400", description = "Request Inválido"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PersonRequest request){
        log.info("Solicitud para actualizar persona con id={}: {}", id, request);
        PersonResponse response = personService.updatePerson(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar una persona")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Persona Eliminada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        log.info("Solicitud para eliminar persona con id={}", id);
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}
