package com.davidrt301.medicare.service.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davidrt301.medicare.dto.request.SpecialtyRequest;
import com.davidrt301.medicare.dto.response.SpecialtyResponse;
import com.davidrt301.medicare.exception.InvalidRequestException;
import com.davidrt301.medicare.exception.ResourceNotFoundException;
import com.davidrt301.medicare.mapper.SpecialtyMapper;
import com.davidrt301.medicare.model.Specialty;
import com.davidrt301.medicare.model.Status;
import com.davidrt301.medicare.repository.SpecialtyRepository;
import com.davidrt301.medicare.service.SpecialtyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    @Transactional
    public SpecialtyResponse createSpecialty(SpecialtyRequest request) {
        Specialty specialty = specialtyMapper.toEntity(request);
        specialty = specialtyRepository.save(specialty);

        log.info("Especialidad creada. id={}", specialty.getId());
        return specialtyMapper.toResponse(specialty);
    }

    @Override
    @Transactional
    public SpecialtyResponse updateSpecialty(Long id, SpecialtyRequest request) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));

        if(request.name() == null || request.name().trim().isEmpty()){
            throw new InvalidRequestException("El nombre de la especialidad es obligatorio");
        }

        specialtyMapper.updateEntity(request, specialty);
        specialty = specialtyRepository.save(specialty);

        log.info("Especialidad actualizada. id={}", specialty.getId());
        return specialtyMapper.toResponse(specialty);
    }

    @Override
    @Transactional
    public void deleteSpecialty(Long id) {
        if(!specialtyRepository.existsById(id)){
            throw new ResourceNotFoundException("Especialidad no encontrada con ID: " + id);
        }
        specialtyRepository.deleteById(id);
        log.info("Especialidad eliminada. id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyResponse getSpecialtyById(Long id) {
        return specialtyRepository.findById(id)
                .map(specialtyMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> getAllSpecialties(Pageable pageable) {
        return specialtyRepository.findAll(pageable)
                .map(specialtyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> searchByName(String name, Pageable pageable) {
        if(name == null || name.trim().isEmpty()){
            throw new InvalidRequestException("El nombre para la búsqueda no puede estar vacío");
        }

        return specialtyRepository.searchByName(name, pageable)
                .map(specialtyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> getByStatus(String status, Pageable pageable) {
        if(status == null || status.trim().isEmpty()){
            throw new InvalidRequestException("El estado no puede estar vacío");
        }

        try {
            Status statusEnum = Status.valueOf(status.toUpperCase());
            return specialtyRepository.findByStatus(statusEnum, pageable)
                    .map(specialtyMapper::toResponse);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Estado inválido: " + status);
        }
    }

}
