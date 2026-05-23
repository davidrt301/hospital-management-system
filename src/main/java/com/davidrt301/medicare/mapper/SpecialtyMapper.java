package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.SpecialtyRequest;
import com.davidrt301.medicare.dto.response.SpecialtyResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.Specialty;

@Mapper(config = MapperConfiguration.class)
public interface  SpecialtyMapper {

    Specialty toEntity(SpecialtyRequest request);

    SpecialtyResponse toResponse(Specialty entity);

    void updateEntity(SpecialtyRequest request, @MappingTarget Specialty entity);

}
