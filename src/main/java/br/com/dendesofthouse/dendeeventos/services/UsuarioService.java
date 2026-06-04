package br.com.dendesofthouse.dendeeventos.services;

import br.com.dendesofthouse.dendeeventos.models.Usuario;
import br.com.dendesofthouse.dendeeventos.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // Método para atualizar (garantindo que o ID seja o mesmo)
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        // 1. Busca o que já está salvo no banco
        Usuario existente = buscarPorId(id);

        // 2. Atualiza só o que veio no JSON (se não veio null)
        if (usuarioAtualizado.getNome() != null) existente.setNome(usuarioAtualizado.getNome());
        if (usuarioAtualizado.getEmail() != null) existente.setEmail(usuarioAtualizado.getEmail());
        if (usuarioAtualizado.getSexo() != null) existente.setSexo(usuarioAtualizado.getSexo());
        // A data_nascimento não será nula porque estamos mantendo a que já estava no banco!

        // 3. Salva a entidade completa
        return repository.save(existente);
    }

    // Método para o PATCH
    public void alterarStatusUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(!usuario.getAtivo());
        repository.save(usuario);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        repository.deleteById(id);
    }
}