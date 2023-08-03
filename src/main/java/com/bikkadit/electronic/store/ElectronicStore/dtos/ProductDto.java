package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

    private String productId;

    private String title;

    private String description;

    private Double price;

    private Double discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;

    private String productImageName;

    private CategoryDto category;
}
