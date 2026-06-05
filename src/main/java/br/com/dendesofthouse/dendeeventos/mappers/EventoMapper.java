package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.evento.*;
import br.com.dendesofthouse.dendeeventos.models.Evento;
import br.com.dendesofthouse.dendeeventos.models.Evento.Modalidade;
import br.com.dendesofthouse.dendeeventos.models.Evento.TipoEvento;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "eventoAtivo", constant = "false")
    @Mapping(target = "ingressos", ignore = true)
    @Mapping(target = "eventoPrincipal", source = "eventoPrincipalId", qualifiedByName = "toEventoPrincipal")
    @Mapping(target = "tipoEvento", source = "tipoEvento", qualifiedByName = "toTipoEvento")
    @Mapping(target = "modalidade", source = "modalidade", qualifiedByName = "toModalidade")
    Evento toModel(CadastrarEventoDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "eventoAtivo", ignore = true)
    @Mapping(target = "ingressos", ignore = true)
    @Mapping(target = "eventoPrincipal", source = "eventoPrincipalId", qualifiedByName = "toEventoPrincipal")
    @Mapping(target = "tipoEvento", source = "tipoEvento", qualifiedByName = "toTipoEvento")
    @Mapping(target = "modalidade", source = "modalidade", qualifiedByName = "toModalidade")
    void updateModel(@MappingTarget Evento evento, AtualizarEventoDto dto);

    EventosOrganizadorDto toEventosOrganizadorDto(Evento evento);

    FeedEventoDto toFeedEventoDto(Evento evento);

    @Mapping(source = "evento.id", target = "eventoId")
    @Mapping(source = "evento.eventoAtivo", target = "ativo")
    StatusEventoDto toStatusEventoDto(String mensagem, Evento evento);

    @Named("toTipoEvento")
    default TipoEvento toTipoEvento(String tipo) {
        if (tipo == null || tipo.isBlank()) return null;
        try {
            return TipoEvento.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de evento inválido: " + tipo);
        }
    }

    @Named("toModalidade")
    default Modalidade toModalidade(String modalidade) {
        if (modalidade == null || modalidade.isBlank()) return null;
        try {
            return Modalidade.valueOf(modalidade.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Modalidade inválida: " + modalidade);
        }
    }

    @Named("toEventoPrincipal")
    default Evento toEventoPrincipal(Long id) {
        if (id == null) return null;
        Evento evento = new Evento();
        evento.setId(id);
        return evento;
    }
}