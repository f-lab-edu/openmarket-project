package oort.cloud.openmarket.category.service;

import oort.cloud.openmarket.category.controller.reponse.CategoryTreeResponse;
import oort.cloud.openmarket.category.controller.reponse.CreateCategoryResponse;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.category.repository.CategoryRepository;
import oort.cloud.openmarket.exception.business.NotFoundCategoryException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundCategoryException::new);
    }

    @Transactional
    public CreateCategoryResponse createCategory(CategoryRequest request){
        Category parent = null;
        if(request.getParentId() != null){
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(NotFoundCategoryException::new);
        }
        Category category = Category.of(request.getCategoryName(), parent);
        Category saved = categoryRepository.save(category);
        return new CreateCategoryResponse(saved.getCategoryId());
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundCategoryException::new);

        category.setCategoryName(request.getCategoryName());

        if(request.getParentId() != null){
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(NotFoundCategoryException::new);
            category.setParent(parent);
        }else{
            category.setParent(null);
        }
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }

    public List<CategoryTreeResponse> getCategoryList() {
        List<Category> parent = categoryRepository.findByParentIsNull();
        return parent.stream()
                .map(this::createCategoryTree)
                .collect(Collectors.toList());
    }

    public CategoryTreeResponse getCategorySubList(Long categoryId) {
        Category parent = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundCategoryException::new);
        return createCategoryTree(parent);
    }
    private CategoryTreeResponse createCategoryTree(Category category) {
        return new CategoryTreeResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getChildren().stream()
                        .map(this::createCategoryTree)
                        .collect(Collectors.toList())
        );
    }
}
