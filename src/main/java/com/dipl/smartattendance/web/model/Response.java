package com.dipl.smartattendance.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private Integer status; // Status code (200, 400, 404)

    private T data;

    private Map<String, List<String>> errors;

    private Pagination pagination;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagination{

        private Integer page;
        private Long size;
        private Long totalItems;
    }
}
