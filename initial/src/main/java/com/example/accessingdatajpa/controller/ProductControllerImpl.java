package com.example.accessingdatajpa.controller;

import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import com.example.accessingdatajpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Реализация контроллера для взаимодействия с продуктами
 * @author Lobov-DI
 */
@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ShortProductDto>> getAllProducts() {
        List<ShortProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ShortProductDto> getProductById(@RequestParam("id") Long id) {
        ShortProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<ShortProductDto> addProduct(@RequestBody AddProductDto request) {
        ShortProductDto addedProduct = productService.addProduct(request);
        return ResponseEntity.ok(addedProduct);
    }

    @Override
    public ResponseEntity<Void> removeProduct(@RequestParam("id") Long id) {
        productService.removeProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ShortProductDto>> searchProducts(
            @RequestParam("namePart") String namePart,
            @RequestParam("ascendingOrder") boolean ascendingOrder) {
        List<ShortProductDto> products = productService.searchProductsByName(namePart, ascendingOrder);
        return ResponseEntity.ok(products);
    }
}