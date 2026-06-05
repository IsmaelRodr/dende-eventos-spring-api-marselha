package br.com.dendesofthouse.dendeeventos.services;

import br.com.dendesofthouse.dendeeventos.dtos.ingresso.IngressoGeradoDto;
import br.com.dendesofthouse.dendeeventos.dtos.ingresso.ResultadoCompraIngressoDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.CancelarIngressoUsuarioDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.ListaIngressosUsuarioDto;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.*;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.CancelamentoNaoPermitidoException;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.IngressoNaoEncontradoException;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.UsuarioInativoException;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.UsuarioNaoEncontradoException;
import br.com.dendesofthouse.dendeeventos.mappers.IngressoMapper;
import br.com.dendesofthouse.dendeeventos.mappers.UsuarioMapper;
import br.com.dendesofthouse.dendeeventos.models.Evento;
import br.com.dendesofthouse.dendeeventos.models.Ingresso;
import br.com.dendesofthouse.dendeeventos.models.Usuario;
import br.com.dendesofthouse.dendeeventos.repositories.EventoRepository;
import br.com.dendesofthouse.dendeeventos.repositories.IngressoRepository;
import br.com.dendesofthouse.dendeeventos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IngressoService {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final IngressoRepository ingressoRepository;
    private final IngressoMapper ingressoMapper;
    private final UsuarioMapper usuarioMapper;

    public ResultadoCompraIngressoDto comprarIngresso(Long usuarioId, Long eventoId, Long organizadorId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        if (!usuario.isAtivo()) throw new UsuarioInativoException("Usuário inativo.");

        // Carregar evento com ingressos para verificar disponibilidade
        Evento evento = eventoRepository.findByIdWithIngressos(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado."));
        if (!evento.isEventoAtivo()) throw new EventoInativoException("Evento inativo.");
        if (evento.getDataInicio().isBefore(LocalDateTime.now()))
            throw new EventoExpiradoException("Evento já iniciado.");
        if (!evento.getOrganizador().getId().equals(organizadorId)) {
            throw new EventoNaoEncontradoException("Evento não pertence ao organizador.");
        }
        if (evento.getIngressosDisponiveis() <= 0) {
            throw new EventoSemIngressosDisponiveisException("Ingressos esgotados.");
        }

        List<Ingresso> ingressosGerados = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        if (evento.getEventoPrincipal() != null) {
            Evento principal = eventoRepository.findById(evento.getEventoPrincipal().getId())
                    .orElseThrow(() -> new EventoNaoEncontradoException("Evento principal não encontrado."));
            if (!principal.isEventoAtivo())
                throw new EventoInativoException("Evento principal está inativo.");
            if (principal.getDataInicio().isBefore(LocalDateTime.now()))
                throw new EventoExpiradoException("Evento principal já iniciado.");

            Ingresso ingressoPrincipal = new Ingresso(usuario, principal, principal.getPrecoUnitarioIngresso(), usuario.getEmail());
            principal.adicionarIngresso(ingressoPrincipal);
            usuario.adicionarIngresso(ingressoPrincipal);
            ingressoRepository.save(ingressoPrincipal);
            eventoRepository.save(principal);
            valorTotal = valorTotal.add(principal.getPrecoUnitarioIngresso());
            ingressosGerados.add(ingressoPrincipal);
        }

        Ingresso ingressoEvento = new Ingresso(usuario, evento, evento.getPrecoUnitarioIngresso(), usuario.getEmail());
        evento.adicionarIngresso(ingressoEvento);
        usuario.adicionarIngresso(ingressoEvento);
        ingressoRepository.save(ingressoEvento);
        eventoRepository.save(evento);
        valorTotal = valorTotal.add(evento.getPrecoUnitarioIngresso());
        ingressosGerados.add(ingressoEvento);

        List<IngressoGeradoDto> dtos = ingressosGerados.stream()
                .map(ingressoMapper::toGeradoDto)
                .collect(Collectors.toList());

        return new ResultadoCompraIngressoDto(valorTotal, dtos);
    }

    public CancelarIngressoUsuarioDto cancelarIngresso(Long usuarioId, Long ingressoId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        Ingresso ingresso = ingressoRepository.findById(ingressoId)
                .orElseThrow(() -> new IngressoNaoEncontradoException("Ingresso não encontrado."));
        if (!ingresso.getUsuario().getId().equals(usuarioId)) {
            throw new CancelamentoNaoPermitidoException("Ingresso não pertence ao usuário.");
        }
        Evento evento = ingresso.getEvento();
        evento.cancelarIngressoIndividual(ingresso);
        ingressoRepository.save(ingresso);
        eventoRepository.save(evento);
        return usuarioMapper.toCancelarDto("Ingresso cancelado com sucesso.", ingresso);
    }

    @Transactional(readOnly = true)
    public List<ListaIngressosUsuarioDto> listarIngressosUsuario(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        List<Ingresso> ingressos = ingressoRepository.findAllByUsuarioIdWithEvento(usuarioId);
        Comparator<Ingresso> groupComparator = Comparator.comparing(Ingresso::isAtivoParaListagem).reversed();
        Comparator<Ingresso> byStartDate = Comparator.comparing(i -> i.getEvento().getDataInicio());
        Comparator<Ingresso> byNomeEvento = Comparator.comparing(i -> i.getEvento().getNome());
        return ingressos.stream()
                .sorted(groupComparator.thenComparing(byStartDate).thenComparing(byNomeEvento))
                .map(ingressoMapper::toListaUsuarioDto)
                .collect(Collectors.toList());
    }
}