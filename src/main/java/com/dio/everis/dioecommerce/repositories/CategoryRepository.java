package com.dio.everis.dioecommerce.repositories;

import com.dio.everis.dioecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
