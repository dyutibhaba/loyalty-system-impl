package com.sii.loyaltysystem.core.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.List;
@Value
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private List<Error> errors;

    public Response(T data) {
        this(data, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, null);
    }

    public static <T> Response<T> failure(List<Response.Error> errors) {
        return new Response<>(null, errors);
    }

    @Value
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {

        private HttpStatus status;
        private Source source;
        private String title;
        private String detail;

        @Value
        public static class Source {
            private String pointer;
        }
    }

}
