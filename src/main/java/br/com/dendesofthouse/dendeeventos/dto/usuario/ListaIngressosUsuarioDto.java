package br.com.dendesofthouse.dendeeventos.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaIngressosUsuarioDto {

    private Long usuarioId;

    private Long ingressoId;

    private Long eventoId;

    private String eventoNome;

    private LocalDateTime dataInicio;

    private String status;

    private Double valorPago;

    private Double valorEstornado;

    private boolean eventoAtivo;

    private LocalDateTime dataCompra;

    private String email;
}