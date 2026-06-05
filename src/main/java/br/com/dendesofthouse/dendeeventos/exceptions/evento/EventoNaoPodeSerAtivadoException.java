package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoNaoPodeSerAtivadoException extends DendeException {

    public EventoNaoPodeSerAtivadoException(String mensagem) {
        super(mensagem);
    }
}