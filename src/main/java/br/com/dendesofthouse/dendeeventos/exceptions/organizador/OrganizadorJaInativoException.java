package br.com.dendesofthouse.dendeeventos.exceptions.organizador;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class OrganizadorJaInativoException extends DendeException {

    public OrganizadorJaInativoException(String mensagem) {
        super(mensagem);
    }
}