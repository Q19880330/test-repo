package com.green.car.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class ResultDto<DTO, EN> {
    private List<DTO> dtoList;  //DTO List
    private int totalPage;  //총 페이지 번호
    private int page;   //현재 페이지 번호
    private int size;   //목록 사이즈
    private int start, end;     //시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next;     //이전, 다음
    private List<Integer> pageList;     //페이지 번호 목록

    public ResultDto(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber()+1; //0부터 시작하므로 1을 추가 (현재 페이지 번호)
        this.size = pageable.getPageSize(); //몇페이지 까지 있는지

        int tempEnd = (int) (Math.ceil(page/10.0))*10;  //1~10페이지까지는 10, 11~20페이지까지는 20

        start = tempEnd-9;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        prev = start > 1;
        next = totalPage>tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());  //1~10,11~20...
    }
}
