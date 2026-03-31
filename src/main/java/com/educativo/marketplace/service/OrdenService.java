package com.educativo.marketplace.service;



import lombok.RequiredArgsConstructor;
import com.educativo.marketplace.model.Orden;
import com.educativo.marketplace.model.Producto;
import com.educativo.marketplace.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.educativo.marketplace.repository.OrdenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdenService {
    private final OrdenRepository ordenRepository;
    private final ProductoService productoService;

    @Transactional
    public Orden crearOrden(Usuario usuario, Long productoId, int cantidad){
        Producto producto= productoService.buscarPorId(productoId);
        productoService.descontarStock(productoId, cantidad);
        return ordenRepository.save(Orden.builder()
                .usuario(usuario)
                .producto(producto)
                .cantidad(cantidad)
                .total(producto.getPrecio() * cantidad)
                .fecha(LocalDateTime.now())
                .estado("PENDIENTE")
                .build());
    }

    public List<Orden> listarPorUsuario(Long usuarioId){
        return ordenRepository.findByUserOrderByFechaDesc(usuarioId);
    }

    public List<Orden> listarTodas(){
        return ordenRepository.findAll();
    }

    @Transactional
    public void cambiarEstado(Long idOrden, String nuevoEstado){
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(()-> new RuntimeException("Orden no encontrada"));
        orden.setEstado(nuevoEstado);
        ordenRepository.save(orden);
    }
}
