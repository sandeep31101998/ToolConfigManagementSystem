package com.tool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tool.ApiResponse;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(ToolNotFound.class)
	public ResponseEntity<ApiResponse> recordNotFoundException(){
		ApiResponse resp = new ApiResponse();
		resp.setMessage("record not found");
		return new ResponseEntity<ApiResponse>(resp,HttpStatus.ACCEPTED);
	}
}
