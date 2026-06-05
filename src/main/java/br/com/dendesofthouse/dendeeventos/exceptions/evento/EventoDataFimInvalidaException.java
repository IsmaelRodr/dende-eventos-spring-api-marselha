package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoDataFimInvalidaException extends DendeException {

    public EventoDataFimInvalidaException(String mensagem) {
        super(mensagem);
    }
}