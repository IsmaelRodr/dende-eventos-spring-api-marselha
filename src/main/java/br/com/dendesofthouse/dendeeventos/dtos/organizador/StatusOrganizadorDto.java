package br.com.dendesofthouse.dendeeventos.dtos.organizador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusOrganizadorDto {

    private String mensagem;

    private Long organizadorId;

    private Boolean ativo;
}