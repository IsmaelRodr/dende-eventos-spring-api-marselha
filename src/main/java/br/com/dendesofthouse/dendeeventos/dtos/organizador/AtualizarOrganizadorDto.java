package br.com.dendesofthouse.dendeeventos.dtos.organizador;

import br.com.dendesofthouse.dendeeventos.models.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarOrganizadorDto {

    private String nome;

    private LocalDate dataNascimento;

    private String sexo;

    private String senha;

    private Empresa empresa;
}