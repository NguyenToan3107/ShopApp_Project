package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories (@RequestParam("page") int page,
                                                    @RequestParam("limit") int limit) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("")
    // if parameter is a object ? => Data Transfer Object = Request Object
    public ResponseEntity<?> createCategory (@Valid @RequestBody CategoryDTO categoryDTO,
                                                  BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("This is insertCategory " + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory (@PathVariable(name = "id") Long id,
                                                  @Valid @RequestBody CategoryDTO categoryDTO) {

        categoryService.updateCategory(id, categoryDTO  );
        return ResponseEntity.ok("update category successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete category with id = " + id);
    }

}
