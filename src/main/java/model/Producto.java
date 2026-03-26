package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 120)
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @NotBlank(message = "La categoria es obligatoria")
    @Column(nullable = false)
    private String categoria;

    private String imagenUrl;

    @Column(nullable = false)
    private Boolean activo = true;
}
