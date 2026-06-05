package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.dtos.ingresso.ResultadoCompraIngressoDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.CancelarIngressoUsuarioDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.ListaIngressosUsuarioDto;
import br.com.dendesofthouse.dendeeventos.services.IngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @PostMapping("/organizadores/{organizadorId}/eventos/{eventoId}/ingressos")
    public ResponseEntity<ResultadoCompraIngressoDto> comprarIngresso(
            @PathVariable Long organizadorId,
            @PathVariable Long eventoId,
            @RequestBody Map<String, Long> request) {

        Long usuarioId = request.get("usuarioId");
        ResultadoCompraIngressoDto resposta = ingressoService.comprarIngresso(usuarioId, eventoId, organizadorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/usuarios/{usuarioId}/ingressos/{ingressoId}")
    public ResponseEntity<CancelarIngressoUsuarioDto> cancelarIngresso(
            @PathVariable Long usuarioId,
            @PathVariable Long ingressoId) {

        CancelarIngressoUsuarioDto resposta = ingressoService.cancelarIngresso(usuarioId, ingressoId);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/usuarios/{usuarioId}/ingressos")
    public ResponseEntity<List<ListaIngressosUsuarioDto>> listarIngressos(@PathVariable Long usuarioId) {
        List<ListaIngressosUsuarioDto> lista = ingressoService.listarIngressosUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}