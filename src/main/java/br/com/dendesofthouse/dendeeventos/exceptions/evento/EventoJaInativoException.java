package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoJaInativoException extends DendeException {

    public EventoJaInativoException(String mensagem) {
        super(mensagem);
    }
}