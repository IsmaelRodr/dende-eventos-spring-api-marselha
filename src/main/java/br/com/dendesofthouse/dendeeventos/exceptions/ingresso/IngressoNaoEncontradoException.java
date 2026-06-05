package br.com.dendesofthouse.dendeeventos.exceptions.ingresso;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class IngressoNaoEncontradoException extends DendeException {

    public IngressoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}