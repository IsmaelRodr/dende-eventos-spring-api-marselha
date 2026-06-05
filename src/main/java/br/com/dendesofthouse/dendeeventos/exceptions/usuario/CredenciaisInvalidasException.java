package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class CredenciaisInvalidasException extends DendeException {

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}