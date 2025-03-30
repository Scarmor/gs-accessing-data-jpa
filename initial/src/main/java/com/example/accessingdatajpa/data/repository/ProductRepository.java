package com.example.accessingdatajpa.data.repository;

import com.example.accessingdatajpa.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с продуктами
 * @author Lobov-DI
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Поиск продуктов по имени с сортировкой по цене.
     *
     * @param namePart Часть имени продукта (чувствительно к регистру).
     * @return Список продуктов, отсортированных по цене.
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :namePart, '%')) ORDER BY p.price")
    List<Product> findByNameContainingAndSortByPrice(@Param("namePart") String namePart);
}
