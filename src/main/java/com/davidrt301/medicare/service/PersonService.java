package com.davidrt301.medicare.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.davidrt301.medicare.dto.request.PersonRequest;
import com.davidrt301.medicare.dto.response.PersonResponse;

public interface PersonService {

    PersonResponse createPerson (PersonRequest request);

    Page<PersonResponse> getAllPersons(Pageable pageable);

    PersonResponse updatePerson(Long id, PersonRequest request);
    
    void deletePerson(Long id);

    Optional<PersonResponse> findByEmail (String email);

    Page<PersonResponse> getByStatus(String status, Pageable pageable);

    Page<PersonResponse> searchByName(String name, Pageable pageable);
    





}
