package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.models.Usuario;
import br.com.dendesofthouse.dendeeventos.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(service.salvar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> visualizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Agora usamos o id que vem da URL
        return ResponseEntity.ok(service.atualizar(id, usuario));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> alternarStatus(@PathVariable Long id) {
        service.alterarStatusUsuario(id);
        return ResponseEntity.ok("Status alterado!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // Status 204: Sucesso, mas sem corpo de resposta
    }
}