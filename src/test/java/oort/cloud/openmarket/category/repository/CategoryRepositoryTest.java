package oort.cloud.openmarket.category.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import oort.cloud.openmarket.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CategoryRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CategoryService categoryService;

    @Test
    @Transactional
    @DisplayName("N + 1 발생 메서드")
    void n_plus_one_problem(){
        // 부모 카테고리 수 만큼 자식 카테고리 조회 쿼리가 날라감
        categoryService.getCategoryList();

    }
}