package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoDataInicioInvalidaException extends DendeException {

    public EventoDataInicioInvalidaException(String mensagem) {
        super(mensagem);
    }
}