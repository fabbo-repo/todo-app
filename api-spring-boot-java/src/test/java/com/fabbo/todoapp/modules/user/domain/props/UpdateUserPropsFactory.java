package com.fabbo.todoapp.modules.user.domain.props;

import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;

import static com.fabbo.todoapp.common.utils.TestDataUtils.*;

public class UpdateUserPropsFactory {
    public static UpdateUserProps updateUserProps(final String userId) {
        final UpdateUserProps updateUserProps = new UpdateUserProps(
                getMockJwtToken(userId)
        );
        updateUserProps.setUsername(randomText(20));
        updateUserProps.setDescription(randomText(300));
        updateUserProps.setLocale(randomText(5));
        updateUserProps.setImageFile(randomMultipartFile());
        return updateUserProps;
    }
}
