package com.example.accessingdatajpa.controller;

import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для взаимодействия с продуктами
 * @author Lobov-DI
 */
@RequestMapping("/product")
public interface ProductController {
    String GET_ALL_PRODUCTS_URL = "/getAllProducts";
    String GET_PRODUCT_BY_ID_URL = "/getProductById";
    String ADD_PRODUCT_URL = "/addProduct";
    String REMOVE_PRODUCT_URL = "/removeProduct";
    String SEARCH_PRODUCTS_URL = "/searchProducts";

    /**
     * Получить все продукты
     * @return Список продуктов в формате DTO
     */
    @GetMapping(GET_ALL_PRODUCTS_URL)
    ResponseEntity<List<ShortProductDto>> getAllProducts();

    /**
     * Получить продукт по ID
     * @param id ID продукта
     * @return Продукт в формате DTO
     */
    @GetMapping(GET_PRODUCT_BY_ID_URL)
    ResponseEntity<ShortProductDto> getProductById(@RequestParam("id") Long id);

    /**
     * Добавить новый продукт
     * @param request Запрос с данными нового продукта
     * @return Добавленный продукт в формате DTO
     */
    @PostMapping(ADD_PRODUCT_URL)
    ResponseEntity<ShortProductDto> addProduct(@RequestBody AddProductDto request);

    /**
     * Удалить продукт по ID
     * @param id ID продукта
     * @return HTTP-ответ об успешном удалении
     */
    @DeleteMapping(REMOVE_PRODUCT_URL)
    ResponseEntity<Void> removeProduct(@RequestParam("id") Long id);

    /**
     * Поиск продуктов по имени с поддержкой сортировки по цене.
     *
     * @param namePart Часть имени продукта.
     * @param ascendingOrder true - сортировка по возрастанию, false - по убыванию.
     * @return Список продуктов в формате DTO.
     */
    @GetMapping(SEARCH_PRODUCTS_URL)
    ResponseEntity<List<ShortProductDto>> searchProducts(
            @RequestParam("namePart") String namePart,
            @RequestParam("ascendingOrder") boolean ascendingOrder);
}