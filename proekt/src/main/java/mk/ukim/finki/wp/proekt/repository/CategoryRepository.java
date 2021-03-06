package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByName(String name);
}
