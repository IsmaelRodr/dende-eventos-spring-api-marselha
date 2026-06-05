package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EmailInvalidoException extends DendeException {

    public EmailInvalidoException(String mensagem) {
        super(mensagem);
    }
}