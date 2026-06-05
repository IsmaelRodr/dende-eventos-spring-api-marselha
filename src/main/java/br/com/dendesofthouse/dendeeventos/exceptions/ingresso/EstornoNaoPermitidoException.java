package br.com.dendesofthouse.dendeeventos.exceptions.ingresso;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EstornoNaoPermitidoException extends DendeException {

  public EstornoNaoPermitidoException(String mensagem) {
    super(mensagem);
  }
}