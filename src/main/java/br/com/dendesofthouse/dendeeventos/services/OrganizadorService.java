package br.com.dendesofthouse.dendeeventos.services;

import br.com.dendesofthouse.dendeeventos.dtos.LoginDto;
import br.com.dendesofthouse.dendeeventos.dtos.organizador.*;
import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.organizador.*;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.CredenciaisInvalidasException;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.EmailJaCadastradoException;
import br.com.dendesofthouse.dendeeventos.mappers.OrganizadorMapper;
import br.com.dendesofthouse.dendeeventos.models.Empresa;
import br.com.dendesofthouse.dendeeventos.models.Organizador;
import br.com.dendesofthouse.dendeeventos.repositories.EventoRepository;
import br.com.dendesofthouse.dendeeventos.repositories.OrganizadorRepository;
import br.com.dendesofthouse.dendeeventos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrganizadorMapper organizadorMapper;

    public StatusOrganizadorDto cadastrar(CadastrarOrganizadorDto dto) {
        validarEmpresa(dto.empresa());
        if (organizadorRepository.existsByEmail(dto.email()) ||
                usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Já existe um organizador com este email.");
        }
        Organizador organizador = organizadorMapper.toModel(dto);
        if (organizador.getEmpresa() != null) {
            organizador.getEmpresa().setOrganizador(organizador);
        }
        organizador = organizadorRepository.save(organizador);
        return organizadorMapper.toStatusDto("Organizador cadastrado com sucesso!", organizador);
    }

    public StatusOrganizadorDto atualizar(Long id, AtualizarOrganizadorDto dto) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new OrganizadorNaoEncontradoException("Organizador não encontrado."));
        if (dto.empresa() != null) {
            validarEmpresa(dto.empresa());
            if (organizador.getEmpresa() == null) {
                Empresa empresa = organizadorMapper.toEmpresaEntity(dto.empresa());
                empresa.setOrganizador(organizador);
                organizador.setEmpresa(empresa);
            } else {
                Empresa empresa = organizador.getEmpresa();
                empresa.setCnpj(dto.empresa().cnpj());
                empresa.setRazaoSocial(dto.empresa().razaoSocial());
                empresa.setNomeFantasia(dto.empresa().nomeFantasia());
            }
        }
        organizadorMapper.updateModel(organizador, dto);
        organizador = organizadorRepository.save(organizador);
        return organizadorMapper.toStatusDto("Organizador atualizado com sucesso!", organizador);
    }

    @Transactional(readOnly = true)
    public VisualizarOrganizadorDto buscarPorId(Long id) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new OrganizadorNaoEncontradoException("Organizador não encontrado."));
        return organizadorMapper.toVisualizarDto(organizador);
    }

    public StatusOrganizadorDto ativar(Long id, LoginDto dto) {
        if (dto == null) throw new DadosInvalidosException("Credenciais são obrigatórias.");
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new OrganizadorNaoEncontradoException("Organizador não encontrado."));
        if (!organizador.getEmail().equalsIgnoreCase(dto.email())) {
            throw new CredenciaisInvalidasException("Email não confere.");
        }
        if (!organizador.getSenha().equals(dto.senha())) {
            throw new CredenciaisInvalidasException("Senha inválida.");
        }
        if (organizador.isAtivo()) {
            throw new OrganizadorJaAtivoException("Organizador já está ativo.");
        }
        organizador.setAtivo(true);
        organizador = organizadorRepository.save(organizador);
        return organizadorMapper.toStatusDto("Organizador reativado com sucesso!", organizador);
    }

    public StatusOrganizadorDto desativar(Long id) {
        Organizador organizador = organizadorRepository.findByIdWithEventos(id)
                .orElseThrow(() -> new OrganizadorNaoEncontradoException("Organizador não encontrado."));
        organizador.desativar();
        organizador = organizadorRepository.save(organizador);
        return organizadorMapper.toStatusDto("Organizador desativado com sucesso!", organizador);
    }

    private void validarEmpresa(EmpresaDto empresaDto) {
        if (empresaDto == null) return;
        if (empresaDto.cnpj() == null || empresaDto.cnpj().isBlank())
            throw new EmpresaInvalidaException("CNPJ obrigatório.");
        if (empresaDto.razaoSocial() == null || empresaDto.razaoSocial().isBlank())
            throw new EmpresaInvalidaException("Razão social obrigatória.");
        if (empresaDto.nomeFantasia() == null || empresaDto.nomeFantasia().isBlank())
            throw new EmpresaInvalidaException("Nome fantasia obrigatório.");
        String cnpjNumerico = empresaDto.cnpj().replaceAll("\\D", "");
        if (cnpjNumerico.length() != 14) throw new CnpjInvalidoException("CNPJ inválido.");
    }
}