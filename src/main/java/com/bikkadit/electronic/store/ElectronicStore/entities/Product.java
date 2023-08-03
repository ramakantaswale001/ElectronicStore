package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String productId;

    private String title;

    @Column(length = 10000)
    private String description;

    private Double price;

    private Double discountedPrice;


    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;

    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;



    
}
