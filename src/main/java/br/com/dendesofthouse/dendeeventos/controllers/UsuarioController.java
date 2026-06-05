package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.dtos.LoginDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.*;
import br.com.dendesofthouse.dendeeventos.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<StatusUsuarioDto> cadastrar(@Valid @RequestBody CadastrarUsuarioDto dto) {
        StatusUsuarioDto resposta = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusUsuarioDto> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody AtualizarUsuarioDto dto) {
        StatusUsuarioDto resposta = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<VisualizarUsuarioDto> visualizarPerfil(@PathVariable Long usuarioId) {
        VisualizarUsuarioDto resposta = usuarioService.buscarPorId(usuarioId);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{usuarioId}/desativar")
    public ResponseEntity<StatusUsuarioDto> desativarUsuario(@PathVariable Long usuarioId) {
        StatusUsuarioDto resposta = usuarioService.desativar(usuarioId);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{usuarioId}/ativar")
    public ResponseEntity<StatusUsuarioDto> ativarUsuario(@PathVariable Long usuarioId,
                                                          @Valid @RequestBody LoginDto dto) {
        StatusUsuarioDto resposta = usuarioService.ativar(usuarioId, dto);
        return ResponseEntity.ok(resposta);
    }
}