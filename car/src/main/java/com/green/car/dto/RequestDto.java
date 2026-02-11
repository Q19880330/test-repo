package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class RequestDto {
    private int page;
    private int size;
    private Long categoryId;
   /* private String searchType;    //검색 타입
    private String keyword;     //검색 단어*/

    public RequestDto(){
        this.page=1;
        this.size=4;
    }

    //import org.springframework.data.domain.Pageable;
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
    }
}
