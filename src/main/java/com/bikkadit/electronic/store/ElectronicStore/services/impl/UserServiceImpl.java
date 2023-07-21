package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.controllers.UserController;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helpers.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.UserRepo;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = modelMapper.map(userDto, User.class);
        logger.info("Initiated request in service layer for create user");
        User savedUser = userRepo.save(user);
        logger.info("completed request in service layer for create user");

        UserDto userDto1 = modelMapper.map(savedUser, UserDto.class);
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId: " + userId));

        user.setName(userDto.getName());

        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());
        logger.info("Initiated request in service layer for update user with userId :{}",userId);
        User updatedUser = userRepo.save(user);
        logger.info("completed request in service layer for update user with userId :{}",userId);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiated request in service layer for get user with userId :{}",userId);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
        logger.info("completed request in service layer for get user with userId :{}",userId);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiated request in service layer for get user by email with email :{}",email);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email: " + email));
        logger.info("completed request in service layer for get user by email with email :{}",email);

        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //pageNumber default start from zero
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);

        logger.info("Initiated request in service layer  for getAll user");
        Page<User> page = userRepo.findAll(pageable);

        logger.info("completed request in service layer for getAll user");

        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);

        return pageableResponse;
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId: " + userId));
        logger.info("Initiated request in service layer for delete user with userId :{}",userId);
        // delete user profile image
        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (IOException ex){
            logger.info("user image not found in folder ");
            ex.printStackTrace();
        }

        userRepo.delete(user);

        logger.info("completed request in service layer for delete user with userId :{}",userId);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiated request in service layer for search user ");
        List<User> users = userRepo.findByNameContaining(keyword);
        logger.info("completed request in service layer for search user ");
        List<UserDto> userDtoList = users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtoList;
    }
}
