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
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.repository.PersonRepository;
import com.davidrt301.medicare.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;



    @Override
    @Transactional
    public PersonResponse createPerson(PersonRequest request) {
        Person person = personMapper.toEntity(request);
        person.setStatus(Status.ACTIVO);
        person = personRepository.save(person);

        log.info("Persona creada. id={}", person.getId());
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
    @Transactional
    public PersonResponse updatePerson(Long id, PersonRequest request) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + id));

        personMapper.updateEntity(request, person);
        person = personRepository.save(person);

        log.info("Persona actualizada. id={}", person.getId());
        return personMapper.toResponse(person);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Persona no encontrada con id: " + id);
        }
        personRepository.deleteById(id);
        log.info("Persona eliminada. id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonResponse> findByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(personMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonResponse> getByStatus(String status, Pageable pageable) {
        return personRepository.findByStatus(Status.valueOf(status.toUpperCase()), pageable)
                .map(personMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonResponse> searchByName(String name, Pageable pageable) {
        return personRepository.searchByName(name, pageable)
                .map(personMapper::toResponse);
    }

}
