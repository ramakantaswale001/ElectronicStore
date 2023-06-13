package com.bikkadit.electronic.store.ElectronicStore.helpers;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V > PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){

        List<U> entity = page.getContent();
        List<V> list = entity.stream()
                .map(object -> new ModelMapper().map(object,type))
                .collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(list);
        response.setPageNumber(page.getNumber()+1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
