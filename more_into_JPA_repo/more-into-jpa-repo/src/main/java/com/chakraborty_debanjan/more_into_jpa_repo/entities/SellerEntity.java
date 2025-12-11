package com.chakraborty_debanjan.more_into_jpa_repo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "registered_sellers"
)
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sellerId;

    @Length(min = 3, max = 255)
    @Column(nullable = false, unique = true, length = 255, updatable = false)
    private String sellerName;

    @OneToMany(
            mappedBy = "seller",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private Set<ProductEntity> productsListedBySeller = new HashSet<>();
}
