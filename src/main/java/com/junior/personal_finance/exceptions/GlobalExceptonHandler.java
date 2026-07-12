package com.junior.personal_finance.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptonHandler {

    @ExceptionHandler(RecursosNaoEncontradosException.class)
    public ResponseEntity<String> naoEncontrado(RecursosNaoEncontradosException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validacaoInvalida(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return ResponseEntity.status(400).body(mensagem);
    }
}