package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
}
