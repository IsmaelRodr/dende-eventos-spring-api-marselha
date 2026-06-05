package br.com.dendesofthouse.dendeeventos.dtos.organizador;

import jakarta.validation.constraints.Pattern;

public record EmpresaDto(
        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$|^\\d{14}$", message = "CNPJ inválido")
        String cnpj,
        String razaoSocial,
        String nomeFantasia
) {}