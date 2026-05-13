package edu.ulibre.picasyfijas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "partidas")
public class Partida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PAR_INTENTOS", nullable = false)
    private int intentos;

    @Column(name = "PAR_TIEMPO_SEGUNDOS", nullable = false)
    private long tiempoSegundos;

    @Column(name = "PAR_FECHA")
    private LocalDateTime fecha;

    @Column(name = "PAR_COMPLETADA")
    private boolean completada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAR_USUARIO_ID", nullable = false)
    @ToString.Exclude
    private Usuario usuario;

}