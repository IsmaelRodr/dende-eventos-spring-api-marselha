package br.com.dendesofthouse.dendeeventos.exceptions.ingresso;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class CancelamentoNaoPermitidoException extends DendeException {

    public CancelamentoNaoPermitidoException(String mensagem) {
        super(mensagem);
    }
}