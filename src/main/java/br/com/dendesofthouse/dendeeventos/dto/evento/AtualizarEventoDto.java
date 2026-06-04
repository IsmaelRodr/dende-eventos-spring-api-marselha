package br.com.dendesofthouse.dendeeventos.dto.evento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarEventoDto {

    private String nome;

    private String descricao;

    private String paginaWeb;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String tipoEvento;

    private Long eventoPrincipalId;

    private String modalidade;

    private Double precoUnitarioIngresso;

    private Double taxaCancelamento;

    private Boolean eventoEstorno;

    private Integer capacidadeMaxima;

    private String localEvento;
}