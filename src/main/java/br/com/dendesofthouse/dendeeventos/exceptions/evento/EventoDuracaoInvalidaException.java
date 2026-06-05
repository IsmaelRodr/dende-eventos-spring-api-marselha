package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoDuracaoInvalidaException extends DendeException {

    public EventoDuracaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}