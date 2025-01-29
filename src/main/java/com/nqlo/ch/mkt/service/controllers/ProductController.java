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

import com.nqlo.ch.mkt.service.dto.ProductDTO;
import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.entities.ErrorResponse;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.services.CategoryService;
import com.nqlo.ch.mkt.service.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getProducts();
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDTO ProductDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages.toString(), null, 0)); // 400 Bad Request
        }
        Category category = categoryService.findById(ProductDTO.getCategoryId());

        // Crear el producto y asignar la categoría
        Product product = new Product();
        product.setName(ProductDTO.getName());
        product.setDescription(ProductDTO.getDescription());
        product.setCategory(category); // Asignar la categoría encontrada
        product.setPrice(ProductDTO.getPrice());
        product.setStock(ProductDTO.getStock());
        Product newProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct); // 201 Created

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product product = new Product();

            product.setId(id); // Asignar el id del producto a actualizar
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStock(productDTO.getStock());

            // Buscar la categoría por ID
            if(productDTO.getCategoryId() != null){
                Category category = categoryService.findById(productDTO.getCategoryId());
                product.setCategory(category); // Asignar la categoría encontrada   
            }


            // Actualizar el producto
            Product updatedProduct = productService.updateById(id, product);
            return  ResponseEntity.ok(updatedProduct);
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), "Not Found", HttpStatus.NOT_FOUND.value()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("An unexpected error occurred: " + e.getMessage(), "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
            }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteById(id);
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
