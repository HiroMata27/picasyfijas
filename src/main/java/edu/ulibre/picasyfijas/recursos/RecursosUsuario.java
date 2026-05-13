package edu.ulibre.picasyfijas.recursos;

import edu.ulibre.picasyfijas.dto.UsuarioDto;
import edu.ulibre.picasyfijas.servicios.ServicioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class RecursosUsuario {

    private final ServicioUsuario servicioUsuario;

    private static final List<String> AVATARES = List.of(
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Felix",
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Aneka",
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Milo",
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Luna",
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Zoe",
            "https://api.dicebear.com/7.x/pixel-art/svg?seed=Max"
    );

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuarioDto", new UsuarioDto());
        model.addAttribute("avatares", AVATARES);
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute UsuarioDto usuarioDto,
                            Model model) {
        try {
            UsuarioDto guardado = servicioUsuario.registrar(usuarioDto);
            return "redirect:/juego?usuarioId=" + guardado.getId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuarioDto", usuarioDto);
            model.addAttribute("avatares", AVATARES);
            return "registro";
        }
    }

}