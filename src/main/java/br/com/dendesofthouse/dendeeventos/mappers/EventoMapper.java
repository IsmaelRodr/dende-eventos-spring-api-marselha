package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.evento.*;
import br.com.dendesofthouse.dendeeventos.models.Evento;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EventoMapper {

    @Mapping(target = "id", ignore = true)                       // gerado pelo banco
    @Mapping(target = "eventoAtivo", constant = "false")         // valor padrão: false
    @Mapping(target = "ingressosDisponiveis", ignore = true)     // campo calculado, não persistido
    @Mapping(target = "organizador", ignore = true)              // será setado no service
    @Mapping(target = "eventoPrincipal", source = "eventoPrincipalId", qualifiedByName = "toEventoPrincipal") //Usa método específico.
    @Mapping(target = "tipoEvento", source = "tipoEvento", qualifiedByName = "toTipoEvento") //usa método específico.
    @Mapping(target = "modalidade", source = "modalidade", qualifiedByName = "toModalidade") //usa método específico.
    Evento toModel(CadastrarEventoDto dto); //Cria um objeto com dados do DTO

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "ingressosDisponiveis", ignore = true) //Esse campo não existe em persistência ele é calculado na memória.
    @Mapping(target = "eventoPrincipal", source = "eventoPrincipalId", qualifiedByName = "toEventoPrincipal") //usa método específico.
    @Mapping(target = "tipoEvento", source = "tipoEvento", qualifiedByName = "toTipoEvento") //usa método específico.
    @Mapping(target = "modalidade", source = "modalidade", qualifiedByName = "toModalidade") //usa método específico.
    void updateModel(@MappingTarget Evento evento, AtualizarEventoDto dto);

    EventosOrganizadorDto toEventosOrganizadorDto(Evento evento); //DTO de saída de dados convertendo objeto em DTO.

    FeedEventoDto toFeedEventoDto(Evento evento); //DTO de saída de dados convertendo objeto em DTO.

    @Mapping(source = "evento.id", target = "eventoId") //Mapear o ID do DTO
    @Mapping(source = "evento.eventoAtivo", target = "ativo") //Mapear o atributo ativo (desnessario?)
    StatusEventoDto toStatusEventoDto(String mensagem, Evento evento);


    @Named("toTipoEvento")
    default Evento.TipoEvento toTipoEvento(String tipoEvento) {
        if (tipoEvento == null || tipoEvento.isBlank()) return null;
        return Evento.TipoEvento.valueOf(tipoEvento.trim().toUpperCase());
    }

    @Named("toModalidade")
    default Evento.Modalidade toModalidade(String modalidade) {
        if (modalidade == null || modalidade.isBlank()) return null;
        return Evento.Modalidade.valueOf(modalidade.trim().toUpperCase());
    }

    @Named("toEventoPrincipal")
    default Evento toEventoPrincipal(Long eventoPrincipalId) {
        if (eventoPrincipalId == null) return null;
        Evento principal = new Evento();
        principal.setId(eventoPrincipalId);
        return principal;  // apenas com ID, o restante será carregado pelo service se necessário
    }
}
