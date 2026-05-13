package edu.ulibre.picasyfijas.repositorios;

import edu.ulibre.picasyfijas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}