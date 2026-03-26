package controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.Producto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.ProductoService;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public String listar(Model model){
        model.addAllAttributes("productos", productoService.listarTodos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                          BindingResult result,
                          RedirectAttributes flash){
        if (result.hasErrors()){
            return "productos/formulario";
        }
        productoService.guardar(producto);
        flash.addFlashAttribute("mensaje", "Producto publicado correctamente");
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model){
        model.addAllAttributes("producto", productoService.buscarPorId(id));
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash){
        productoService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Producto eliminado correctamente");
        return "redirect:/";
    }
}
