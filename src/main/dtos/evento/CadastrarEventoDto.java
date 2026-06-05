package br.com.dendesofthouse.dendeeventos.dtos.evento;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastrarEventoDto(
        @NotBlank String nome,
        String descricao,
        String paginaWeb,
        @NotNull @Future LocalDateTime dataInicio,
        @NotNull LocalDateTime dataFim,
        @NotBlank String tipoEvento,
        Long eventoPrincipalId,
        @NotBlank String modalidade,
        @PositiveOrZero BigDecimal precoUnitarioIngresso,
        @PositiveOrZero @DecimalMax("100") BigDecimal taxaCancelamento,
        boolean eventoEstorno,
        @Positive int capacidadeMaxima,
        @NotBlank String localEvento
) {}