package com.security.bearer.dto;

import java.io.Serializable;

public class ResponseDTO<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private T dto;

    public ResponseDTO(T dto) {
        super();
        this.dto = dto;
    }

    public ResponseDTO() {
        super();
    }

    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

}
