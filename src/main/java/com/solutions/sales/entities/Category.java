package com.solutions.sales.entities;

import javax.validation.constraints.*;

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
@Table(name = "CATEGORIES")
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private int id; 

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;
 
}
