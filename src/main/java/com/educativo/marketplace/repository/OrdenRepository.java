package com.educativo.marketplace.repository;

import com.educativo.marketplace.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioOrderByFechaDesc(Long usuarioId);
    List<Orden> findByProductoId(Long productoId);
    List<Orden> findByEstado(String estado);
}
