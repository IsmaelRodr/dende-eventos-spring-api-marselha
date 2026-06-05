package br.com.dendesofthouse.dendeeventos.dtos.organizador;

public record StatusOrganizadorDto(
        String mensagem,
        Long organizadorId,
        Boolean ativo
) {}