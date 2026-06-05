package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoPrincipalNaoEncontradoException extends DendeException {

    public EventoPrincipalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}