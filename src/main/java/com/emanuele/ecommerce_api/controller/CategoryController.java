package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.dto.CategoryRequestDTO;
import com.emanuele.ecommerce_api.dto.CategoryResponseDTO;
import com.emanuele.ecommerce_api.entity.Category;
import com.emanuele.ecommerce_api.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> findAll(){
        List<CategoryResponseDTO> responseList = new ArrayList<>();

        for(Category categoryList : categoryService.findAll()){
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setId(categoryList.getId());
            responseDTO.setName(categoryList.getName());
            responseDTO.setDescription(categoryList.getDescription());

            responseList.add(responseDTO);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        //busca
        Category category = categoryService.findById(id);
        //transforma em DTO
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(category.getId());
        responseDTO.setName(category.getName());
        responseDTO.setDescription(category.getDescription());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> save(@RequestBody CategoryRequestDTO dto){
        //DTO request -> Entidade
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        //salva a entidade
        Category saved = categoryService.save(category);

        //Entidade -> DTO response
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(saved.getId());
        responseDTO.setName(saved.getName());
        responseDTO.setDescription(saved.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody CategoryRequestDTO dto){
        Category category = categoryService.findById(id);
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        categoryService.save(category);

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(category.getId());
        responseDTO.setName(category.getName());
        responseDTO.setDescription(category.getDescription());

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
    }
}
