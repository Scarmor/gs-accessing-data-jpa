package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;

import java.util.List;

/**
 * Сервис для работы с продуктами.
 * Предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * и дополнительных операций, таких как поиск продуктов по имени с поддержкой сортировки.
 *
 * @author Lobov-DI
 */
public interface ProductService {
    /**
     * Получает список всех продуктов.
     *
     * @return Список всех продуктов в формате DTO (ShortProductDto).
     * Если продуктов нет, возвращается пустой список.
     */
    List<ShortProductDto> getAllProducts();

    /**
     * Получает продукт по его уникальному идентификатору (ID).
     *
     * @param id Уникальный идентификатор продукта.
     * @return Продукт в формате DTO (ShortProductDto).
     * @throws RuntimeException Если продукт с указанным ID не найден.
     */
    ShortProductDto getProductById(Long id);

    /**
     * Добавляет новый продукт в систему.
     *
     * @param request DTO с данными нового продукта (AddProductDto).
     *                Должен содержать обязательные поля: название, описание и цену.
     * @return DTO добавленного продукта (ShortProductDto) с присвоенным ID.
     */
    ShortProductDto addProduct(AddProductDto request);

    /**
     * Удаляет продукт из системы по его уникальному идентификатору (ID).
     *
     * @param id Уникальный идентификатор продукта.
     * @throws RuntimeException Если продукт с указанным ID не найден.
     */
    void removeProduct(Long id);

    /**
     * Поиск продуктов по имени с поддержкой сортировки по цене.
     *
     * @param namePart Часть имени продукта.
     * @param ascendingOrder true - сортировка по возрастанию, false - по убыванию.
     * @return Список продуктов в формате DTO.
     */
    List<ShortProductDto> searchProductsByName(String namePart, boolean ascendingOrder);
}
