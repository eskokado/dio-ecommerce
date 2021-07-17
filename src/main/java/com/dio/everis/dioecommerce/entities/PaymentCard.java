package com.dio.everis.dioecommerce.entities;

import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PAYMENT_CARD")
public class PaymentCard extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer parcelAmount;

    public PaymentCard(Long id, PaymentStatus paymentStatus, Order order, Integer parcelAmount) {
        super(id, paymentStatus, order);
        this.parcelAmount = parcelAmount;
    }

    public Integer getParcelAmount() {
        return parcelAmount;
    }

    public void setParcelAmount(Integer parcelAmount) {
        this.parcelAmount = parcelAmount;
    }
}
