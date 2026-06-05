package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class UsuarioJaInativoException extends DendeException {

    public UsuarioJaInativoException(String mensagem) {
        super(mensagem);
    }
}