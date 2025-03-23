package com.solutions.sales.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

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
