package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.controllers.UserController;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import com.bikkadit.electronic.store.ElectronicStore.entities.Product;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helpers.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.CategoryRepo;
import com.bikkadit.electronic.store.ElectronicStore.repositories.ProductRepo;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${product.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto create(ProductDto productDto) {
        //generate unique id in string format
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        productDto.setAddedDate(new Date());

        Product product = modelMapper.map(productDto, Product.class);
        productRepo.save(product);
        ProductDto productDto1 = modelMapper.map(product, ProductDto.class);
        return productDto1;
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given id: " + productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.getStock());
        product.setLive(productDto.getLive());
        product.setProductImageName(productDto.getProductImageName());

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given id: " + productId));


        logger.info("Initiated request in service layer for delete product with productId :{}",productId);
        // delete product  image
        String fullPath = imagePath + product.getProductImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (IOException ex){
            logger.info("product image not found in folder ");
            ex.printStackTrace();
        }

        productRepo.delete(product);

        logger.info("completed request in service layer for delete product with productId :{}",productId);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given id: " + productId));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //pageNumber default start from zero
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> page = productRepo.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //pageNumber default start from zero
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> byLiveTrue = productRepo.findByLiveTrue(pageable);
        return Helper.getPageableResponse(byLiveTrue, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {

        //pageNumber default start from zero
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> byLiveTrue = productRepo.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(byLiveTrue, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        //fetch category from db
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with given categoryId; " + categoryId));

        //generate unique id in string format
        String productId = UUID.randomUUID().toString();
        Product product = modelMapper.map(productDto, Product.class);
        product.setProductId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        productRepo.save(product);
        ProductDto productDto1 = modelMapper.map(product, ProductDto.class);
        return productDto1;
    }


}
