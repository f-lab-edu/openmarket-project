package oort.cloud.openmarket.products.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import oort.cloud.openmarket.category.service.CategoryService;
import oort.cloud.openmarket.common.cusor.Cursor;
import oort.cloud.openmarket.common.cusor.CursorPageResponse;
import oort.cloud.openmarket.common.cusor.CursorPageRequest;
import oort.cloud.openmarket.common.cusor.CursorUtil;
import oort.cloud.openmarket.exception.auth.UnauthorizedAccessException;
import oort.cloud.openmarket.exception.business.NotFoundProductException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.products.controller.response.CreateProductResponse;
import oort.cloud.openmarket.products.controller.response.ProductDetailResponse;
import oort.cloud.openmarket.products.controller.request.ProductRequest;
import oort.cloud.openmarket.category.entity.Category;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;
import oort.cloud.openmarket.products.entity.ProductCategory;
import oort.cloud.openmarket.products.entity.Products;
import oort.cloud.openmarket.products.enums.ProductCursorStrategy;
import oort.cloud.openmarket.products.enums.ProductsCursorField;
import oort.cloud.openmarket.products.enums.ProductsStatus;
import oort.cloud.openmarket.products.repository.ProductsRepository;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CursorUtil cursorUtil;
    public ProductsService(ProductsRepository productsRepository, CategoryService categoryService, UserService userService, CursorUtil cursorUtil) {
        this.productsRepository = productsRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cursorUtil = cursorUtil;
    }

    @Transactional
    public CreateProductResponse createProduct(Long userId, ProductRequest request) {
        Users user = userService.findUserEntityById(userId);
        Products product = Products.create(
                request.getProductName(),
                user,
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );

        Category category = categoryService.findCategoryById(request.getCategoryId());
        ProductCategory productCategory = ProductCategory.of(product, category, LocalDateTime.now());
        product.addProductCategories(productCategory);

        return new CreateProductResponse(productsRepository.save(product).getProductId());
    }

    @Transactional
    public void updateProduct(Long userId, Long productId, ProductRequest request){
        Products product = productsRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);

        if(userId.equals(product.getUser().getUserId()))
            throw new UnauthorizedAccessException(ErrorType.UNAUTHORIZED_ACCESS);

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.getProductCategories().clear();

        Category category = categoryService.findCategoryById(request.getCategoryId());
        ProductCategory productCategory = ProductCategory.of(product, category, LocalDateTime.now());
        product.getProductCategories().add(productCategory);
    }

    @Transactional
    public void deleteProduct(Long productId){
        Products product = productsRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);
        product.setStatus(ProductsStatus.DELETED);
    }

    public CursorPageResponse<ProductsResponse> getProductsByCategoryCursorPaging(Long categoryId, CursorPageRequest request){
        String sortKeyString = request.getSortKey();
        String cursorData = request.getCursor();
        int size = request.getSize();

        ProductCursorStrategy sortKey = ProductCursorStrategy.getSearchSortKey(sortKeyString);
        List<OrderSpecifier<?>> orderSpecifiers = sortKey.getOrderSpecifiers();
        
        // 커서 조건 데이터 확인 없으면 null 리턴
        Cursor<ProductsCursorField> cursor = cursorUtil.decodeCursor(cursorData, ProductsCursorField.class);
        
        Optional<BooleanExpression> cursorCondition = sortKey.getBooleanExpression(cursor);

        List<ProductsResponse> contents = productsRepository.findByCategoryWithSortAndCursor(
                categoryId,
                cursorCondition.orElse(null),
                orderSpecifiers,
                size
        );
        // 다음 커서 생성
        Optional<String> nextCursor = getNextCursor(sortKey, contents);
        
        return new CursorPageResponse<>(contents, nextCursor.orElse(null));
    }

    private Optional<String> getNextCursor(ProductCursorStrategy sortKey, List<ProductsResponse> contents) {
        if(contents.isEmpty()){
            return Optional.empty();
        }

        ProductsResponse last = contents.get(contents.size() - 1);
        Cursor<ProductsCursorField> next = new Cursor<>();
        
        sortKey.getCursorFields().forEach(field -> {
            next.put(field, field.extract(last));
        });

        return Optional.of(cursorUtil.createCursor(next));
    }

    public ProductDetailResponse getProductDetail(Long productId) {
        return productsRepository.findById(productId)
                .map(ProductDetailResponse::new)
                .orElseThrow(NotFoundProductException::new);
    }
}
