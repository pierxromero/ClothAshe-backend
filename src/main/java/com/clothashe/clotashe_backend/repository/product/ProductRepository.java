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
    Page<ProductEntity> findByCategoryId(Long categoryId, Pageable pageable);

    Page<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<ProductEntity> findByStockGreaterThan(Integer stock, Pageable pageable);

    Page<ProductEntity> findByStockIsNullOrStockLessThanEqual(Integer stock, Pageable pageable);

    boolean existsByNameAndCategoryIdAndSizeIdAndColorIdAndBrandId(
            String name, Long categoryId, Long sizeId, Long colorId, Long brandId);
    @Query("""
    SELECT p
    FROM ProductEntity p
    JOIN p.comments c
    GROUP BY p
    ORDER BY AVG(c.validation) DESC
""")
    Page<ProductEntity> findTopRatedProducts(Pageable pageable);
}