package br.com.dendesofthouse.dendeeventos.exceptions.usuario;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EmailJaCadastradoException extends DendeException {

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}