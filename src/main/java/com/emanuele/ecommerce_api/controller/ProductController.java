package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.dto.ProductRequestDTO;
import com.emanuele.ecommerce_api.dto.ProductResponseDTO;
import com.emanuele.ecommerce_api.entity.Category;
import com.emanuele.ecommerce_api.entity.Product;
import com.emanuele.ecommerce_api.service.CategoryService;
import com.emanuele.ecommerce_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController (ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    private ProductResponseDTO toResponse(Product product){
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStockQuantity(product.getStockQuantity());
        responseDTO.setActive(product.getActive());
        responseDTO.setCategoryId(product.getCategory().getId());
        responseDTO.setCategoryName(product.getCategory().getName());

        return responseDTO;
    }
    private Product toEntity(ProductRequestDTO requestDTO, Product product){
        Category category = categoryService.findById(requestDTO.getCategoryId());

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setActive(requestDTO.getActive());
        product.setCategory(category);

        return product;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (Product product : productService.findAll()) {
            responseList.add(toResponse(product));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        return new ResponseEntity<>(toResponse(product), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO requestDTO){
        Product product = toEntity(requestDTO, new Product());
        Product saved = productService.save(product);

        return new ResponseEntity<>(toResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO){
        Product product = productService.findById(id);
        Product update = productService.save(toEntity(requestDTO, product));

        return new ResponseEntity<>(toResponse(update), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

}
