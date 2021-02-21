package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Category;
import mk.ukim.finki.wp.proekt.repository.CategoryRepository;
import mk.ukim.finki.wp.proekt.sevice.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(String name, String description) {
        if (!name.isEmpty()){
            Category category=new Category(name, description);
            return this.categoryRepository.save(category);
        }
        else {
            //TODO:exception
            return null;
        }
    }

    @Override
    public Category findById(Integer id) {
        if(this.categoryRepository.findById(id).isPresent()){
            return this.categoryRepository.findById(id).get();
        }
        else{
            //TODO: exception
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }
}
