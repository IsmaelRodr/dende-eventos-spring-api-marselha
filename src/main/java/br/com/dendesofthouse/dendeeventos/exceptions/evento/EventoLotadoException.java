package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoLotadoException extends DendeException {

    public EventoLotadoException(String mensagem) {
        super(mensagem);
    }
}