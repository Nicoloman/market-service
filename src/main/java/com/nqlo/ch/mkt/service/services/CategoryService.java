package com.nqlo.ch.mkt.service.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.exceptions.DuplicateEntryException;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.CategoryRepository;
import static com.nqlo.ch.mkt.service.utils.UpdateUtils.updateIfChanged;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Validator validator; // Inyección del validador de JSR-303

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " couldnt be found"));
    }

    @Transactional
    public Category save(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new DuplicateEntryException("Name ", "Category with name: '" + category.getName() + "' already exists.");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateById(Long id, Category updatedCategory) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " couldnt be found"));

        if (updatedCategory.getName() != null && !updatedCategory.getName().isEmpty()) {
            Set<ConstraintViolation<Category>> violations = validator.validateValue(Category.class, "name", updatedCategory.getName());
            if (!violations.isEmpty()) {
                throw new IllegalArgumentException("Name '" + updatedCategory.getName() + "' is not valid.");
            }
            if (categoryRepository.existsByName(updatedCategory.getName()) && !category.getName().equals(updatedCategory.getName())) {
                throw new DuplicateEntryException("name", "category named'" + updatedCategory.getName() + "' already exists");
            }
        }
        updateIfChanged(updatedCategory.getName(), category::getName, category::setName);
        updateIfChanged(updatedCategory.getDescription(), category::getDescription, category::setDescription);
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id: " + id + " couldnt be found");
        }
        categoryRepository.deleteById(id);
    }

}
