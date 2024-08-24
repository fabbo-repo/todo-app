package com.fabbo.todoapp.modules.user.application.usecases;

import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;

public interface GetUserUseCase {
    User getUser(GetUserProps getUserProps);
}
