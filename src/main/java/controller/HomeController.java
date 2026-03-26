package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ProductoService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductoService productoService;

    @GetMapping("/")
    public String inicio(Model model, @RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            model.addAttribute("productos", productoService.buscarPorCategoria(categoria));
            model.addAttribute("categoria", categoria);
        } else {
            model.addAttribute("productos", productoService.listarActivos());
        }
        model.addAttribute("categoria", productoService.listarCategoria());
        return "index";
    }
}
