package com.dio.everis.dioecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer code;
    private String description;

    public static PaymentStatus toEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (PaymentStatus x: PaymentStatus.values()) {
            if(code.equals(x.getCode())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Code invalid: " + code);
    }
}
