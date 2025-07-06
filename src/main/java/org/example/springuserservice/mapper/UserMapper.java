package org.example.springuserservice.mapper;

import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);
}