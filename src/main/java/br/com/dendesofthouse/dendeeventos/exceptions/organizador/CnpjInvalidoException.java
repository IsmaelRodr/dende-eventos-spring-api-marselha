package br.com.dendesofthouse.dendeeventos.exceptions.organizador;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class CnpjInvalidoException extends DendeException {

    public CnpjInvalidoException(String mensagem) {
        super(mensagem);
    }
}