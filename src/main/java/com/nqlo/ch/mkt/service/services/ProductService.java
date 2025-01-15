package com.nqlo.ch.mkt.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.repositories.CategoryRepository;
import com.nqlo.ch.mkt.service.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + id + "couldnt be found"));
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateById(Long id, Product updatedproduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id: " + id + "couldnt be found"));

        product.setPrice(updatedproduct.getPrice());
        product.setStock(updatedproduct.getStock());
        product.setCategory(updatedproduct.getCategory());
        product.setDescription(updatedproduct.getDescription());

        if (product.getName() == null) {
            product.setName(updatedproduct.getName());
        }

        return productRepository.save(product);
    }


    @Transactional
    public void deleteById(Long id){
        if(!productRepository.existsById(id)){
            throw new IllegalArgumentException("Product with id: " + id + "couldnt be found");
        }
        productRepository.deleteById(id);
    }

}
