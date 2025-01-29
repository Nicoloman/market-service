package com.nqlo.ch.mkt.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.CategoryRepository;
import com.nqlo.ch.mkt.service.repositories.ProductRepository;
import static com.nqlo.ch.mkt.service.utils.UpdateUtils.updateIfChanged;

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
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " couldn't be found"));
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateById(Long id, Product updatedproduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + "couldnt be found"));

        updateIfChanged(updatedproduct.getName(), product::getName, product::setName);
        updateIfChanged(updatedproduct.getPrice(), product::getPrice, product::setPrice);
        updateIfChanged(updatedproduct.getStock(),product::getStock, product::setStock);
        updateIfChanged(updatedproduct.getCategory(),product::getCategory, product::setCategory);
        updateIfChanged(updatedproduct.getDescription(),product::getDescription, product::setDescription);

        return productRepository.save(product);
    }


    @Transactional
    public void deleteById(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product with id: " + id + " couldnt be found");
        }
        productRepository.deleteById(id);
    }

}
