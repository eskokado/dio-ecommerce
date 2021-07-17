package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
