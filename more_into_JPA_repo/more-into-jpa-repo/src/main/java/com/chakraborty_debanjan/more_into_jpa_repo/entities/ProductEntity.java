package com.chakraborty_debanjan.more_into_jpa_repo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_product_name", columnNames = {"product_name"})
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "product_sku")
    @Length(min = 10, max = 30)
    private String sku;

    @Column(nullable = false)
    private String product_cover_image;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "product_image_url")
    private List<String> product_images;

    @PrePersist
    void prePersist() {
        // add a cover image to a product images list if nothing else was added
        if (product_images.isEmpty() && product_cover_image != null) {
            product_images.add(product_cover_image);
        }
    }


    @Length(min = 3, max = 255)
    @Column(nullable = false, unique = true, length = 255, updatable = true)
    @Builder.Default
    private String product_name = "Not specified"; // default value

    @Length(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    @Builder.Default
    private String short_description = "Not specified"; // default value

    @Length(min = 3, max = 1000)
    @Column(nullable = false, length = 1000)
    @Builder.Default
    private String long_description = "Not specified"; // default value

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;

    @Column(nullable = false, name = "product_price", updatable = true, precision = 10, scale = 3)
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO; // default value

    @Column(nullable = false, name = "product_quantity", updatable = true)
    @JsonIgnore // exclude from json output
    @Builder.Default
    private int quantity = 0; // default value

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;
}
