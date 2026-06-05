package br.com.dendesofthouse.dendeeventos.exceptions.repository;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class PersistenciaException extends DendeException {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }
}