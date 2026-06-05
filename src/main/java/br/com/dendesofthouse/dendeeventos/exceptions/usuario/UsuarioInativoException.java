package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class UsuarioInativoException extends DendeException {

    public UsuarioInativoException(String mensagem) {
        super(mensagem);
    }
}