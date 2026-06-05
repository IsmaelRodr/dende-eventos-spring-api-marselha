package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoInativoException extends DendeException {

    public EventoInativoException(String mensagem) {
        super(mensagem);
    }
}