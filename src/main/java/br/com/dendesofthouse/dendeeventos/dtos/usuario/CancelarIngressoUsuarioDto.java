package br.com.dendesofthouse.dendeeventos.dtos.usuario;

import java.math.BigDecimal;

public record CancelarIngressoUsuarioDto(
        String mensagem,
        Long ingressoId,
        BigDecimal valorEstornado
) {}