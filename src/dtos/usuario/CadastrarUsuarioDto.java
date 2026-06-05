package br.com.dendesofthouse.dendeeventos.dtos.usuario;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CadastrarUsuarioDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        @NotBlank(message = "Sexo é obrigatório")
        String sexo,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha
) {}