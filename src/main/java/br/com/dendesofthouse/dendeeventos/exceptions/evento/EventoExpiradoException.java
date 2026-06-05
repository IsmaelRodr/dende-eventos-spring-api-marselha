package br.com.dendesofthouse.dendeeventos.exceptions.evento;

import br.com.dendesofthouse.dendeeventos.exceptions.DendeException;

public class EventoExpiradoException extends DendeException {

  public EventoExpiradoException(String mensagem) {
    super(mensagem);
  }
}