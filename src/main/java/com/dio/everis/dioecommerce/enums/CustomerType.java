package com.dio.everis.dioecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerType {
    PESSOAFISICA(1, "Pessoa Fisica"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private Integer code;
    private String description;

    public static CustomerType toEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (CustomerType x: CustomerType.values()) {
            if(code.equals(x.getCode())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Code invalid: " + code);
    }
}
