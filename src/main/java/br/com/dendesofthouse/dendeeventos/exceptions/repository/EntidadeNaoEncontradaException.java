package br.com.dendesofthouse.dendeeventos.exceptions.repository;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EntidadeNaoEncontradaException extends DendeException {

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}