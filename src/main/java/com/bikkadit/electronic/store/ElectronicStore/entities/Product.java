package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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


    
}
