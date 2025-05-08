package oort.cloud.openmarket.category.service;

import oort.cloud.openmarket.category.controller.reponse.CreateCategoryResponse;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.category.repository.CategoryRepository;
import oort.cloud.openmarket.data.CategoryRequestTest;
import oort.cloud.openmarket.exception.business.NotFoundCategoryException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category parentCategory;

    @BeforeEach
    void init() {
        parentCategory = Category.of("Parent", null);
        parentCategory.setParent(null);
        parentCategory.setChildren(new ArrayList<>());
        ReflectionTestUtils.setField(parentCategory, "categoryId", 1L);
    }

    @Test
    @DisplayName("카테고리 리스트 조회에 성공한다.")
    void success_findCategoryByIds() {
        Long id = 1L;
        Category category = Category.of("Cat1", null);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = categoryService.findCategoryById(id);

        assertThat(result).isNotNull();
        verify(categoryRepository).findById(id);
    }

    @Test
    @DisplayName("부모 카테고리가 있는 카테고리 저장에 성공한다.")
    void create_category_exsist_parent() {
        CategoryRequest request = new CategoryRequestTest("Child", 1L);

        Category saved = Category.of("Child", parentCategory);
        ReflectionTestUtils.setField(saved, "categoryId", 10L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CreateCategoryResponse result = categoryService.createCategory(request);

        assertThat(result.getCategoryId()).isEqualTo(10L);
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("부모 카테로리가 없는 경우 최상위 카테고리 저장 성공한다.")
    void createCategory_parent_not_exist() {
        CategoryRequest request = new CategoryRequestTest("ROOT", null);

        Category saved = Category.of("ROOT", null);
        ReflectionTestUtils.setField(saved, "categoryId", 11L);

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CreateCategoryResponse result = categoryService.createCategory(request);

        assertThat(result.getCategoryId()).isEqualTo(11L);
        verify(categoryRepository, never()).findById(any());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("부모 카테고리 아이디가 없는 경우 예외 발생")
    void createCategory() {
        CategoryRequest request = new CategoryRequestTest("Invalid", 99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(request))
                .isInstanceOf(NotFoundCategoryException.class)
                .hasMessageContaining(ErrorType.NOT_FOUND_CATEGORY.getMessage());
    }

    @Test
    @DisplayName("카테고리 업데이트에 성공한다.")
    void updateCategory_성공() {
        Category category = Category.of("OldName", null);
        ReflectionTestUtils.setField(category, "categoryId", 2L);
        CategoryRequest request = new CategoryRequestTest("NewName", 1L);

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parentCategory));

        categoryService.updateCategory(2L, request);

        assertThat(category.getCategoryName()).isEqualTo("NewName");
        assertThat(category.getParent()).isEqualTo(parentCategory);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 업데이트시에 NotFound 예외가 발생한다.")
    void fail_update_category() {
        CategoryRequest request = new CategoryRequestTest("NewName", null);

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(999L, request))
                .isInstanceOf(NotFoundCategoryException.class);
    }

    @Test
    @DisplayName("카테고리 삭제에 성공한다.")
    void success_delete() {
        categoryService.deleteCategory(3L);

        verify(categoryRepository).deleteByCategoryId(3L);
    }

}