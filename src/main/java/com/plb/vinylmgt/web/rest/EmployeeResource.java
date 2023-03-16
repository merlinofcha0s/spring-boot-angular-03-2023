package com.plb.vinylmgt.web.rest;

import com.plb.vinylmgt.entity.Employee;
import com.plb.vinylmgt.service.EmployeeService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> allEmployees = employeeService.getAll();

        if (allEmployees.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allEmployees);
        }
    }

    @GetMapping("/get-by-salary")
    public ResponseEntity<List<Employee>> getAllBySalary(@RequestParam Long salary) {
        List<Employee> allBySalary = employeeService.getBySalary(salary);

        if (allBySalary.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allBySalary);
        }
    }

    @GetMapping("/get-by-job-title")
    public ResponseEntity<List<Employee>> getByJobTitle(@RequestParam String jobTitle) {
        List<Employee> allByJobTitle = employeeService.getByJobTitle(jobTitle);

        if (allByJobTitle.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allByJobTitle);
        }
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee Employee) {
        return ResponseEntity.ok(employeeService.save(Employee));
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee Employee) {
        return ResponseEntity.ok(employeeService.save(Employee));
    }

    @DeleteMapping("/by-salary")
    public ResponseEntity<Void> delete(@RequestParam Long salary) {
        int nbRemoved = employeeService.deleteBySalary(salary);
        if (nbRemoved == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/by-id")
    public ResponseEntity<Void> deleteById(@RequestParam UUID id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException erdae) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee not found with id : " + id);
        }
    }
}
