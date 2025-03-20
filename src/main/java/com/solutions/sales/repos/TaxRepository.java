package com.solutions.sales.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.sales.entities.Tax;


@Repository
public interface TaxRepository extends JpaRepository<Tax, Integer> {

    Optional<Tax> findByName(String name);
}
