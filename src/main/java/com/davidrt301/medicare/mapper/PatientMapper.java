package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.PatientRequest;
import com.davidrt301.medicare.dto.response.PatientResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.Patient;

@Mapper(config = MapperConfiguration.class, uses = { PersonMapper.class })
public interface PatientMapper {

    @Mapping(source = "personId", target = "person.id")
    Patient toEntity(PatientRequest request);

    PatientResponse toResponse(Patient entity);

    @Mapping(source = "personId", target = "person.id")
    void updateEntity(PatientRequest request, @MappingTarget Patient entity);

}
