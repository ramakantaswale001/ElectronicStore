package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.controllers.UserController;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import com.bikkadit.electronic.store.ElectronicStore.repositories.UserRepo;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        logger.info("Initiated request  for create user");
        User savedUser = userRepo.save(user);
        logger.info("completed request  for create user");

        UserDto userDto1 = modelMapper.map(savedUser, UserDto.class);
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with userId: " + userId));

        user.setName(userDto.getName());

        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());
        logger.info("Initiated request  for update user with userId :{}",userId);
        User updatedUser = userRepo.save(user);
        logger.info("completed request for update user with userId :{}",userId);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiated request  for get user with userId :{}",userId);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with userId: " + userId));
        logger.info("completed request for get user with userId :{}",userId);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiated request  for get user by email with email :{}",email);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found with email: " + email));
        logger.info("completed request for get user by email with email :{}",email);

        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Initiated request  for getAll user");
        List<User> allUsers = userRepo.findAll();
        logger.info("completed request for getAll user");
        List<UserDto> list = allUsers.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with userId: " + userId));
        logger.info("Initiated request  for delete user with userId :{}",userId);
        userRepo.delete(user);
        logger.info("completed request for delete user with userId :{}",userId);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiated request  for search user ");
        List<User> users = userRepo.findByNameContaining(keyword);
        logger.info("completed request for search user ");
        List<UserDto> userDtoList = users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtoList;
    }
}
