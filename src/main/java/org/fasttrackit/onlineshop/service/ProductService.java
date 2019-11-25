package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exeption.ResourceNotFoundExeption;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    //    IoC-Inversion of Control
    private final ProductRepository productRepository;

    //    Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {} ", request);
        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setName(request.getName());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product: {}", id);
//        using Optional
//        lambda expression
        return productRepository.findById(id).orElseThrow(() ->new ResourceNotFoundExeption
                ("Product " + id + " does not exist."));
    }
}
