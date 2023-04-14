package com.java.springilmiofotoalbum.service;

import com.java.springilmiofotoalbum.exceptions.CategoryNotFoundException;
import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Category;
import com.java.springilmiofotoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll(Sort.by("name"));
    }

    public Category create(Category formCategory) {
        Category categoryToCreate = new Category();

        categoryToCreate.setName(formCategory.getName());

        return categoryRepository.save(categoryToCreate);
    }

    public Category update(Category formCategory) {

        return categoryRepository.save(formCategory);
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    // metodo per cancellare tramite l'id una photo
    public boolean deleteById(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(Integer.toString(id)));
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
