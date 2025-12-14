package com.debanjan.e_commerce.entities;

import com.debanjan.e_commerce.utils.AuditableBase;
import com.debanjan.e_commerce.utils.custom_enums.Product_Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity extends AuditableBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true, updatable = false)
    private String productCode;

    @PrePersist
    private void prePersistGenerateProductCode(){
        // Logic to generate product code before saving to DB
        if(this.productCode == null){
            this.productCode = "P" + this.productName.toUpperCase().substring(0, 3) + UUID.randomUUID();
        }
    }

    @Column(name = "product_name", nullable = false, length = 100)
    @Length(min = 3, max = 100)
    private String productName;

    @Column(name = "product_description", nullable = false)
    @Length(max = 255)
    private String productDescription;

    @Column(name = "product_cover_image", nullable = false)
    private String productCoverImage;

    @Column(name = "product_price", nullable = false)
    @Positive
    private BigDecimal productPrice = BigDecimal.ZERO;

    @Column(name = "product_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Product_Category productCategory;

    @Column(name = "product_stock", nullable = false)
    @Positive
    private Integer productStock = 0;

    @Version
    private Long version;
}