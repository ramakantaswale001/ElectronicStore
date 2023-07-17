package com.bikkadit.electronic.store.ElectronicStore.controllers;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    // create
    /**
     * @author Ramakant
     * @apiNote This is method for create user
     * @since v 0.1
     * @param userDto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Initiated request in controller layer for create user");
        UserDto userDto1 = userService.createUser(userDto);
        logger.info("completed request in controller layer for create user");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    // update
    /**
     * @author Ramakant
     * @apiNote This is method for update user
     * @since v 0.1
     * @param userDto
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId){
        logger.info("Initiated request in controller layer for update user with userId :{}",userId);
        UserDto userDto1 = userService.updateUser(userDto, userId);
        logger.info("completed request in controller layer for update user with userId :{}",userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }
   //  get all
    /**
     * @author Ramakant
     * @apiNote This is method for getAll user
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        logger.info("Initiated request in controller layer for getAll user");
        PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        logger.info("completed request in controller layer for getAll user");
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
    // get single
    /**
     * @author Ramakant
     * @apiNote This is method for get user
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        logger.info(" Initiated request in controller layer for get user with userId :{}",userId);
        UserDto userById = userService.getUserById(userId);
        logger.info("completed request in controller layer for get user with userId :{}",userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
     //get by email
    /**
     * @author Ramakant
     * @apiNote This is method for get user by email
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        logger.info("Initiated request in controller layer for get user by email with email :{}",email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("completed request in controller layer for get user by email with email :{}",email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }
    // delete
    /**
     * @author Ramakant
     * @apiNote This is method for delete user
     * @since v 0.1
     * @param
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        logger.info("Initiated request in controller layer for delete user with userId :{}",userId);
       userService.deleteUser(userId);
        logger.info("completed request in controller layer for delete user with userId :{}",userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("user deleted successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(message ,HttpStatus.OK);
    }
    // search user

    /**
     * @author Ramakant
     * @apiNote This is method for search user
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUserBySearch(@PathVariable String keyword){
        logger.info("Initiated request in controller layer for search user ");
        List<UserDto> userDtoList = userService.searchUser(keyword);
        logger.info("completed request in controller layer for search user ");
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }
}
