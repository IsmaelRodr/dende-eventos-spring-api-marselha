package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoCapacidadeInvalidaException extends DendeException {

    public EventoCapacidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}