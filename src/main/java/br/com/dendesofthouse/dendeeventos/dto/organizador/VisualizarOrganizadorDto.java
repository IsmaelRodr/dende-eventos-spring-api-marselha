package br.com.softhouse.dende.dto.organizador;

import br.com.dendesofthouse.dendeeventos.dto.organizador.EmpresaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizarOrganizadorDto {

    private String nome;

    private LocalDate dataNascimento;

    private String idade;

    private String sexo;

    private String email;

    private Boolean ativo;

    private EmpresaDto empresa;
}