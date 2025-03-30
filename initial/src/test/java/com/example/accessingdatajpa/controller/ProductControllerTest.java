package com.example.accessingdatajpa.controller;

import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import com.example.accessingdatajpa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тест контроллера для взаимодействия с продуктами
 * @author Lobov-DI
 */
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductControllerImpl productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Получение всех продуктов")
    void testGetAllProducts() {
        ShortProductDto product1 = new ShortProductDto(1L, "Ноутбук Asus G512", "Ноутбук с современным железом", new BigDecimal(58600.0));
        ShortProductDto product2 = new ShortProductDto(2L, "Samsung S25 Ultra", "Телефон samsung последнего поколения", new BigDecimal(120000.0));
        List<ShortProductDto> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ShortProductDto>> response = productController.getAllProducts();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Ноутбук Asus G512", response.getBody().get(0).getName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Получение продукта по ID")
    void testGetProductById() {
        Long productId = 1L;
        ShortProductDto product = new ShortProductDto(productId, "Ноутбук Asus G512","Ноутбук с современным железом", new BigDecimal(58600.0));

        when(productService.getProductById(productId)).thenReturn(product);

        ResponseEntity<ShortProductDto> response = productController.getProductById(productId);

        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getId());
        assertEquals("Ноутбук Asus G512", response.getBody().getName());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    @DisplayName("Добавление нового продукта")
    void testAddProduct() {
        AddProductDto request = new AddProductDto("Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        ShortProductDto addedProduct = new ShortProductDto(3L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));

        when(productService.addProduct(request)).thenReturn(addedProduct);

        ResponseEntity<ShortProductDto> response = productController.addProduct(request);

        assertNotNull(response.getBody());
        assertEquals("Планшет Apple", response.getBody().getName());
        verify(productService, times(1)).addProduct(request);
    }

    @Test
    @DisplayName("Удаление продукта")
    void testRemoveProduct() {
        Long productId = 1L;

        doNothing().when(productService).removeProduct(productId);

        ResponseEntity<Void> response = productController.removeProduct(productId);

        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).removeProduct(productId);
    }

    @Test
    @DisplayName("Поиск продуктов по имени (по возрастанию)")
    void testSearchProducts() {
        String namePart = "Планш";
        boolean ascendingOrder = true;

        ShortProductDto dto1 = new ShortProductDto(1L, "Планшет Apple", "Новый мощный планшет", new BigDecimal(94500.0));
        ShortProductDto dto2 = new ShortProductDto(2L, "Планшет Apple Pro", "Еще более мощный планшет", new BigDecimal(156000.0));

        when(productService.searchProductsByName(namePart, ascendingOrder))
                .thenReturn(Arrays.asList(dto1, dto2));

        ResponseEntity<List<ShortProductDto>> response = productController.searchProducts(namePart, ascendingOrder);

        assertEquals(2, response.getBody().size());
        assertEquals("Планшет Apple", response.getBody().get(0).getName());
        assertEquals("Планшет Apple Pro", response.getBody().get(1).getName());
    }
}