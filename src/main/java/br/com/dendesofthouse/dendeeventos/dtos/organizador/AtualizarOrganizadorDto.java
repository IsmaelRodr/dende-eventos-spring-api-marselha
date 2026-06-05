package br.com.dendesofthouse.dendeeventos.dtos.organizador;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record AtualizarOrganizadorDto(
        String nome,
        @Past(message = "Data de nascimento inválida")
        LocalDate dataNascimento,
        String sexo,
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,
        @Valid
        EmpresaDto empresa
) {}