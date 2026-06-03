package br.com.dendesofthouse.dendeeventos.dto.ingresso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoGeradoDto {

    private Long usuarioId;

    private Long ingressoId;

    private Long eventoId;

    private String nomeEvento;

    private Double valorPago;
}