package com.bikkadit.electronic.store.ElectronicStore.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private Boolean lastPage;



}
