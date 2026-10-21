package com.rutul.employee.common;
public class ResourceNotFoundException extends RuntimeException {
 public ResourceNotFoundException(String type,Long id){super(type+" not found with id: "+id);}
}
