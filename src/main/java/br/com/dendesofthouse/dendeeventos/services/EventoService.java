package br.com.dendesofthouse.dendeeventos.services;

import br.com.dendesofthouse.dendeeventos.dtos.evento.*;
import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.*;
import br.com.dendesofthouse.dendeeventos.exceptions.organizador.OrganizadorNaoEncontradoException;
import br.com.dendesofthouse.dendeeventos.mappers.EventoMapper;
import br.com.dendesofthouse.dendeeventos.models.Evento;
import br.com.dendesofthouse.dendeeventos.models.Organizador;
import br.com.dendesofthouse.dendeeventos.repositories.EventoRepository;
import br.com.dendesofthouse.dendeeventos.repositories.OrganizadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;
    private final EventoMapper eventoMapper;

    public StatusEventoDto cadastrarEvento(Long organizadorId, CadastrarEventoDto dto) {
        Organizador organizador = organizadorRepository.findById(organizadorId)
                .orElseThrow(() -> new OrganizadorNaoEncontradoException("Organizador não encontrado."));
        validarCadastro(dto);
        Evento evento = eventoMapper.toModel(dto);
        if (dto.eventoPrincipalId() != null) {
            Evento principal = eventoRepository.findById(dto.eventoPrincipalId())
                    .orElseThrow(() -> new EventoPrincipalNaoEncontradoException("Evento principal não encontrado."));
            evento.setEventoPrincipal(principal);
        }
        organizador.adicionarEvento(evento);
        evento = eventoRepository.save(evento);
        return eventoMapper.toStatusEventoDto("Evento criado com sucesso!", evento);
    }

    public StatusEventoDto atualizarEvento(Long organizadorId, Long eventoId, AtualizarEventoDto dto) {
        if (!organizadorRepository.existsById(organizadorId)) {
            throw new OrganizadorNaoEncontradoException("Organizador não encontrado.");
        }
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado."));
        if (!evento.getOrganizador().getId().equals(organizadorId)) {
            throw new EventoNaoEncontradoException("Evento não pertence ao organizador.");
        }
        if (!evento.isEventoAtivo()) {
            throw new EventoInativoException("Evento inativo não pode ser alterado.");
        }
        validarAtualizacao(dto, evento.getId());
        eventoMapper.updateModel(evento, dto);
        if (dto.eventoPrincipalId() != null) {
            if (dto.eventoPrincipalId().equals(eventoId)) {
                throw new DadosInvalidosException("Um evento não pode ser principal de si mesmo.");
            }
            Evento principal = eventoRepository.findById(dto.eventoPrincipalId())
                    .orElseThrow(() -> new EventoPrincipalNaoEncontradoException("Evento principal não encontrado."));
            if (!principal.getOrganizador().getId().equals(organizadorId)) {
                throw new DadosInvalidosException("O evento principal deve pertencer ao mesmo organizador.");
            }
            evento.setEventoPrincipal(principal);
        }
        evento = eventoRepository.save(evento);
        return eventoMapper.toStatusEventoDto("Evento atualizado com sucesso!", evento);
    }

    public StatusEventoDto ativarEvento(Long organizadorId, Long eventoId) {
        if (!organizadorRepository.existsById(organizadorId)) {
            throw new OrganizadorNaoEncontradoException("Organizador não encontrado.");
        }
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado."));
        if (!evento.getOrganizador().getId().equals(organizadorId)) {
            throw new EventoNaoEncontradoException("Evento não pertence ao organizador.");
        }
        validarDatasEvento(evento.getDataInicio(), evento.getDataFim());
        evento.ativar();
        evento = eventoRepository.save(evento);
        return eventoMapper.toStatusEventoDto("Evento ativado!", evento);
    }

    public StatusEventoDto desativarEvento(Long organizadorId, Long eventoId) {
        if (!organizadorRepository.existsById(organizadorId)) {
            throw new OrganizadorNaoEncontradoException("Organizador não encontrado.");
        }
        Evento evento = eventoRepository.findByIdWithIngressos(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado."));
        if (!evento.getOrganizador().getId().equals(organizadorId)) {
            throw new EventoNaoEncontradoException("Evento não pertence ao organizador.");
        }
        evento.desativar();
        evento = eventoRepository.save(evento);
        return eventoMapper.toStatusEventoDto("Evento desativado e ingressos cancelados.", evento);
    }

    @Transactional(readOnly = true)
    public List<EventosOrganizadorDto> listarEventosOrganizador(Long organizadorId) {
        if (!organizadorRepository.existsById(organizadorId)) {
            throw new OrganizadorNaoEncontradoException("Organizador não encontrado.");
        }
        return eventoRepository.findAllByOrganizadorId(organizadorId).stream()
                .map(eventoMapper::toEventosOrganizadorDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FeedEventoDto> listarEventosAtivos() {
        return eventoRepository.findAllAtivosDisponiveis().stream()
                .map(eventoMapper::toFeedEventoDto)
                .toList();
    }

    private void validarCadastro(CadastrarEventoDto dto) {
        if (dto == null) throw new DadosInvalidosException("Dados do evento inválidos.");
        if (dto.capacidadeMaxima() <= 0)
            throw new EventoCapacidadeInvalidaException("Capacidade máxima deve ser maior que zero.");
        if (dto.precoUnitarioIngresso().compareTo(BigDecimal.ZERO) < 0)
            throw new EventoPrecoIngressoInvalidoException("Preço do ingresso não pode ser negativo.");

        validarDatasEvento(dto.dataInicio(), dto.dataFim());
    }

    private void validarAtualizacao(AtualizarEventoDto dto, Long eventoId) {
        if (dto == null) throw new DadosInvalidosException("Dados de atualização inválidos.");
        if (dto.precoUnitarioIngresso() != null && dto.precoUnitarioIngresso().compareTo(BigDecimal.ZERO) < 0)
            throw new EventoPrecoIngressoInvalidoException("Preço do ingresso não pode ser negativo.");
        if (dto.taxaCancelamento() != null && dto.taxaCancelamento().compareTo(BigDecimal.ZERO) < 0)
            throw new EventoTaxaCancelamentoInvalidaException("Taxa de cancelamento não pode ser negativa.");
        if (dto.capacidadeMaxima() != null && dto.capacidadeMaxima() < 0)
            throw new EventoCapacidadeInvalidaException("Capacidade máxima não pode ser negativa.");
        if (dto.dataInicio() != null && dto.dataFim() != null) {
            validarDatasEvento(dto.dataInicio(), dto.dataFim());
        } else if (dto.dataInicio() != null) {
            Evento evento = eventoRepository.findById(eventoId).orElseThrow();
            validarDatasEvento(dto.dataInicio(), evento.getDataFim());
        } else if (dto.dataFim() != null) {
            Evento evento = eventoRepository.findById(eventoId).orElseThrow();
            validarDatasEvento(evento.getDataInicio(), dto.dataFim());
        }
    }

    private void validarDatasEvento(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio == null || dataFim == null)
            throw new DadosInvalidosException("Datas do evento são obrigatórias.");
        LocalDateTime agora = LocalDateTime.now();
        if (dataInicio.isBefore(agora))
            throw new EventoDataInicioInvalidaException("Data de início inválida.");
        if (dataFim.isBefore(agora))
            throw new EventoDataFimInvalidaException("Data de fim inválida.");
        if (dataFim.isBefore(dataInicio))
            throw new EventoDataFimAnteriorInicioException("Data final não pode ser anterior à inicial.");
        if (Duration.between(dataInicio, dataFim).toMinutes() < 30)
            throw new EventoDuracaoInvalidaException("Evento deve ter no mínimo 30 minutos.");
    }
}