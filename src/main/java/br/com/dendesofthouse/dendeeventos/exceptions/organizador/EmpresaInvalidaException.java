package br.com.dendesofthouse.dendeeventos.exceptions.organizador;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EmpresaInvalidaException extends DendeException {

    public EmpresaInvalidaException(String mensagem) {
        super(mensagem);
    }
}