package edu.ulibre.picasyfijas.servicios;

import edu.ulibre.picasyfijas.dto.UsuarioDto;
import edu.ulibre.picasyfijas.entidades.Usuario;
import edu.ulibre.picasyfijas.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServicioUsuario implements Serializable {

    private final RepositorioUsuario repoUsuario;

    public UsuarioDto registrar(UsuarioDto dto) {
        if (repoUsuario.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .avatar(dto.getAvatar())
                .build();
        repoUsuario.save(usuario);
        return toDto(usuario);
    }

    public List<UsuarioDto> obtenerTodos() {
        return repoUsuario.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UsuarioDto obtenerPorNombre(String nombre) {
        Usuario usuario = repoUsuario.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDto(usuario);
    }

    public UsuarioDto obtenerPorId(Long id) {
        Usuario usuario = repoUsuario.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDto(usuario);
    }

    private UsuarioDto toDto(Usuario u) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setAvatar(u.getAvatar());
        return dto;
    }

}