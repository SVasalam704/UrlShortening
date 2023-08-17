package com.project1.urlShortnening.model;

import lombok.Data;

@Data
public class UrlErrorResponseDto {
    private String httpStatus;
    private String statusMsg;
}
