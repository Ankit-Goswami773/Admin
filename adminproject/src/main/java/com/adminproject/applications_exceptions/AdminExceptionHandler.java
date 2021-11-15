package com.adminproject.applications_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adminproject.common.CommonResponse;

@ControllerAdvice
public class AdminExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AdminCommonException.class)
	public ResponseEntity<CommonResponse> employeeNotFound(AdminCommonException notFound) {
		CommonResponse message = new CommonResponse();
		message.setStatusCode(404);
		message.setMessage(notFound.getMessage());
		message.setData("");
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
