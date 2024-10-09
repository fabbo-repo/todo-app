package com.fabbo.todoapp.modules.user.application.usecases;

import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;

public interface UpdateUserUseCase {
    Void updateUser(UpdateUserProps updateUserDto);
}
