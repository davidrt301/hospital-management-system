package com.davidrt301.medicare.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidrt301.medicare.dto.request.EmployeeRequest;
import com.davidrt301.medicare.dto.response.EmployeeResponse;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.mapper.EmployeeMapper;
import com.davidrt301.medicare.model.Employee;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.repository.EmployeeRepository;
import com.davidrt301.medicare.repository.PersonRepository;
import com.davidrt301.medicare.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Person person = personRepository.findById(request.personId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Persona no encontrada con id: " + request.personId()));

        Employee employee = employeeMapper.toEntity(request);
        employee.setPerson(person);
        employee = employeeRepository.save(employee);

        log.info("Empleado creado. id={}", employee.getId());
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getByStatus(String status, Pageable pageable) {
        Page<Employee> page = employeeRepository.findByStatus(Enum.valueOf(Status.class, status.toUpperCase()),
                pageable);
        return page.map(employeeMapper::toResponse);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));

        if (!employee.getPerson().getId().equals(request.personId())) {
            Person person = personRepository.findById(request.personId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Persona no encontrada con id: " + request.personId()));
            employee.setPerson(person);
        }

        employeeMapper.updateEntity(request, employee);

        Employee updateEmployee = employeeRepository.save(employee);

        log.info("Empleado actualizado. id={}", employee.getId());
        return employeeMapper.toResponse(updateEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado con ID: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Empleado eliminado. id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeResponse> findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponse);
    }

}
