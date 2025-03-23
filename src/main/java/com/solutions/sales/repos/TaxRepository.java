package com.solutions.sales.repos;

import com.solutions.sales.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Integer> {

    Optional<Tax> findByName(String name);
    List<Tax> findByNameIn(Collection<String> name);
}
