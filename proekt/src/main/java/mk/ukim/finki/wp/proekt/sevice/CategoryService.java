package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Category;

public interface CategoryService {

    Category save (String name, String description);

    Category findById(Integer id);
}
