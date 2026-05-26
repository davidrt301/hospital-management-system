package com.davidrt301.medicare.service.imp;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidrt301.medicare.dto.request.PersonRequest;
import com.davidrt301.medicare.dto.response.PersonResponse;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.mapper.PersonMapper;
import com.davidrt301.medicare.model.Person;
import com.davidrt301.medicare.repository.PersonRepository;
import com.davidrt301.medicare.service.PersonService;
import com.davidrt301.medicare.model.Status;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;



    @Override
    public PersonResponse createPerson(PersonRequest request) {
        Person person = personMapper.toEntity(request);
        person = personRepository.save(person);
        return personMapper.toResponse(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonResponse> getAllPersons(Pageable pageable) {
        log.info("Listando personas paginadas, page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return personRepository.findAll(pageable)
                .map(personMapper::toResponse);
        }

    @Override
    public PersonResponse updatePerson(Long id, PersonRequest request) {
        log.info("Actualizar persona cin id= {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada con id: " + id));

        personMapper.updateEntity(request, person);
        person = personRepository.save(person);
        
        return personMapper.toResponse(person);
    }

    @Override
    public void deletePerson(Long id) {
        if(!personRepository.existsById(id)){
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        personRepository.deleteById(id);
        log.info("Persona eliminado. id={}", id);
    }

    @Override
    public Optional<PersonResponse> findByEmail(String email) {
        log.info("Busacamos persona con email= {}", email);
        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("El email es obligatorio");
        }
        return personRepository.findByEmail(email)
                .map(personMapper::toResponse);
    }

    @Override
    public Page<PersonResponse> getByStatus(String status, Pageable pageable) {
        log.info("Listando personas por estado={} paginados", status);
        if(status == null || status.trim().isEmpty()){
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        Status statusEnum = Status.valueOf(status.toUpperCase());
        return personRepository.findByStatus(statusEnum, pageable)
                .map(personMapper::toResponse);
    }

    @Override
    public Page<PersonResponse> searchByName(String name, Pageable pageable) {
        log.info("Buscando personas por nombre= {}", name);
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        return personRepository.searchByName(name, pageable)
                .map(personMapper::toResponse);

    }

}
