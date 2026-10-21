package com.rutul.employee.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
 boolean existsByEmailIgnoreCase(String email);
 Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
