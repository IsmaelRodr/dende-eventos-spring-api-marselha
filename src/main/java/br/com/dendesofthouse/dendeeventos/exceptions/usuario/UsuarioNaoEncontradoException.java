package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class UsuarioNaoEncontradoException extends DendeException {

    public UsuarioNaoEncontradoException(String s) {
        super("Usuário não encontrado.");
    }
}