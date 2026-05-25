package com.davidrt301.medicare.controller;

import java.util.List;
import java.util.Optional;

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

import com.davidrt301.medicare.dto.request.EmployeeRequest;
import com.davidrt301.medicare.dto.response.EmployeeResponse;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    
    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado Creado"),
            @ApiResponse(responseCode = "400", description = "Request Inválido"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request){
        log.info("Solicitud para crear empleado recibida: {}", request);
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos los empleados")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> listAllEmployees(){
        log.info("Listando todos los empleados");
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Obtener un empleado por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id){
        log.info("Buscando empleado con id={}", id);
        Optional<EmployeeResponse> employees = employeeService.findById(id);
        return employees.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar empleados por estado (paginados)")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<EmployeeResponse>> listByStatus(
            @PathVariable Status status,
            @ParameterObject Pageable pageable){
        log.info("Listando empleados con status={}, page={} size={}", 
                status, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(employeeService.getByStatus(status.name(), pageable));
    }

    @Operation(summary = "Actualizar un empleado")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request){
        log.info("Solicitud para actualizar empleado con id={}: {}", id, request);
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un empleado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        log.info("Solicitud para eliminar empleado con id={}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
