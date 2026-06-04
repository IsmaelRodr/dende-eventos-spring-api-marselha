package br.com.dendesofthouse.dendeeventos.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelarIngressoUsuarioDto {

    private String mensagem;

    private Long ingressoId;

    private Double valorEstornado;
}