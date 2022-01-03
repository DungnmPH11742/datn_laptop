package com.poly.vo.response;

import lombok.Data;

@Data
public class ReponseObjectRate {
    private Double star;
    private Double starTextAccount;
    private Double starText;
    private Object data;
    private String message;
    private String errors;
}
