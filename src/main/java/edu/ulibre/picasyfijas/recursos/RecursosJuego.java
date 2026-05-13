package edu.ulibre.picasyfijas.recursos;

import edu.ulibre.picasyfijas.dto.UsuarioDto;
import edu.ulibre.picasyfijas.servicios.ServicioPartida;
import edu.ulibre.picasyfijas.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class RecursosJuego {

    private final ServicioPartida servicioPartida;
    private final ServicioUsuario servicioUsuario;

    @GetMapping("/juego")
    public String mostrarJuego(@RequestParam Long usuarioId,
                               HttpSession session, Model model) {
        UsuarioDto usuario = servicioUsuario.obtenerPorId(usuarioId);
        session.setAttribute("usuarioId", usuarioId);
        session.setAttribute("secreta", servicioPartida.generarSecreta());
        session.setAttribute("intentos", 0);
        session.setAttribute("historial", new ArrayList<String>());
        session.setAttribute("inicio", System.currentTimeMillis());
        model.addAttribute("usuario", usuario);
        model.addAttribute("historial", new ArrayList<>());
        model.addAttribute("intentos", 0);
        model.addAttribute("mensaje", "");
        return "juego";
    }

    @PostMapping("/juego/intentar")
    public String intentar(@RequestParam String intento,
                           HttpSession session, Model model) {
        String secreta = (String) session.getAttribute("secreta");
        int intentos = (int) session.getAttribute("intentos");
        List<String> historial = (List<String>) session.getAttribute("historial");
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        Long inicio = (Long) session.getAttribute("inicio");
        UsuarioDto usuario = servicioUsuario.obtenerPorId(usuarioId);

        // Validar que sean 4 dígitos sin repetir
        if (!intento.matches("\\d{4}") || intento.chars().distinct().count() < 4) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("historial", historial);
            model.addAttribute("intentos", intentos);
            model.addAttribute("mensaje", "⚠️ Ingresa 4 dígitos diferentes");
            return "juego";
        }

        intentos++;
        int[] resultado = servicioPartida.calcularPicasFijas(secreta, intento);
        int picas = resultado[0];
        int fijas = resultado[1];

        historial.add("Intento " + intentos + ": " + intento +
                " → 🎯 Fijas: " + fijas + " | 💡 Picas: " + picas);

        session.setAttribute("intentos", intentos);
        session.setAttribute("historial", historial);

        model.addAttribute("usuario", usuario);
        model.addAttribute("historial", historial);
        model.addAttribute("intentos", intentos);

        if (fijas == 4) {
            long tiempoSegundos = (System.currentTimeMillis() - inicio) / 1000;
            servicioPartida.guardarPartida(usuarioId, intentos, tiempoSegundos);
            model.addAttribute("mensaje", "🎉 ¡Ganaste en " + intentos + " intentos y " + tiempoSegundos + " segundos!");
            model.addAttribute("gano", true);
            return "juego";
        }

        model.addAttribute("mensaje", "Sigue intentando...");
        return "juego";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("ranking", servicioPartida.obtenerRanking());
        return "ranking";
    }

}