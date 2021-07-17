package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.Customer;
import com.dio.everis.dioecommerce.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional(readOnly = true)
    Page<Order> findByCustomer(Customer customer, Pageable pageable);
}
