package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoDataFimAnteriorInicioException extends DendeException {

    public EventoDataFimAnteriorInicioException(String mensagem) {
        super(mensagem);
    }
}