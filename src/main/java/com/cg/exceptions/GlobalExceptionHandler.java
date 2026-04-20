package com.cg.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNotFound(NotFoundException ex)
	{
		ErrorResponse error= new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.toString());
		return error;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalid(InvalidException ex)
	{
		ErrorResponse error= new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.toString());
		return error;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleExisting(CustomerAreadyExistsException ex)
	{
		ErrorResponse error= new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.toString());
		return error;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBadRequest(BadRequestException ex)
	{
		ErrorResponse error= new ErrorResponse();
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.toString());
		return error;
	}
}
