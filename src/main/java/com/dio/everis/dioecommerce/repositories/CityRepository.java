package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
