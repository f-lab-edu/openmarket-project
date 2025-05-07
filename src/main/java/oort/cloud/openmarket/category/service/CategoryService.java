package oort.cloud.openmarket.category.service;

import oort.cloud.openmarket.exception.business.NotFoundCategoryException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException());
    }

    @Transactional
    public Long createCategory(CategoryRequest request){
        Category parent = null;
        if(request.getParentId() != null){
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundCategoryException());
        }
        Category category = Category.of(request.getCategoryName(), parent);
        return categoryRepository.save(category).getCategoryId();
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategoryException());

        category.setCategoryName(request.getCategoryName());

        if(request.getParentId() != null){
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundCategoryException());
            category.setParent(parent);
        }else{
            category.setParent(null);
        }
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }
}
