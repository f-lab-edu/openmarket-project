package oort.cloud.openmarket.products.service;

import oort.cloud.openmarket.category.service.CategoryService;
import oort.cloud.openmarket.exception.auth.UnauthorizedAccessException;
import oort.cloud.openmarket.exception.business.NotFoundProductException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.products.controller.request.ProductRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.products.entity.ProductCategory;
import oort.cloud.openmarket.products.entity.Products;
import oort.cloud.openmarket.products.enums.ProductsEnum;
import oort.cloud.openmarket.products.repository.ProductsRepository;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    public ProductsService(ProductsRepository productsRepository, CategoryService categoryService, UserService userService) {
        this.productsRepository = productsRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Transactional
    public Long createProduct(Long userId, ProductRequest request) {
        Users user = userService.findUserEntityById(userId);
        Products product = Products.create(
                request.getProductName(),
                user,
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );

        Category category = categoryService.findCategoryById(request.getCategoryId());
        ProductCategory productCategory = ProductCategory.of(product, category);
        product.addProductCategories(productCategory);

        return productsRepository.save(product).getProductId();
    }

    @Transactional
    public void updateProduct(Long userId, Long productId, ProductRequest request){
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new NotFoundProductException(ErrorType.NOT_FOUND_PRODUCT));

        if(userId.equals(product.getUser().getUserId()))
            throw new UnauthorizedAccessException(ErrorType.UNAUTHORIZED_ACCESS);

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.getProductCategories().clear();

        Category category = categoryService.findCategoryById(request.getCategoryId());
        ProductCategory productCategory = ProductCategory.of(product, category);
        product.getProductCategories().add(productCategory);
    }

    @Transactional
    public void deleteProduct(Long productId){
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new NotFoundProductException(ErrorType.NOT_FOUND_PRODUCT));
        product.setStatus(ProductsEnum.DELETED);
    }

}
