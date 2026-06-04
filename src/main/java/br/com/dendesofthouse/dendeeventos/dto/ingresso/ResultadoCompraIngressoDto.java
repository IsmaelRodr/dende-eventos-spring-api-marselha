package br.com.softhouse.dende.dto.ingresso;

import br.com.dendesofthouse.dendeeventos.dto.ingresso.IngressoGeradoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoCompraIngressoDto {

    private Double valorTotal;

    private List<IngressoGeradoDto> ingressosGerados;
}