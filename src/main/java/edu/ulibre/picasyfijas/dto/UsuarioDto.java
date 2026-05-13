package edu.ulibre.picasyfijas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDto implements Serializable {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String avatar;

}