package br.com.dendesofthouse.dendeeventos.dtos.evento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedEventoDto {

    private String nome;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String local;

    private Double precoIngresso;

    private Integer capacidadeMaxima;

    private String tipoEvento;

    private String modalidade;
}