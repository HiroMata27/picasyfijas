package edu.ulibre.picasyfijas.servicios;

import edu.ulibre.picasyfijas.dto.PartidaDto;
import edu.ulibre.picasyfijas.entidades.Partida;
import edu.ulibre.picasyfijas.entidades.Usuario;
import edu.ulibre.picasyfijas.repositorios.RepositorioPartida;
import edu.ulibre.picasyfijas.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicioPartida implements Serializable {

    private final RepositorioPartida repoPartida;
    private final RepositorioUsuario repoUsuario;

    public String generarSecreta() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i <= 9; i++) numeros.add(i);
        Collections.shuffle(numeros);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) sb.append(numeros.get(i));
        return sb.toString();
    }

    public int[] calcularPicasFijas(String secreta, String intento) {
        int fijas = 0, picas = 0;
        for (int i = 0; i < 4; i++) {
            if (intento.charAt(i) == secreta.charAt(i)) {
                fijas++;
            } else if (secreta.contains(String.valueOf(intento.charAt(i)))) {
                picas++;
            }
        }
        return new int[]{picas, fijas};
    }

    public PartidaDto guardarPartida(Long usuarioId, int intentos, long tiempoSegundos) {
        Usuario usuario = repoUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Partida partida = Partida.builder()
                .intentos(intentos)
                .tiempoSegundos(tiempoSegundos)
                .fecha(LocalDateTime.now())
                .completada(true)
                .usuario(usuario)
                .build();
        repoPartida.save(partida);
        return toDto(partida);
    }

    public List<PartidaDto> obtenerRanking() {
        return repoPartida.findRanking().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PartidaDto toDto(Partida p) {
        PartidaDto dto = new PartidaDto();
        dto.setId(p.getId());
        dto.setIntentos(p.getIntentos());
        dto.setTiempoSegundos(p.getTiempoSegundos());
        dto.setFecha(p.getFecha());
        dto.setCompletada(p.isCompletada());
        dto.setUsuarioId(p.getUsuario().getId());
        dto.setUsuarioNombre(p.getUsuario().getNombre());
        dto.setUsuarioAvatar(p.getUsuario().getAvatar());
        return dto;
    }

}