package br.com.dendesofthouse.dendeeventos.exceptions.ingresso;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class IngressoInvalidoException extends DendeException {

    public IngressoInvalidoException(String mensagem) {
        super(mensagem);
    }
}