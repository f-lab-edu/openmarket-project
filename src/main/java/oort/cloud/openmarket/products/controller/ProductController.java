package oort.cloud.openmarket.products.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.products.controller.request.ProductRequest;
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
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest request,
                                              @AccessToken AccessTokenPayload payload){
        Long productId = productsService.createProduct(payload.getUserId(), request);
        return ResponseEntity.ok(productId);
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
}
