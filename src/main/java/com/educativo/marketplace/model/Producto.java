package com.educativo.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3 , max =120)
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatorio")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "El precio es obligatorio")
    @DecimalMin(value="0.01", message="El precio debe ser mayor a Cero")
    @Column(nullable = false)
    private Double precio;

    @NotBlank(message = "El stock es obligatorio")
    @Size(min = 1, max = 10000)
    @Column(nullable = false)
    private Integer stock;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;

    private String imagenUrl;

    @Column(nullable = false)
    private boolean activo = true;

}
