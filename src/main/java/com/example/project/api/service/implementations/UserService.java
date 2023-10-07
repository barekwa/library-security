package com.example.project.api.service.implementations;

import com.example.project.dto.user.UserCreateDTO;
import com.example.project.dto.user.UserEditDTO;
import com.example.project.exception.RoleException;
import com.example.project.exception.UserException;
import com.example.project.mapper.UserMapper;
import com.example.project.model.User;
import com.example.project.api.repository.RoleRepository;
import com.example.project.api.repository.UserRepository;
import com.example.project.api.service.interfaces.UserServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class UserService implements UserServiceAPI {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByid(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User findByUsername(String name){
        return userRepository.findByName(name);
    }

    @Override // TODO: 03.10.2023 Check if username is unique
    public User save(UserCreateDTO user) {
        return ofNullable(user)
                .map(UserMapper::userCreateDTOToUser)
                .flatMap(userToAdd ->
                        // TODO: 03.10.2023 Add serching by role name so it looks better
                        roleRepository.findById(2L) //setting default role to reader
                                .map(role -> {
                                    userToAdd.setRole(role);
                                    return userToAdd;
                                })
                )
                .map(userRepository::save)
                .orElseThrow(() -> new UserException("Cannot save user"));
    }

    @Override
    public User update(UserEditDTO user) {
        return userRepository.findById(user.getId())
                .map(userToUpdate -> UserMapper.userEditDTOToUser(user))
                .flatMap(userToUpdate ->
                        roleRepository.findById(userToUpdate.getId())
                                .map(role -> {
                                    userToUpdate.setRole(role);
                                    return userToUpdate;
                                }))
                .map(userRepository::save)
                .orElseThrow(() -> new RoleException("Cannot find role"));
    }
}
