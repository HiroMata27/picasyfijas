package edu.ulibre.picasyfijas.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartidaDto implements Serializable {

    private Long id;
    private int intentos;
    private long tiempoSegundos;
    private LocalDateTime fecha;
    private boolean completada;
    private Long usuarioId;
    private String usuarioNombre;
    private String usuarioAvatar;

}