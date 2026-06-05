package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoNaoEncontradoException extends DendeException {

    public EventoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}