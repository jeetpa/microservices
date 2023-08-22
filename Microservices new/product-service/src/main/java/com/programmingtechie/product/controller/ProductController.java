package com.programmingtechie.product.controller;

import com.programmingtechie.product.dto.ProductRequest;
import com.programmingtechie.product.dto.ProductResponse;
import com.programmingtechie.product.model.Product;
import com.programmingtechie.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product){
        productService.createProduct(product);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
       return productService.getAllProducts();
    }
}
