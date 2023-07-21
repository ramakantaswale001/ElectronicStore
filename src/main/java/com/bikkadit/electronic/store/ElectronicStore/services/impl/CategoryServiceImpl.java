package com.bikkadit.electronic.store.ElectronicStore.services.impl;


import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helpers.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.CategoryRepo;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        //generate unique id in string format
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = modelMapper.map(categoryDto, Category.class);
        logger.info("Initiated request in service layer for create category");
        Category savedCategory = categoryRepo.save(category);
        logger.info("completed request in service layer for create category");

        CategoryDto categoryDto1 = modelMapper.map(savedCategory, CategoryDto.class);
        return categoryDto1;

    }

    @Override
    public CategoryDto update(CategoryDto userDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found with categoryId: " + categoryId));

        category.setTitle(userDto.getTitle());

        category.setDescription(userDto.getDescription());
        category.setCoverImage(userDto.getCoverImage());
        logger.info("Initiated request in service layer for update category with categoryId :{}",categoryId);
        Category updatedCategory = categoryRepo.save(category);
        logger.info("completed request in service layer for update category with categoryId :{}",categoryId);

        return modelMapper.map(updatedCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        logger.info("Initiated request in service layer for get category with categoryId :{}",categoryId);
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found with categoryId: " + categoryId));
        logger.info("completed request in service layer for get category with categoryId :{}",categoryId);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //pageNumber default start from zero
        //Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);

        logger.info("Initiated request in service layer  for getAllCategory category");
        Page<Category> page = categoryRepo.findAll(pageable);

        logger.info("completed request in service layer for getAllCategory category");

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found with categoryId: " + categoryId));
        logger.info("Initiated request in service layer for delete category with categoryId :{}",categoryId);
        // delete user profile image
        String fullPath = imagePath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (IOException ex){
            logger.info("category image not found in folder ");
            ex.printStackTrace();
        }

        categoryRepo.delete(category);

        logger.info("completed request in service layer for delete category with categoryId :{}",categoryId);
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        logger.info("Initiated request in service layer for search category ");
        List<Category> categories = categoryRepo.findByNameContaining(keyword);
        logger.info("completed request in service layer for search category ");
        List<CategoryDto> categoryDtoList = categories.stream()
                .map((cat) -> modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtoList;
    }
}
