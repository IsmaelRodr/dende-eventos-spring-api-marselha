package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoJaAtivoException extends DendeException {

    public EventoJaAtivoException(String mensagem) {
        super(mensagem);
    }
}