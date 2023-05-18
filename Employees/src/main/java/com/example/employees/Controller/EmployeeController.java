package com.example.employees.Controller;

import com.example.employees.Model.Employee;
import com.example.employees.apiResponce.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    // array as Database
    ArrayList<Employee> employees = new ArrayList<>();

    // get all Employee
    @GetMapping("/getAll")
    public ArrayList<Employee> getEmployee() {
        return employees;
    }


    //Add new Employee
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEmployee(@Valid @RequestBody Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String massage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(massage));
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));
    }

    // Update Employee
    @PutMapping("/updated/{index}")
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee, Errors errors, @PathVariable int index) {
        if (errors.hasErrors()) {
            String massage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(massage));
        }
        Employee existingEmployee = employees.get(index);
        existingEmployee.setId(employee.getId());
        existingEmployee.setName(employee.getName());
        existingEmployee.setAge(employee.getAge());
        existingEmployee.setOnLeave(employee.getOnLeave());
        existingEmployee.setRole(employee.getRole());
        existingEmployee.setAnnualLeave(employee.getAnnualLeave());
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated" ));
    }

    // delete Employee
    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
    }

    //Employees apply for an annual
    @PutMapping("/{index}/apply-annual-leave")
    public ResponseEntity applyAnnualLeave(@PathVariable int index, @RequestBody Employee employee) {

        if (employee.getOnLeave()) {
            return ResponseEntity.badRequest().body("Employee is already on leave.");
        }
        if (employee.getAnnualLeave() <= 0) {
            return ResponseEntity.badRequest().body("Employee has no annual leave remaining.");
        }
        // if applied accepted
        employee.setOnLeave(true);
        employee.setAnnualLeave(employee.getAnnualLeave() - 1);
        return ResponseEntity.ok(employee);
    }


}
