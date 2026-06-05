package br.com.dendesofthouse.dendeeventos.dtos.usuario;

import java.time.LocalDate;

public record VisualizarUsuarioDto(
        String nome,
        LocalDate dataNascimento,
        String idade,
        String sexo,
        String email,
        Boolean ativo
) {}