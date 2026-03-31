package com.educativo.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.educativo.marketplace.model.Producto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.educativo.marketplace.service.ProductoService;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@Valid @ModelAttribute Producto producto,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "productos/formulario";
        }
        productoService.guardar(producto);
        flash.addFlashAttribute("message", "Producto publicado correctamente");
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model){
        model.addAttribute("producto", productoService.buscarPorId(id));
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash){
        productoService.eliminar(id);
        flash.addFlashAttribute("message", "Producto eliminado correctamente");
        return "redirect:/";
    }
}
