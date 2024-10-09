package com.fabbo.todoapp.modules.user.infrastructure.output.clients;

import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageClient {
    void uploadImageContent(final ApiImage apiImage, MultipartFile imageFile);

    void loadUserImageUrl(final User user);

    void deleteImageContent(final ApiImage image);
}
