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

    @Value("${category.cover.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    // create
    /**
     * @author Ramakant
     * @apiNote This is method for create category1
     * @since v 0.1
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Initiated request in controller layer for create category1");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("completed request in controller layer for create category1");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update
    /**
     * @author Ramakant
     * @apiNote This is method for update category1
     * @since v 0.1
     * @param categoryDto
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        logger.info("Initiated request in controller layer for update category1 with categoryId :{}",categoryId);
        CategoryDto categoryDto1 = categoryService.update(categoryDto, categoryId);
        logger.info("completed request in controller layer for update category1 with categoryId :{}",categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    //  get all
    /**
     * @author Ramakant
     * @apiNote This is method for getAll category1
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        logger.info("Initiated request in controller layer for getAll category1");
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDir);
        logger.info("completed request in controller layer for getAll category1");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }
    // get single
    /**
     * @author Ramakant
     * @apiNote This is method for get category1
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        logger.info(" Initiated request in controller layer for get category1 with categoryId :{}",categoryId);
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        logger.info("completed request in controller layer for get category1 with categoryId :{}",categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    // delete
    /**
     * @author Ramakant
     * @apiNote This is method for delete category1
     * @since v 0.1
     * @param
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId){
        logger.info("Initiated request in controller layer for delete category1 with categoryId :{}",categoryId);
        categoryService.deleteCategory(categoryId);
        logger.info("completed request in controller layer for delete category1 with categoryId :{}",categoryId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("category deleted successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(message ,HttpStatus.OK);
    }
    // search user

    /**
     * @author Ramakant
     * @apiNote This is method for search category1
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> getCategoryBySearch(@PathVariable String keyword){
        logger.info("Initiated request in controller layer for search category1 ");
        List<CategoryDto> categoryDtoList = categoryService.searchCategory(keyword);
        logger.info("completed request in controller layer for search category1 ");
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }

    // upload user image

    @PostMapping("image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam("categoryImage") MultipartFile image,
            @PathVariable String categoryId)
            throws IOException {

        String imageName = fileService.uploadImage(image, imageUploadPath);

        CategoryDto category = categoryService.getCategoryById(categoryId);
        category.setCoverImage(imageName);
        categoryService.update(category,categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    // serve user image

    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        logger.info("Category image name : "+categoryDto.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
