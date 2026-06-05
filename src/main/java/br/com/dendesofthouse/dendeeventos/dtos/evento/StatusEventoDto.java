package br.com.dendesofthouse.dendeeventos.dtos.evento;

public record StatusEventoDto(
        String mensagem,
        Long eventoId,
        Boolean ativo
) {}