package br.com.dendesofthouse.dendeeventos.exceptions.repository;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class OperacaoRepositorioException extends DendeException {

    public OperacaoRepositorioException(String mensagem) {
        super(mensagem);
    }
}