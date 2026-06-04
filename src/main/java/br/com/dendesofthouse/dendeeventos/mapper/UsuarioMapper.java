package br.com.dendesofthouse.dendeeventos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true) //Gerado pelo banco
    Usuario toModel(CadastrarUsuarioDto dto); //Converte os dados de entrada em objeto.

    void updateModel(@MappingTarget Usuario usuario, AtualizarUsuarioDto dto); //Atualiza o objeto.

    @Mapping(target= "idade", expression = "java(calcularIdade(usuario.getDataNascimento()))") //Usar o método auxiliar para imprimir a idade com base na US.
    VisualizarUsuarioDto toVisualizarDto(Usuario usuario);

    @Mapping(source = "mensagem", target = "mensagem")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.ativo", target = "ativo")
    StatusUsuarioDto toStatusDto(String mensagem, Usuario usuario); //DTO para confirmação de operações.

    @Mapping(source = "mensagem", target = "mensagem")
    @Mapping(source = "ingresso.id", target = "ingressoId")
    @Mapping(source = "ingresso.valorEstornado", target = "valorEstornado")
    CancelarIngressoUsuarioDto toCancelarDTO(String mensagem, Ingresso ingresso); //DTO para confirmação de cancelamento.

    private String calcularIdade(LocalDate nascimento) {
        Period p = Period.between(nascimento, LocalDate.now());
        return p.getYears() + " anos, " +
                p.getMonths() + " meses, " +
                p.getDays() + " dias";
    }
}
