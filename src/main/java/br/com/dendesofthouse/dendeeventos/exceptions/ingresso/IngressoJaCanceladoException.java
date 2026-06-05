package br.com.dendesofthouse.dendeeventos.exceptions.ingresso;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class IngressoJaCanceladoException extends DendeException {

    public IngressoJaCanceladoException(String mensagem) {
        super(mensagem);
    }
}