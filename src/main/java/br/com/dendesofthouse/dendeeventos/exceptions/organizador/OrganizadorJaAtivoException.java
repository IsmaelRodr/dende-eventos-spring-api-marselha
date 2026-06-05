package br.com.dendesofthouse.dendeeventos.exceptions.organizador;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class OrganizadorJaAtivoException extends DendeException {

    public OrganizadorJaAtivoException(String mensagem) {
        super(mensagem);
    }
}