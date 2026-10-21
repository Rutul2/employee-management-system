package com.rutul.employee.common;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<Map<String,String>> notFound(ResourceNotFoundException ex){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));}
 @ExceptionHandler({IllegalArgumentException.class, DataIntegrityViolationException.class}) ResponseEntity<Map<String,String>> badRequest(RuntimeException ex){return ResponseEntity.badRequest().body(Map.of("error", "Invalid request or duplicate value"));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){Map<String,String> errors=new LinkedHashMap<>();ex.getBindingResult().getFieldErrors().forEach(e->errors.put(e.getField(),e.getDefaultMessage()));return ResponseEntity.badRequest().body(Map.of("error","Validation failed","fields",errors));}
}
