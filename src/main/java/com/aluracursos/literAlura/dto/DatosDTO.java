package com.aluracursos.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosDTO(
        @JsonAlias("results")
        List<LibroDTO> resultados
) {
}
