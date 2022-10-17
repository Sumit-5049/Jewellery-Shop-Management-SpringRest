package com.virtusa.jsm.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice

public class GlobalExceptionHandler {
	
	  @ExceptionHandler(DataNotFoundException.class)
	    public @ResponseBody  ResponseEntity<?> DataNotFoundException(DataNotFoundException e, HttpServletRequest req) {
	          return new ResponseEntity<>(new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI()),HttpStatus.NOT_FOUND);
	    }
	  
	  @ExceptionHandler(InvalidCredentialException.class)
	    public @ResponseBody  ResponseEntity<?> InvalidCredentialException(InvalidCredentialException e, HttpServletRequest req) {
	          return new ResponseEntity<>(new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI()),HttpStatus.UNAUTHORIZED);
	    }
	  
	  @ExceptionHandler(StockUnavailableException.class)
	    public @ResponseBody  ResponseEntity<?> StockUnavailableException(StockUnavailableException e, HttpServletRequest req) {
	          return new ResponseEntity<>(new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI()),HttpStatus.NOT_FOUND);
	    }
	
	  @ExceptionHandler(WrongFormatException.class)
	    public @ResponseBody  ResponseEntity<?> WrongFormatException(WrongFormatException e, HttpServletRequest req) {
	          return new ResponseEntity<>(new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI()),HttpStatus.NOT_FOUND);
	    }
	  
	  @ExceptionHandler(DuplicateException.class)
	    public @ResponseBody  ResponseEntity<?> DuplicateException(DuplicateException e, HttpServletRequest req) {
	          return new ResponseEntity<>(new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI()),HttpStatus.NOT_ACCEPTABLE);
	    }
	
	  
//	  @ExceptionHandler(DataNotFoundException.class)
//	    public @ResponseBody  ErrorInfo ProductErrorException(DataNotFoundException e, HttpServletRequest req) {
//	          return new ErrorInfo(LocalDateTime.now(), e.getMessage(), req.getRequestURI());
//	    }
	  
}
