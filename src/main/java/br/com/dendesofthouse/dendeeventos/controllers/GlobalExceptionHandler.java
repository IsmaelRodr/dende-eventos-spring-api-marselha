package br.com.dendesofthouse.dendeeventos.controllers;

import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.*;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.*;
import br.com.dendesofthouse.dendeeventos.exceptions.organizador.*;
import br.com.dendesofthouse.dendeeventos.exceptions.repository.PersistenciaException;
import br.com.dendesofthouse.dendeeventos.exceptions.usuario.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Bad Request
    @ExceptionHandler({
            DadosInvalidosException.class,
            DataNascimentoInvalidaException.class,
            EmailInvalidoException.class,
            EmpresaInvalidaException.class,
            CnpjInvalidoException.class,
            EventoCapacidadeInvalidaException.class,
            EventoPrecoIngressoInvalidoException.class,
            EventoTaxaCancelamentoInvalidaException.class,
            EventoDataInicioInvalidaException.class,
            EventoDataFimInvalidaException.class,
            EventoDataFimAnteriorInicioException.class,
            EventoDuracaoInvalidaException.class
    })
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 400 - Validation errors (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 401 - Unauthorized
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<String> handleUnauthorized(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 404 - Not Found
    @ExceptionHandler({
            UsuarioNaoEncontradoException.class,
            OrganizadorNaoEncontradoException.class,
            EventoNaoEncontradoException.class,
            EventoPrincipalNaoEncontradoException.class,
            IngressoNaoEncontradoException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 409 - Conflict
    @ExceptionHandler({
            EmailJaCadastradoException.class,
            EventoJaAtivoException.class,
            EventoJaInativoException.class,
            OrganizadorJaAtivoException.class,
            OrganizadorJaInativoException.class,
            UsuarioJaAtivoException.class,
            UsuarioJaInativoException.class,
            EventoSemIngressosDisponiveisException.class,
            CancelamentoNaoPermitidoException.class,
            IngressoJaCanceladoException.class
    })
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // 403 - Forbidden
    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<String> handleForbidden(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // 422 - Unprocessable Entity
    @ExceptionHandler({
            EventoInativoException.class,
            EventoExpiradoException.class,
            OrganizadorComEventosAtivosException.class
    })
    public ResponseEntity<String> handleUnprocessableEntity(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    // 500 - Internal Server Error
    @ExceptionHandler({PersistenciaException.class, RuntimeException.class})
    public ResponseEntity<String> handleInternalServerError(Exception ex) {
        ex.printStackTrace(); // log adequado seria melhor
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno no servidor.");
    }
}
