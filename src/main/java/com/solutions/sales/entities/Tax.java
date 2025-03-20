package com.solutions.sales.entities;

import java.math.BigDecimal;
import javax.validation.constraints.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TAXES")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@ToString
public class Tax {

    @Id
    private Integer id;
    private String name;

    @NotNull
    private BigDecimal rate;
}
