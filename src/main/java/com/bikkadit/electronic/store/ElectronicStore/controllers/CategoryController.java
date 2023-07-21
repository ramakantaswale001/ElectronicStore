package com.bikkadit.electronic.store.ElectronicStore.controllers;

import com.bikkadit.electronic.store.ElectronicStore.dtos.*;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    // create
    /**
     * @author Ramakant
     * @apiNote This is method for create category
     * @since v 0.1
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Initiated request in controller layer for create category");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("completed request in controller layer for create category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update
    /**
     * @author Ramakant
     * @apiNote This is method for update category
     * @since v 0.1
     * @param categoryDto
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        logger.info("Initiated request in controller layer for update category with categoryId :{}",categoryId);
        CategoryDto categoryDto1 = categoryService.update(categoryDto, categoryId);
        logger.info("completed request in controller layer for update category with categoryId :{}",categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    //  get all
    /**
     * @author Ramakant
     * @apiNote This is method for getAll category
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        logger.info("Initiated request in controller layer for getAll category");
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDir);
        logger.info("completed request in controller layer for getAll category");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }
    // get single
    /**
     * @author Ramakant
     * @apiNote This is method for get category
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        logger.info(" Initiated request in controller layer for get category with categoryId :{}",categoryId);
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        logger.info("completed request in controller layer for get category with categoryId :{}",categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    // delete
    /**
     * @author Ramakant
     * @apiNote This is method for delete category
     * @since v 0.1
     * @param
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId){
        logger.info("Initiated request in controller layer for delete category with categoryId :{}",categoryId);
        categoryService.deleteCategory(categoryId);
        logger.info("completed request in controller layer for delete category with categoryId :{}",categoryId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("category deleted successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(message ,HttpStatus.OK);
    }
    // search user

    /**
     * @author Ramakant
     * @apiNote This is method for search category
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> getCategoryBySearch(@PathVariable String keyword){
        logger.info("Initiated request in controller layer for search category ");
        List<CategoryDto> categoryDtoList = categoryService.search(keyword);
        logger.info("completed request in controller layer for search category ");
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }

    // upload user image

    @PostMapping("image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId)
            throws IOException {

        String imageName = fileService.uploadImage(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        userService.updateUser(user,userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    // serve user image

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("User image name : "+user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
