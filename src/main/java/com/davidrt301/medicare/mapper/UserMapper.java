package com.davidrt301.medicare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.davidrt301.medicare.dto.request.UserRequest;
import com.davidrt301.medicare.dto.response.UserResponse;
import com.davidrt301.medicare.mapper.config.MapperConfiguration;
import com.davidrt301.medicare.model.User;

@Mapper(config = MapperConfiguration.class, uses = { PersonMapper.class })
public interface UserMapper {

    @Mapping(source = "personId", target = "person.id")
    User toEntity(UserRequest request);

    UserResponse toResponse(User entity);

    @Mapping(source = "personId", target = "person.id")
    void updateEntity(UserRequest request, @MappingTarget User entity);


}
