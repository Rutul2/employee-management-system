package com.rutul.employee.employee;
import com.rutul.employee.common.ResourceNotFoundException;
import com.rutul.employee.department.Department;
import com.rutul.employee.department.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/employees")
public class EmployeeController {
 private final EmployeeRepository employees; private final DepartmentRepository departments;
 public EmployeeController(EmployeeRepository employees,DepartmentRepository departments){this.employees=employees;this.departments=departments;}
 @GetMapping public Page<Employee> list(@RequestParam(defaultValue="") String name,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,@RequestParam(defaultValue="id") String sortBy){
   if(page<0 || size<1 || size>100) throw new IllegalArgumentException("page must be non-negative and size must be 1..100");
   Pageable pageable=PageRequest.of(page,size,Sort.by(sortBy).ascending());
   return name.isBlank()?employees.findAll(pageable):employees.findByNameContainingIgnoreCase(name,pageable);
 }
 @GetMapping("/{id}") public Employee get(@PathVariable Long id){return employees.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee",id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('ADMIN')")
 public Employee create(@Valid @RequestBody EmployeeRequest request){if(employees.existsByEmailIgnoreCase(request.email()))throw new IllegalArgumentException("Email already exists");return employees.save(toEmployee(request,new Employee()));}
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
 public Employee update(@PathVariable Long id,@Valid @RequestBody EmployeeRequest request){Employee employee=get(id);if(!employee.getEmail().equalsIgnoreCase(request.email())&&employees.existsByEmailIgnoreCase(request.email()))throw new IllegalArgumentException("Email already exists");return employees.save(toEmployee(request,employee));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable Long id){employees.delete(get(id));}
 private Employee toEmployee(EmployeeRequest r,Employee e){e.setName(r.name());e.setEmail(r.email());e.setSalary(r.salary());if(r.departmentId()==null)e.setDepartment(null);else e.setDepartment(departments.findById(r.departmentId()).orElseThrow(()->new ResourceNotFoundException("Department",r.departmentId())));return e;}
 public record EmployeeRequest(@jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max=100) String name,@jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Email String email,@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("0.0") java.math.BigDecimal salary,Long departmentId){}
}
