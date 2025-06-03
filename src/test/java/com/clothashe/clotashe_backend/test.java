package com.clothashe.clotashe_backend;

import com.clothashe.clotashe_backend.mapper.product.ProductMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class test {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void testToEntityMapping_withManualRelations() {
        // Crear los objetos relacionados simulados
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Category Example");

        BrandEntity brand = new BrandEntity();
        brand.setId(4L);
        brand.setName("Brand Example");

        SizeEntity size = new SizeEntity();
        size.setId(2L);
        size.setCode("M");

        ColorEntity color = new ColorEntity();
        color.setId(3L);
        color.setName("Red");
        System.out.println(category);
        System.out.println(brand);
        System.out.println(size);
        System.out.println(color);
        // Crear DTO
        CreateProductRequestDTO dto = new CreateProductRequestDTO();
        dto.setName("Test Product");
        dto.setDescription("Desc");
        dto.setPrice(BigDecimal.valueOf(9.99));
        dto.setStock(10);
        dto.setIsActive(true);
        dto.setCategoryId(category.getId());
        dto.setSizeId(size.getId());
        dto.setColorId(color.getId());
        dto.setBrandId(brand.getId());
        System.out.println(dto);
        // Mapear DTO a entidad
        ProductEntity entity = productMapper.toEntity(dto);

        // Setear manualmente las entidades relacionadas (como haría el service)
        entity.setCategory(category);
        entity.setBrand(brand);
        entity.setSize(size);
        entity.setColor(color);
        System.out.println(entity);
        // Validaciones básicas
        assertEquals("Test Product", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals(BigDecimal.valueOf(9.99), entity.getPrice());
        assertEquals(10, entity.getStock());
        assertTrue(entity.getIsActive());

        assertNotNull(entity.getCategory());
        assertEquals(1L, entity.getCategory().getId());

        assertNotNull(entity.getBrand());
        assertEquals(4L, entity.getBrand().getId());

        assertNotNull(entity.getSize());
        assertEquals(2L, entity.getSize().getId());

        assertNotNull(entity.getColor());
        assertEquals(3L, entity.getColor().getId());
    }
}