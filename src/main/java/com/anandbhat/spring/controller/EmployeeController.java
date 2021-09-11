package com.anandbhat.spring.controller;


import com.anandbhat.spring.exception.ResourceNotFoundException;
import com.anandbhat.spring.model.Employee;
import com.anandbhat.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final String RESOURCE_NOT_FOUND = "Employee Not found for the id :";
    @Autowired
    private EmployeeRepository employeeRepository;

    //  Creating the  get Methods
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/requestParam/")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam(value = "id") long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/pathVariable/{id}")
    public ResponseEntity<Employee> getEmployeeByIdPath(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long id, @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND + id));
        employeeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
