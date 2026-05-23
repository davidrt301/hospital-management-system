package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.MedicalSpecialtyRequest;
import com.davidrt301.medicare.dto.response.MedicalSpecialtyResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.MedicalSpecialty;

@Mapper(config = MapperConfiguration.class, uses = {EmployeeMapper.class, SpecialtyMapper.class})
public interface MedicalSpecialtyMapper {

    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "specialtyId", target = "specialty.id")
    MedicalSpecialty toEntity(MedicalSpecialtyRequest request);

    MedicalSpecialtyResponse toResponse(MedicalSpecialty entity);

    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "specialtyId", target = "specialty.id")
    void updateEntity(MedicalSpecialtyRequest request, @MappingTarget MedicalSpecialty entity);

}
