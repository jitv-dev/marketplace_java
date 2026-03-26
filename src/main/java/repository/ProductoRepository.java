package repository;

import model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue(Boolean activo);

    List<Producto> findByCategoriaAndActivoTrue(String categoria, Boolean activo);

    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre, Boolean activo);

    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.activo = true ORDER BY  p.categoria")
    List<String> findCategoriasDistintas();

    @Query("SELECT p FROM Producto p WHERE p.stock <= 5 AND p.activo = true")
    List<String> findStockBajo();
}
