package oort.cloud.openmarket.category.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;
import oort.cloud.openmarket.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/v1/admin/categories")
    public ResponseEntity<Long> createCategory(@RequestBody @Valid CategoryRequest request){
        Long categoryId = categoryService.createCategory(request);
        return ResponseEntity.ok(categoryId);
    }

    @PutMapping("/v1/admin/categories/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest request){
        categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/admin/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }


}
