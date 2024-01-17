package com.yourcast.api.http.advice;

import com.yourcast.api.exception.EntityAlreadyExistException;
import com.yourcast.api.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yourcast.api.exception.EntityNotFoundException;
import com.yourcast.api.http.model.AppError;
import com.yourcast.api.http.model.AppErrorReason;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<AppError> catchEntityNotFoundException(EntityNotFoundException exception) {
    log.error("Handled exception with message: {}", exception.getMessage(), exception);

    AppErrorReason reason = AppErrorReason.builder()
        .code(HttpStatus.NOT_FOUND.toString())
        .severity("ERROR")
        .message(exception.getMessage())
        .build();

    AppError error = AppError.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .reasons(List.of(reason))
        .build();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<AppError> catchMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    log.error("Handled exception with message: {}", exception.getMessage(), exception);

    AppErrorReason reason = AppErrorReason.builder()
        .code(HttpStatus.UNPROCESSABLE_ENTITY.toString())
        .severity("ERROR")
        .message("Request validation failed.")
        .build();

    AppError error = AppError.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .reasons(List.of(reason))
        .build();
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(EntityAlreadyExistException.class)
  public ResponseEntity<AppError> catchEntityAlreadyExistException(EntityAlreadyExistException exception) {
    log.error("Handled exception with message: {}", exception.getMessage(), exception);

    AppErrorReason reason = AppErrorReason.builder()
        .code(HttpStatus.UNPROCESSABLE_ENTITY.toString())
        .severity("ERROR")
        .message(exception.getMessage())
        .build();

    AppError error = AppError.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .reasons(List.of(reason))
        .build();
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<AppError> catchEntityAlreadyExistException(UnauthorizedException exception) {
    log.error("Handled exception with message: {}", exception.getMessage(), exception);

    AppErrorReason reason = AppErrorReason.builder()
        .code(HttpStatus.UNAUTHORIZED.toString())
        .severity("ERROR")
        .message(exception.getMessage())
        .build();

    AppError error = AppError.builder()
        .status(HttpStatus.UNAUTHORIZED.value())
        .reasons(List.of(reason))
        .build();
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }
}