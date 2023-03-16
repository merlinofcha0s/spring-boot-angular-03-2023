package com.plb.vinylmgt.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plb.vinylmgt.entity.Employee;
import com.plb.vinylmgt.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeResourceIT {

    public static final long DEFAULT_COMMISSION_PCT = 12L;
    public static final long DEFAULT_SALARY = 1400L;
    public static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochSecond(1000000);

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @Autowired
    private MockMvc restEmployeeMockMVC;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        employeeRepository.deleteAll();
        employee = createEntity();
    }

    public static Employee createEntity() {
        Employee employee = new Employee();
        employee.setCommissionPct(DEFAULT_COMMISSION_PCT);
        employee.setSalary(DEFAULT_SALARY);
        employee.setHireDate(DEFAULT_HIRE_DATE);
        return employee;
    }

    @Test
    public void getAllEmployees() throws Exception {
        employeeRepository.save(employee);

        Employee secondEmployee = createEntity();
        employeeRepository.save(secondEmployee);

        restEmployeeMockMVC.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].salary")
                        .value(hasItems(employee.getSalary().intValue(),
                                secondEmployee.getSalary().intValue())))
                .andExpect(jsonPath("$.[*].hireDate")
                        .value(hasItems(employee.getHireDate().toString(),
                                secondEmployee.getHireDate().toString())));
    }


    @Test
    public void save() throws Exception {
        int databaseSizeCreate = employeeRepository.findAll().size();

        Employee secondEmployee = createEntity();

        restEmployeeMockMVC.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(secondEmployee)))
                .andExpect(status().isOk());

        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size()).isEqualTo(databaseSizeCreate + 1);
    }
}
