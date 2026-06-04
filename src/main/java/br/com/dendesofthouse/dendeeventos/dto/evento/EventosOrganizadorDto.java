package br.com.dendesofthouse.dendeeventos.dto.evento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventosOrganizadorDto {

    private String nome;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String local;

    private Double precoIngresso;

    private Integer capacidadeMaxima;
}