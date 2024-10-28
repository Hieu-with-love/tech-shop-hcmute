package com.hcmute.tech_shop.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus httpStatus;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestMethod;
    protected Map<?,?> data;
}
