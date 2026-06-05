package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoPrecoIngressoInvalidoException extends DendeException {

    public EventoPrecoIngressoInvalidoException(String mensagem) {
        super(mensagem);
    }
}