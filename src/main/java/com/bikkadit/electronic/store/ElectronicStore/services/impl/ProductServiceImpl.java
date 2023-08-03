package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.Product;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.repositories.ProductRepo;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto create(ProductDto productDto) {
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

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given id: " + productId));
        productRepo.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found of given id: " + productId));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
    }



    @Override
    public List<ProductDto> getAllLive() {
        return null;
    }

    @Override
    public List<ProductDto> searchByTitle(String subTitle) {
        return null;
    }
}
