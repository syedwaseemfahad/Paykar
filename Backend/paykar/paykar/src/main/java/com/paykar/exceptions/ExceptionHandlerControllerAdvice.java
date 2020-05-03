package com.paykar.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse("User Does Not Exists");
		return error;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatus status,WebRequest request){

		List<String> list=new ArrayList<String>();
		for(ObjectError error1:ex.getBindingResult().getAllErrors()) {
			list.add(error1.getDefaultMessage());
		}
		SignUpExceptionResponse error = new SignUpExceptionResponse("Validation Failed",list);
		return new ResponseEntity<Object>(error,HttpStatus.BAD_REQUEST);
	
	}
//
//	
//	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,HttpHeaders headers,HttpStatus status,WebRequest request){

		ExceptionResponse error = new ExceptionResponse(ex.getLocalizedMessage());

		return new ResponseEntity<>(error,HttpStatus.METHOD_NOT_ALLOWED);
	
	}
	
	
	
	@ExceptionHandler({InvalidLoginCredentialsException.class,Exception.class})
	protected ResponseEntity<Object> handleInvalidLogin(InvalidLoginCredentialsException ex){
		return new ResponseEntity<Object>(new ExceptionResponse(ex.getLocalizedMessage()),HttpStatus.FORBIDDEN);
	}


	@ExceptionHandler(MobileNumberRegisteredException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleMobileNumberRegistered(final MobileNumberRegisteredException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(exception.getLocalizedMessage());
		return error;
	}

	@ExceptionHandler(EmailRegisteredException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleEmailRegistered(final EmailRegisteredException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(exception.getLocalizedMessage());
		return error;
	}

	
	@ExceptionHandler(UserNameExistsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleUserNameExistsException(final UserNameExistsException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(exception.getLocalizedMessage());
		return error;
	}


	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleInsufficientBalance(final InsufficientBalanceException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(exception.getLocalizedMessage());
		return error;
	}

	@ExceptionHandler(AccountNumberDoesNotExistsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleAccountNumberDoesNotExists(final AccountNumberDoesNotExistsException exception,final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse(exception.getLocalizedMessage());
		return error;
	}




}







	
	
	
	
	
	
	
	
	
	
	
	
	
	