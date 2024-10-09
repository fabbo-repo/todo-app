package com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.mappers;

import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.responses.AccountRestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountRestMapper {
    AccountRestMapper INSTANCE = Mappers.getMapper(AccountRestMapper.class);

    AccountRestResponse userToResponse(final User user);
}
