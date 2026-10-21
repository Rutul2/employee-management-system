package com.rutul.employee.department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="departments", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class Department {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank private String name;
  private String description;
  protected Department() {}
  public Department(String name, String description) { this.name=name; this.description=description; }
  public Long getId(){return id;} public String getName(){return name;} public void setName(String name){this.name=name;}
  public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
}
