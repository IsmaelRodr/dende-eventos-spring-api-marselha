package br.com.dendesofthouse.dendeeventos.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarUsuarioDto {

    private String nome;

    private LocalDate dataNascimento;

    private String sexo;

    private String senha;
}