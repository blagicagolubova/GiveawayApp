package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Category;

import java.util.List;

public interface CategoryService {

    Category save (String name, String description);

    Category findById(Integer id);

    List<Category> findAll();

    Category findByName(String name);
}
