package edu.ulibre.picasyfijas.repositorios;

import edu.ulibre.picasyfijas.entidades.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioPartida extends JpaRepository<Partida, Long> {

    List<Partida> findByUsuarioId(Long usuarioId);

    @Query("SELECT p FROM Partida p WHERE p.completada = true ORDER BY p.intentos ASC, p.tiempoSegundos ASC")
    List<Partida> findRanking();

}