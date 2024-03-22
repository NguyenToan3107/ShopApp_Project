package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.UpdateCategoryResponse;
import com.project.shopapp.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam("page") int page,
                                                           @RequestParam("limit") int limit) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("")
    // if parameter is a object ? => Data Transfer Object = Request Object
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result) {
        if (result.hasErrors()) {
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
    public ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable(name = "id") Long id,
                                                                 @Valid @RequestBody CategoryDTO categoryDTO,
                                                                 HttpServletRequest request) {

        categoryService.updateCategory(id, categoryDTO);
        Locale locale = localeResolver.resolveLocale(request);

        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                .message(messageSource.getMessage("category.update_category.update_successfully", null, locale))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete category with id = " + id);
    }

}
