package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class DataNascimentoInvalidaException extends DendeException {

    public DataNascimentoInvalidaException(String mensagem) {
        super(mensagem);
    }
}