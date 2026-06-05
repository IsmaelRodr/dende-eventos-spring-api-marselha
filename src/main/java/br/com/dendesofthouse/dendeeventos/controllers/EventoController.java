package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.dtos.evento.FeedEventoDto;
import br.com.dendesofthouse.dendeeventos.services.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<FeedEventoDto>> feedEvento() {
        List<FeedEventoDto> resposta = eventoService.listarEventosAtivos();
        return ResponseEntity.ok(resposta);
    }
}