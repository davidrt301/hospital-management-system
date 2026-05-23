package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.EmployeeRequest;
import com.davidrt301.medicare.dto.response.EmployeeResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.Employee;

@Mapper(config = MapperConfiguration.class , uses = {PersonMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "personId",  target = "person.id")
    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee entity);

    @Mapping(source = "personId",  target = "person.id")
    void updateEntity(EmployeeRequest request, @MappingTarget Employee entity);




}
