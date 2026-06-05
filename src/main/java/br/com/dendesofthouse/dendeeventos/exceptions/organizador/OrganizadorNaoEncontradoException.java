package br.com.dendesofthouse.dendeeventos.exceptions.organizador;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class OrganizadorNaoEncontradoException extends DendeException {

    public OrganizadorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}