package com.dio.everis.dioecommerce.entities;

import com.dio.everis.dioecommerce.enums.PaymentStatus;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENTS_SLIPS")
public class PaymentSlip extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate dueDate;
    private LocalDate paymentDate;

    @Builder
    public PaymentSlip(Long id, PaymentStatus paymentStatus, Order order,
                       LocalDate dueDate, LocalDate paymentDate) {
        super(id, paymentStatus, order);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
