package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class UsuarioJaAtivoException extends DendeException {

    public UsuarioJaAtivoException(String mensagem) {
        super(mensagem);
    }
}