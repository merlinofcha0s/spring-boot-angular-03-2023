package com.plb.vinylmgt.service;

import com.plb.vinylmgt.entity.Employee;
import com.plb.vinylmgt.entity.Job;
import com.plb.vinylmgt.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getBySalary(Long salary) {
        return  employeeRepository.findAllBySalary(salary);
    }

    public List<Employee> getByJobTitle(String jobTitle) {
        return employeeRepository.findAllByJobs_jobTitleContains(jobTitle);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public int deleteBySalary(Long salary) {
        return employeeRepository.deleteBySalary(salary);
    }

    @Transactional
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }
}
