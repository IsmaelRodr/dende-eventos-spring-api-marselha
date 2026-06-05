package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.usuario.*;
import br.com.dendesofthouse.dendeeventos.models.Ingresso;
import br.com.dendesofthouse.dendeeventos.models.Usuario;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "ingressos", ignore = true)
    Usuario toModel(CadastrarUsuarioDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "ingressos", ignore = true)
    void updateModel(@MappingTarget Usuario usuario, AtualizarUsuarioDto dto);

    @Mapping(target = "idade", expression = "java(calcularIdade(usuario.getDataNascimento()))")
    VisualizarUsuarioDto toVisualizarDto(Usuario usuario);

    @Mapping(source = "mensagem", target = "mensagem")
    @Mapping(source = "ingresso.id", target = "ingressoId")
    @Mapping(source = "ingresso.valorEstornado", target = "valorEstornado")
    CancelarIngressoUsuarioDto toCancelarDto(String mensagem, Ingresso ingresso);

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.ativo", target = "ativo")
    StatusUsuarioDto toStatusDto(String mensagem, Usuario usuario);

    default String calcularIdade(LocalDate nascimento) {
        if (nascimento == null) return null;
        Period p = Period.between(nascimento, LocalDate.now());
        return p.getYears() + " anos, " + p.getMonths() + " meses, " + p.getDays() + " dias";
    }
}