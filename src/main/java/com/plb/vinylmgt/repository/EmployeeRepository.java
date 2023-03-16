package com.plb.vinylmgt.repository;


import com.plb.vinylmgt.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findAllBySalary(Long salary);

    List<Employee> findAllByJobs_jobTitleContains(String jobTitle);

    int deleteBySalary(Long salary);
}
