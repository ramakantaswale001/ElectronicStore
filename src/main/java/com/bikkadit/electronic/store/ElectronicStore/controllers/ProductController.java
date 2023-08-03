package com.bikkadit.electronic.store.ElectronicStore.controllers;

import com.bikkadit.electronic.store.ElectronicStore.dtos.*;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.cover.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    // create
    /**
     * @author Ramakant
     * @apiNote This is method for create Product
     * @since v 0.1
     * @param productDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto){
        logger.info("Initiated request in controller layer for create product");
        ProductDto productDto1 = productService.create(productDto);
        logger.info("completed request in controller layer for create product");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    // update
    /**
     * @author Ramakant
     * @apiNote This is method for update Product
     * @since v 0.1
     * @param productDto
     * @return
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto, @PathVariable String productId){
        logger.info("Initiated request in controller layer for update product with categoryId :{}",productId);
        ProductDto productDto1 = productService.update(productDto, productId);
        logger.info("completed request in controller layer for update product with categoryId :{}",productId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
    }

    // get single
    /**
     * @author Ramakant
     * @apiNote This is method for get Product
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> get(@PathVariable String productId){
        logger.info(" Initiated request in controller layer for get product with categoryId :{}",productId);
        ProductDto productDto = productService.get(productId);
        logger.info("completed request in controller layer for get product with categoryId :{}",productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //  get all
    /**
     * @author Ramakant
     * @apiNote This is method for getAll Product
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        logger.info("Initiated request in controller layer for getAll product");
        PageableResponse<ProductDto> allProduct = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed request in controller layer for getAll product");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        logger.info("Initiated request in controller layer for getAll product");
        PageableResponse<ProductDto> allProduct = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed request in controller layer for getAll product");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    // delete
    /**
     * @author Ramakant
     * @apiNote This is method for delete Product
     * @since v 0.1
     * @param
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId){
        logger.info("Initiated request in controller layer for delete product with productId :{}",productId);
        productService.delete(productId);
        logger.info("completed request in controller layer for delete product with productId :{}",productId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("product deleted successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(message ,HttpStatus.OK);
    }
    // search user

    /**
     * @author Ramakant
     * @apiNote This is method for search Product
     * @since v 0.1
     * @param
     * @return
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Initiated request in controller layer for search product ");
        PageableResponse<ProductDto> productDtoPageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed request in controller layer for search product ");
        return new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);
    }

    // upload user image

//    @PostMapping("image/{categoryId}")
//    public ResponseEntity<ImageResponse> uploadCategoryImage(
//            @RequestParam("categoryImage") MultipartFile image,
//            @PathVariable String productId)
//            throws IOException {
//
//        String imageName = fileService.uploadImage(image, imageUploadPath);
//
//        CategoryDto category = productService.getCategoryById(productId);
//        category.setCoverImage(imageName);
//        productService.update(category,productId);
//
//        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();
//
//        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
//    }
//
//    // serve user image
//
//    @GetMapping("/image/{categoryId}")
//    public void serveCategoryImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
//        CategoryDto categoryDto = productService.getCategoryById(productId);
//        logger.info("Category image name : "+categoryDto.getCoverImage());
//        InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
//
//    }
}
