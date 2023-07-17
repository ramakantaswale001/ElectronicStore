package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {


    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String email);
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteUser(String userId);

    List<UserDto> searchUser(String keyword);
}
