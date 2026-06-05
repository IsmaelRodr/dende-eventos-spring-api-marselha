package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.ingresso.IngressoGeradoDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.ListaIngressosUsuarioDto;
import br.com.dendesofthouse.dendeeventos.models.Ingresso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IngressoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "id", target = "ingressoId")
    @Mapping(source = "evento.id", target = "eventoId")
    @Mapping(source = "evento.nome", target = "eventoNome")
    @Mapping(source = "evento.dataInicio", target = "dataInicio")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valorPago", target = "valorPago")
    @Mapping(source = "valorEstornado", target = "valorEstornado")
    @Mapping(source = "evento.eventoAtivo", target = "eventoAtivo")
    @Mapping(source = "dataCompra", target = "dataCompra")
    @Mapping(source = "email", target = "email")
    ListaIngressosUsuarioDto toListaUsuarioDto(Ingresso ingresso);

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "id", target = "ingressoId")
    @Mapping(source = "evento.id", target = "eventoId")
    @Mapping(source = "evento.nome", target = "eventoNome")
    @Mapping(source = "valorPago", target = "valorPago")
    IngressoGeradoDto toGeradoDto(Ingresso ingresso);
}