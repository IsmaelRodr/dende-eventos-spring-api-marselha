package br.com.dendesofthouse.dendeeventos.dtos.organizador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDto {

    private String cnpj;

    private String razaoSocial;

    private String nomeFantasia;
}