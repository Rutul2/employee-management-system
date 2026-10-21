package com.rutul.employee.employee;
import com.rutul.employee.department.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
@Entity @Table(name="employees", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class Employee {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank @Size(max=100) private String name;
  @NotBlank @Email @Size(max=160) private String email;
  @NotNull @DecimalMin(value="0.0", inclusive=true) @Column(precision=12,scale=2) private BigDecimal salary;
  @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="department_id") private Department department;
  protected Employee() {}
  public Employee(String name,String email,BigDecimal salary,Department department){this.name=name;this.email=email;this.salary=salary;this.department=department;}
  public Long getId(){return id;} public String getName(){return name;} public void setName(String name){this.name=name;}
  public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
  public BigDecimal getSalary(){return salary;} public void setSalary(BigDecimal salary){this.salary=salary;}
  public Department getDepartment(){return department;} public void setDepartment(Department department){this.department=department;}
}
