package com.bikkadit.electronic.store.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 4,message = "title must of minimum 4 charactor")
    private String title;

    @NotBlank(message = "Description required !!")
    private String description;


    private String coverImage;
}
