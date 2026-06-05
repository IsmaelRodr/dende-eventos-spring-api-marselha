package br.com.dendesofthouse.dendeeventos.dtos.evento;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtualizarEventoDto(
        String nome,
        String descricao,
        String paginaWeb,
        @Future(message = "Data de início deve ser no futuro")
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String tipoEvento,
        Long eventoPrincipalId,
        String modalidade,
        @PositiveOrZero(message = "Preço não pode ser negativo")
        BigDecimal precoUnitarioIngresso,
        @PositiveOrZero(message = "Taxa de cancelamento não pode ser negativa")
        @DecimalMax(value = "100", message = "Taxa não pode ultrapassar 100%")
        BigDecimal taxaCancelamento,
        Boolean eventoEstorno,
        @Positive(message = "Capacidade máxima deve ser positiva")
        Integer capacidadeMaxima,
        String localEvento
) {}