package com.rutul.employee.department;
import jakarta.validation.Valid;
import com.rutul.employee.common.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/departments")
public class DepartmentController {
  private final DepartmentRepository repository;
  public DepartmentController(DepartmentRepository repository){this.repository=repository;}
  @GetMapping public List<Department> all(){return repository.findAll();}
  @GetMapping("/{id}") public Department one(@PathVariable Long id){return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department",id));}
  @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('ADMIN')")
  public Department create(@Valid @RequestBody Department input){if(repository.existsByNameIgnoreCase(input.getName())) throw new IllegalArgumentException("Department name already exists"); return repository.save(input);}
  @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
  public Department update(@PathVariable Long id,@Valid @RequestBody Department input){Department d=one(id);d.setName(input.getName());d.setDescription(input.getDescription());return repository.save(d);}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable Long id){repository.delete(one(id));}
}
