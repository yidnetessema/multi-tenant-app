//package com.itsoftwaretech.demo.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handles @Valid validation errors
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String field = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(field, message);
//        });
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
//
//    // Generic fallback for other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
//}
