package com.nqlo.ch.mkt.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + id + " couldnt be found"));
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateById(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id: " + id + " couldnt be found"));

        category.setDescription(updatedCategory.getDescription());
        category.setName(updatedCategory.getName());

        
        return categoryRepository.save(category);
    }


    @Transactional
    public void deleteById(Long id){
        if(!categoryRepository.existsById(id)){
            throw new IllegalArgumentException("Category with id: " + id + " couldnt be found");
        }
        categoryRepository.deleteById(id);
    }

}
