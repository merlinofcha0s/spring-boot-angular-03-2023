package com.plb.vinylmgt;

import com.plb.vinylmgt.entity.*;
import com.plb.vinylmgt.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;

@SpringBootApplication
public class VinylmgtApplication {

    public static void main(String[] args) {
        System.out.println("test devtool");
        SpringApplication.run(VinylmgtApplication.class, args);
    }

    @Bean
    public CommandLineRunner createData(UserRepository userRepository,
                                        VinylRepository vinylRepository,
                                        AuthorRepository authorRepository,
                                        EmployeeRepository employeeRepository,
                                        JobRepository jobRepository,
                                        TaskRepository taskRepository) {
        return args -> {
            User newUser = new User("toto@toto.com", "azerty", "toto",
                    "titi");
            userRepository.save(newUser);

            Author linkinParkSaved = authorRepository.save(new Author("Linkin Park", LocalDate.of(1996, 1, 1)));
            Vinyl inTheEnd = new Vinyl("In the end", LocalDate.of(2000, 10, 24), linkinParkSaved, newUser);
            Vinyl papercut = new Vinyl("Papercut", LocalDate.of(2000, 10, 24), linkinParkSaved, newUser);
            Vinyl oneStepCloser = new Vinyl("One step closer", LocalDate.of(2000, 10, 24), linkinParkSaved, newUser);
            Vinyl pointsOfAuthority = new Vinyl("Points of Authority", LocalDate.of(2000, 10, 24), linkinParkSaved, newUser);

            vinylRepository.save(inTheEnd);
            vinylRepository.save(papercut);
            vinylRepository.save(oneStepCloser);
            vinylRepository.save(pointsOfAuthority);
            vinylRepository.findAll().forEach(System.out::println);
            userRepository.findAll().forEach(System.out::println);
            authorRepository.findAll().forEach(System.out::println);

            Employee employee1 = new Employee();
            employee1.setSalary(1600L);
            employee1.setCommissionPct(10L);
            employee1.setHireDate(Instant.now());

            Employee employee2 = new Employee();
            employee2.setSalary(1500L);
            employee2.setCommissionPct(10L);
            employee2.setHireDate(Instant.now());

            Employee employee3 = new Employee();
            employee3.setSalary(1700L);
            employee3.setCommissionPct(10L);
            employee3.setHireDate(Instant.now());

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);

            Job job = new Job();
            job.setJobTitle("Developer");
            job.setMaxSalary(4000L);
            job.setMinSalary(2000L);
            job.setEmployee(employee1);
            jobRepository.save(job);

            Task task = new Task();
            task.setTitle("Build spring application");
            task.setDescription("Implements new features");
            task.getJobs().add(job);
            taskRepository.save(task);

            task.setDescription("After change in transaction");

            job.getTasks().add(task);
        };
    }
}
