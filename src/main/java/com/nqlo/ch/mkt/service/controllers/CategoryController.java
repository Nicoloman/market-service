package com.nqlo.ch.mkt.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.entities.ErrorResponse;
import com.nqlo.ch.mkt.service.exceptions.DuplicateEntryException;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            return ResponseEntity.ok(categories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages.toString(), null, 0)); // 400 Bad Request
        }
        Category newCategory = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory); //201
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            category.setId(id);
            Category updatedCategory = categoryService.updateById(id, category);
            return ResponseEntity.ok(updatedCategory);
    } catch (DuplicateEntryException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), "Bad Request", HttpStatus.BAD_REQUEST.value()));
        } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), "Not Found", HttpStatus.NOT_FOUND.value()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An unexpected error occurred: " + e.getMessage(), "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build(); //204
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage(), "Not Found", HttpStatus.NOT_FOUND.value()));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ErrorResponse("An unexpected error occurred: " + e.getMessage(), "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
    }
}
