package br.com.dendesofthouse.dendeeventos.dtos.ingresso;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoCompraIngressoDto(
        BigDecimal valorTotal,
        List<IngressoGeradoDto> ingressosGerados
) {}