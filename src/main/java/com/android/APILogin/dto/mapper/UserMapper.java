package com.android.APILogin.dto.mapper;

import com.android.APILogin.dto.request.UserDtoRequest;
import com.android.APILogin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target="password", ignore = true)
    UserDtoRequest toDTO(User user);

    User toEntity(UserDtoRequest userDtoRequest);

    List<UserDtoRequest> toDTOList(List<User> entityList);

    List<User> toEntityList(List<UserDtoRequest> dtoList);
}
