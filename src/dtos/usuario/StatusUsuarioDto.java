package br.com.dendesofthouse.dendeeventos.dtos.usuario;

public record StatusUsuarioDto(
        String mensagem,
        Long usuarioId,
        Boolean ativo
) {}