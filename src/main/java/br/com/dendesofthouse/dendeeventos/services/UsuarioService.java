package br.com.dendesofthouse.dendeeventos.services;

import br.com.dendesofthouse.dendeeventos.dtos.LoginDto;
import br.com.dendesofthouse.dendeeventos.dtos.usuario.*;
import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.*;
import br.com.dendesofthouse.dendeeventos.mappers.UsuarioMapper;
import br.com.dendesofthouse.dendeeventos.models.Usuario;
import br.com.dendesofthouse.dendeeventos.repositories.OrganizadorRepository;
import br.com.dendesofthouse.dendeeventos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final OrganizadorRepository organizadorRepository;
    private final UsuarioMapper usuarioMapper;

    public StatusUsuarioDto cadastrar(CadastrarUsuarioDto dto) {
        if (emailExiste(dto.email())) {
            throw new EmailJaCadastradoException("Já existe um usuário com este email.");
        }
        Usuario usuario = usuarioMapper.toModel(dto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toStatusDto("Usuário registrado com sucesso!", usuario);
    }

    public StatusUsuarioDto atualizar(Long id, AtualizarUsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        usuarioMapper.updateModel(usuario, dto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toStatusDto("Perfil atualizado com sucesso!", usuario);
    }

    @Transactional(readOnly = true)
    public VisualizarUsuarioDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        return usuarioMapper.toVisualizarDto(usuario);
    }

    public StatusUsuarioDto ativar(Long id, LoginDto dto) {
        if (dto == null) throw new DadosInvalidosException("Credenciais são obrigatórias.");
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        if (!usuario.getEmail().equalsIgnoreCase(dto.email())) {
            throw new CredenciaisInvalidasException("Email não confere.");
        }
        if (!usuario.getSenha().equals(dto.senha())) {
            throw new CredenciaisInvalidasException("Senha inválida.");
        }
        if (usuario.isAtivo()) {
            throw new UsuarioJaAtivoException("Usuário já está ativo.");
        }
        usuario.setAtivo(true);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toStatusDto("Usuário reativado com sucesso!", usuario);
    }

    public StatusUsuarioDto desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        if (!usuario.isAtivo()) {
            throw new UsuarioJaInativoException("Usuário já está inativo.");
        }
        usuario.setAtivo(false);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toStatusDto("Usuário desativado com sucesso!", usuario);
    }

    private boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email) || organizadorRepository.existsByEmail(email);
    }
}