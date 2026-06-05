package br.com.dendesofthouse.dendeeventos.dtos.usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListaIngressosUsuarioDto(
        Long usuarioId,
        Long ingressoId,
        Long eventoId,
        String eventoNome,
        LocalDateTime dataInicio,
        String status,
        BigDecimal valorPago,
        BigDecimal valorEstornado,
        boolean eventoAtivo,
        LocalDateTime dataCompra,
        String email
) {}