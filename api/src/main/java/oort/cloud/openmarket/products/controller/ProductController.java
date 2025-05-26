package oort.cloud.openmarket.products.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.common.paging.cusor.CursorPageResponse;
import oort.cloud.openmarket.common.paging.cusor.CursorPageRequest;
import oort.cloud.openmarket.products.controller.response.CreateProductResponse;
import oort.cloud.openmarket.products.controller.response.ProductDetailResponse;
import oort.cloud.openmarket.products.controller.request.ProductRequest;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;
import oort.cloud.openmarket.products.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/v1/products")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid ProductRequest request,
                                                               @AccessToken AccessTokenPayload payload){
        return ResponseEntity.ok()
                .body(productsService.createProduct(payload.getUserId(), request));
    }

    @PutMapping("/v1/products/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId,
                                              @RequestBody @Valid ProductRequest request,
                                              @AccessToken AccessTokenPayload payload){
        productsService.updateProduct(payload.getUserId(), productId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        productsService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/products/categories/{categoryId}")
    public ResponseEntity<CursorPageResponse<ProductsResponse>> getCategoryProductList(
            @ModelAttribute @Valid CursorPageRequest request,
            @PathVariable Long categoryId
            ){
        return ResponseEntity.ok()
                .body(productsService.getProductsByCategoryCursorPaging(categoryId, request));
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId){
        return ResponseEntity.ok()
                .body(productsService.getProductDetail(productId));
    }
}
