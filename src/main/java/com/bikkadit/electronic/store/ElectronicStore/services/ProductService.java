package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
    void delete(String productId);

    // get all
    List<ProductDto> getAll();

    // get single
    ProductDto get(String productId);

    // get all live
    List<ProductDto> getAllLive();

    // search product
    List<ProductDto> searchByTitle(String subTitle);
}
