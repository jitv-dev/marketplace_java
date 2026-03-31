package com.educativo.marketplace.service;


import lombok.RequiredArgsConstructor;
import com.educativo.marketplace.model.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.educativo.marketplace.repository.ProductoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> listarTodos(){
        return productoRepository.findAll();
    }
     public List<Producto> listarActivos(){
        return productoRepository.findByActivoTrue();
    }

     public List<Producto> buscarPorCategoria(String categoria){
        return productoRepository.findByCategoriaAndActivoTrue(categoria);
    }
    public List<String> listarCategorias(){
        return productoRepository.findByCategoriasDistintas();
    }

    public Producto buscarPorId(Long id){
        return productoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Producto no encontrado: " + id));
    }

    @Transactional
    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }

    @Transactional
    public Producto actualizar(Producto producto){
        return productoRepository.save(producto);
    }

    @Transactional
    public void descontarStock(Long productoId, int cantidad){
        Producto p= buscarPorId(productoId);
        if(p.getStock() < cantidad){
            throw  new RuntimeException("Stock no encontrado");
        }
        p.setStock(p.getStock() - cantidad);
        productoRepository.save(p);
    }

}
