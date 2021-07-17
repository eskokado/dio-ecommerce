package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
