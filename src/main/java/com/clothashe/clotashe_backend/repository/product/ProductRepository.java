package com.clothashe.clotashe_backend.repository.product;

import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryId(Long categoryId);
    List<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice);
    List<ProductEntity> findByStockGreaterThan(Integer stock);
    List<ProductEntity> findByStockIsNullOrStockLessThanEqual(Integer stock);


    @Query("""
    SELECT p
    FROM ProductEntity p
    JOIN p.comments c
    GROUP BY p
    ORDER BY AVG(c.validation) DESC
""")
    Page<ProductEntity> findTopRatedProducts(Pageable pageable);
}