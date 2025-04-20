package oort.cloud.openmarket.category.repository;

import oort.cloud.openmarket.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteByCategoryId(Long categoryId);
}
