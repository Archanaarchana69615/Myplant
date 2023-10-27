package com.example.library.service.imple;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;
import com.example.library.repository.CategoryRepository;
import com.example.library.service.Categoryservice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryserviceImple implements Categoryservice{


    private CategoryRepository repo;


    public Category save(CategoryDto category)
    {
        try {
            Category categorySave = new Category(category.getName());
//            Category category = new Category(category.getDescription());
            return repo.save(categorySave);
        }
            catch(Exception e)
            {
             e.printStackTrace();
             return null;
            }
        }



    @Override
    public Category getById(Long id)
    {
        return repo.getById(id);
    }


    public Category update(Category category) {
        Category categoryUpdate = repo.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        categoryUpdate.setDescription(category.getDescription());
        categoryUpdate.setActivated(category.isActivated());
        categoryUpdate.setDeleted(category.isDeleted());
        return repo.save(categoryUpdate);
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category deleteById(Long id) {
    Category category=repo.getById(id);
    repo.delete(category);
        return category;
    }

    @Override
    public void enableById(Long id) {
        Category category=repo.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        repo.save(category);

    }

    @Override
    public void disableById(Long id) {
        Category category=repo.getById(id);
        category.setActivated(false);
        category.setDeleted(true);
        repo.save(category);

    }

    @Override
    public List<Category> findAllActivatedTrue() {
        return repo.findAllByActivatedTrue();
    }

    @Override
    public boolean existsById(Long id) {
//        System.out.println(existsById(id));
        return repo.existsById(id);
    }

    @Override
    public void saveOrUpdate(Category category) {

    }


    @Override
    public List<Category> findAll() {
       return repo.findAll();
    }
}
