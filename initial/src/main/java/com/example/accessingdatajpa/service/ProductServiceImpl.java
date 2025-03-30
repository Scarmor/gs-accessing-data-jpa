package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.data.entity.Product;
import com.example.accessingdatajpa.mapper.ProductMapper;
import com.example.accessingdatajpa.data.repository.ProductRepository;
import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с продуктами
 * @author Lobov-DI
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ShortProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toShortProductDto)
                .toList();
    }

    @Override
    public ShortProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
        return productMapper.toShortProductDto(product);
    }

    @Override
    public ShortProductDto addProduct(AddProductDto request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toShortProductDto(savedProduct);
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ShortProductDto> searchProductsByName(String namePart, boolean ascendingOrder) {
        // Получаем список продуктов из репозитория
        List<Product> products = productRepository.findByNameContainingAndSortByPrice(namePart);

        // Преобразуем продукты в DTO
        List<ShortProductDto> result = products.stream()
                .map(productMapper::toShortProductDto)
                .collect(Collectors.toList());

        // Если ascendingOrder == false, меняем порядок на обратный
        if (!ascendingOrder) {
            Collections.reverse(result);
        }

        return result;
    }
}