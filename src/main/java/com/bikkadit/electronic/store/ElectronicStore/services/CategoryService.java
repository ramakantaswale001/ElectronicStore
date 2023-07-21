package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;

import java.util.List;


public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto userDto, String categoryId);

    CategoryDto getCategoryById(String categoryId);


    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteCategory(String categoryId);

    List<CategoryDto> searchCategory(String keyword);
}

