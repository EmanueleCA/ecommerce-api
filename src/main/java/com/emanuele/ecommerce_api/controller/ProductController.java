package com.emanuele.ecommerce_api.controller;


import com.emanuele.ecommerce_api.entity.Product;
import com.emanuele.ecommerce_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@RequestBody Product product, @PathVariable Long id){
        product.setId(id);
        return productService.save(product);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

}
