package com.example.project.api.service.interfaces;

import com.example.project.dto.user.UserCreateDTO;
import com.example.project.dto.user.UserEditDTO;
import com.example.project.model.User;

public interface UserServiceAPI {

    User findByid(Long id);

    User findByUsername(String name);

    User save(UserCreateDTO user);

     User update(UserEditDTO user);
}
