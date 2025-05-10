package oort.cloud.openmarket.category.service;

import oort.cloud.openmarket.category.controller.reponse.CategoryResponse;
import oort.cloud.openmarket.category.controller.reponse.CreateCategoryResponse;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.category.repository.CategoryQueryDslRepository;
import oort.cloud.openmarket.category.repository.CategoryRepository;
import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.common.paging.offset.OffsetPageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryQueryDslRepository categoryQueryDslRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryQueryDslRepository categoryQueryDslRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryQueryDslRepository = categoryQueryDslRepository;
    }

    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundResourceException::new);
    }

    @Transactional
    public CreateCategoryResponse createCategory(CategoryRequest request){
        Category parent = null;
        if(request.getParentId() != null){
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(NotFoundResourceException::new);
        }
        Category category = Category.of(request.getCategoryName(), parent);
        Category saved = categoryRepository.save(category);
        return new CreateCategoryResponse(saved.getCategoryId());
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundResourceException::new);

        category.setCategoryName(request.getCategoryName());

        if(request.getParentId() != null){
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(NotFoundResourceException::new);
            category.setParent(parent);
        }else{
            category.setParent(null);
        }
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }

    public OffsetPageResponse<CategoryResponse> getRootCategoryList(Pageable pageable) {
        return categoryQueryDslRepository.findByParentIsNull(null, pageable);
    }

    public OffsetPageResponse<CategoryResponse> getSubCategoryList(Long categoryId, Pageable pageable) {
        return categoryQueryDslRepository.findByParentIsNull(categoryId, pageable);
    }

}
