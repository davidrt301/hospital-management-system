package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.PersonRequest;
import com.davidrt301.medicare.dto.response.PersonResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.Person;

@Mapper(config = MapperConfiguration.class)
public interface PersonMapper {

    Person toEntity(PersonRequest request);

    PersonResponse toResponse(Person entity);

    void updateEntity(PersonRequest request, @MappingTarget Person entity);

}
