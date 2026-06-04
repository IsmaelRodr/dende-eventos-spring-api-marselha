package br.com.dendesofthouse.dendeeventos.dtos.evento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusEventoDto {

    private String mensagem;

    private Long eventoId;

    private Boolean ativo;
}