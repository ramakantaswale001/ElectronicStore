package com.bikkadit.electronic.store.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "title is required!!")
    @Size(min = 4,message = "title must of minimum 4 charactor")
    private String title;

    @NotBlank(message = "Description required !!")
    private String description;

    private String coverImage;
}
