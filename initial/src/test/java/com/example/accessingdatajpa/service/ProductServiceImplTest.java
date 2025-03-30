package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.data.entity.Product;
import com.example.accessingdatajpa.mapper.ProductMapper;
import com.example.accessingdatajpa.data.repository.ProductRepository;
import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тест сервиса для взаимодействия с продуктами
 * @author Lobov-DI
 */
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Получение всех продуктов")
    void testGetAllProducts() {
        Product product1 = new Product(1L, "Ноутбук Asus G512", "Ноутбук с современным железом", new BigDecimal(58600.0));
        Product product2 = new Product(2L, "Samsung S25 Ultra", "Телефон samsung последнего поколения", new BigDecimal(120000.0));

        ShortProductDto dto1 = new ShortProductDto(1L, "Ноутбук Asus G512", "Ноутбук с современным железом", new BigDecimal(58600.0));
        ShortProductDto dto2 = new ShortProductDto(2L, "Samsung S25 Ultra", "Телефон samsung последнего поколения", new BigDecimal(120000.0));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toShortProductDto(product1)).thenReturn(dto1);
        when(productMapper.toShortProductDto(product2)).thenReturn(dto2);

        List<ShortProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ноутбук Asus G512", result.get(0).getName());
        assertEquals("Samsung S25 Ultra", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toShortProductDto(product1);
        verify(productMapper, times(1)).toShortProductDto(product2);
    }

    @Test
    @DisplayName("Получение продукта по ID")
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product(productId, "Ноутбук Asus G512", "Ноутбук с современным железом", new BigDecimal(58600.0));
        ShortProductDto dto = new ShortProductDto(productId, "Ноутбук Asus G512", "Ноутбук с современным железом", new BigDecimal(58600.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toShortProductDto(product)).thenReturn(dto);

        ShortProductDto result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Ноутбук Asus G512", result.getName());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toShortProductDto(product);
    }

    @Test
    @DisplayName("Добавление нового продукта")
    void testAddProduct() {
        AddProductDto request = new AddProductDto("Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        Product productEntity = new Product(null, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        Product savedProduct = new Product(3L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        ShortProductDto dto = new ShortProductDto(3L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));

        when(productMapper.toEntity(request)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(savedProduct);
        when(productMapper.toShortProductDto(savedProduct)).thenReturn(dto);

        ShortProductDto result = productService.addProduct(request);

        assertNotNull(result);
        assertEquals("Планшет Apple", result.getName());
        assertEquals(new BigDecimal(94500.0), result.getPrice());
        verify(productMapper, times(1)).toEntity(request);
        verify(productRepository, times(1)).save(productEntity);
        verify(productMapper, times(1)).toShortProductDto(savedProduct);
    }

    @Test
    @DisplayName("Удаление продукта")
    void testRemoveProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.removeProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Поиск продуктов по имени (по возрастанию)")
    void testSearchProductsByName_AscendingOrder() {
        String namePart = "Планш";
        boolean ascendingOrder = true;

        Product product1 = new Product(1L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        Product product2 = new Product(2L, "Планшет Apple Pro", "Еще более мощный планшет", new BigDecimal(156000.0));

        ShortProductDto dto1 = new ShortProductDto(1L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        ShortProductDto dto2 = new ShortProductDto(2L, "Планшет Apple Pro", "Еще более мощный планшет", new BigDecimal(156000.0));

        when(productRepository.findByNameContainingAndSortByPrice(namePart))
                .thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toShortProductDto(product1)).thenReturn(dto1);
        when(productMapper.toShortProductDto(product2)).thenReturn(dto2);

        List<ShortProductDto> result = productService.searchProductsByName(namePart, ascendingOrder);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Планшет Apple", result.get(0).getName());
        assertEquals("Планшет Apple Pro", result.get(1).getName());
        verify(productRepository, times(1)).findByNameContainingAndSortByPrice(namePart);
        verify(productMapper, times(1)).toShortProductDto(product1);
        verify(productMapper, times(1)).toShortProductDto(product2);
    }

    @Test
    @DisplayName("Поиск продуктов по имени (по убыванию)")
    void testSearchProductsByName_DescendingOrder() {
        String namePart = "Планш";
        boolean ascendingOrder = false;

        Product product1 = new Product(1L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        Product product2 = new Product(2L, "Планшет Apple Pro", "Еще более мощный планшет", new BigDecimal(156000.0));

        ShortProductDto dto1 = new ShortProductDto(1L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        ShortProductDto dto2 = new ShortProductDto(2L, "Планшет Apple Pro", "Еще более мощный планшет", new BigDecimal(156000.0));

        when(productRepository.findByNameContainingAndSortByPrice(namePart))
                .thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toShortProductDto(product1)).thenReturn(dto1);
        when(productMapper.toShortProductDto(product2)).thenReturn(dto2);

        List<ShortProductDto> result = productService.searchProductsByName(namePart, ascendingOrder);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Планшет Apple Pro", result.get(0).getName());
        assertEquals("Планшет Apple", result.get(1).getName());
        verify(productRepository, times(1)).findByNameContainingAndSortByPrice(namePart);
        verify(productMapper, times(1)).toShortProductDto(product1);
        verify(productMapper, times(1)).toShortProductDto(product2);
    }

    @Test
    @DisplayName("Поиск продуктов по имени (нет результатов)")
    void testSearchProductsByName_NoResults() {
        String namePart = "Несуществующий";
        boolean ascendingOrder = true;

        when(productRepository.findByNameContainingAndSortByPrice(namePart)).thenReturn(Collections.emptyList());

        List<ShortProductDto> result = productService.searchProductsByName(namePart, ascendingOrder);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findByNameContainingAndSortByPrice(namePart);
        verify(productMapper, never()).toShortProductDto(any());
    }
}