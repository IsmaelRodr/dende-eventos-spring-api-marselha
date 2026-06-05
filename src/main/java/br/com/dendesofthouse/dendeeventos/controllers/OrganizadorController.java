package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.dtos.LoginDto;
import br.com.dendesofthouse.dendeeventos.dtos.evento.*;
import br.com.dendesofthouse.dendeeventos.dtos.organizador.*;
import br.com.dendesofthouse.dendeeventos.services.EventoService;
import br.com.dendesofthouse.dendeeventos.services.OrganizadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizadores")
@RequiredArgsConstructor
public class OrganizadorController {

    private final OrganizadorService organizadorService;
    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<StatusOrganizadorDto> cadastrarOrganizador(@Valid @RequestBody CadastrarOrganizadorDto dto) {
        StatusOrganizadorDto resposta = organizadorService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{organizadorId}")
    public ResponseEntity<StatusOrganizadorDto> atualizarOrganizador(@PathVariable Long organizadorId,
                                                                     @Valid @RequestBody AtualizarOrganizadorDto dto) {
        StatusOrganizadorDto resposta = organizadorService.atualizar(organizadorId, dto);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{organizadorId}")
    public ResponseEntity<VisualizarOrganizadorDto> visualizarPerfilOrganizador(@PathVariable Long organizadorId) {
        VisualizarOrganizadorDto resposta = organizadorService.buscarPorId(organizadorId);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{organizadorId}/ativar")
    public ResponseEntity<StatusOrganizadorDto> ativarOrganizador(@PathVariable Long organizadorId,
                                                                  @Valid @RequestBody LoginDto dto) {
        StatusOrganizadorDto resposta = organizadorService.ativar(organizadorId, dto);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{organizadorId}/desativar")
    public ResponseEntity<StatusOrganizadorDto> desativarOrganizador(@PathVariable Long organizadorId) {
        StatusOrganizadorDto resposta = organizadorService.desativar(organizadorId);
        return ResponseEntity.ok(resposta);
    }

    // Eventos do organizador

    @PostMapping("/{organizadorId}/eventos")
    public ResponseEntity<StatusEventoDto> cadastrarEvento(@PathVariable Long organizadorId,
                                                           @Valid @RequestBody CadastrarEventoDto dto) {
        StatusEventoDto resposta = eventoService.cadastrarEvento(organizadorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{organizadorId}/eventos/{eventoId}")
    public ResponseEntity<StatusEventoDto> alterarEvento(@PathVariable Long organizadorId,
                                                         @PathVariable Long eventoId,
                                                         @Valid @RequestBody AtualizarEventoDto dto) {
        StatusEventoDto resposta = eventoService.atualizarEvento(organizadorId, eventoId, dto);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{organizadorId}/eventos/{eventoId}/ativar")
    public ResponseEntity<StatusEventoDto> ativarEvento(@PathVariable Long organizadorId,
                                                        @PathVariable Long eventoId) {
        StatusEventoDto resposta = eventoService.ativarEvento(organizadorId, eventoId);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{organizadorId}/eventos/{eventoId}/desativar")
    public ResponseEntity<StatusEventoDto> desativarEvento(@PathVariable Long organizadorId,
                                                           @PathVariable Long eventoId) {
        StatusEventoDto resposta = eventoService.desativarEvento(organizadorId, eventoId);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{organizadorId}/eventos")
    public ResponseEntity<List<EventosOrganizadorDto>> listarEventosDoOrganizador(@PathVariable Long organizadorId) {
        List<EventosOrganizadorDto> resposta = eventoService.listarEventosOrganizador(organizadorId);
        return ResponseEntity.ok(resposta);
    }
}