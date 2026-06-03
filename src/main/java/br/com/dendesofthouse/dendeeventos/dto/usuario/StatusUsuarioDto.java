package br.com.dendesofthouse.dendeeventos.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUsuarioDto {

    private String mensagem;

    private Long usuarioId;

    private Boolean ativo;
}