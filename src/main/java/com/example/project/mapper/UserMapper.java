package com.example.project.mapper;

import com.example.project.dto.user.UserCreateDTO;
import com.example.project.dto.user.UserEditDTO;
import com.example.project.model.User;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class UserMapper {
    public User userCreateDTOToUser(UserCreateDTO userCreateDTO){
        User user = new User();
        BeanUtils.copyProperties(userCreateDTO, user);
        return user;
    }
    public User userEditDTOToUser(UserEditDTO userEditDTO){
        User user = new User();
        BeanUtils.copyProperties(userEditDTO, user);
        return user;
    }
}
