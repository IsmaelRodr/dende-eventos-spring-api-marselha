package br.com.dendesofthouse.dendeeventos.dtos.ingresso;

import java.math.BigDecimal;

public record IngressoGeradoDto(
        Long usuarioId,
        Long ingressoId,
        Long eventoId,
        String eventoNome,
        BigDecimal valorPago
) {}