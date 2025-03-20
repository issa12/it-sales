package com.solutions.sales.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "PRODUCTS")
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category Category;
}
