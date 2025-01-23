package net.javaguides.springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
    private ApplicationContext context;
	
	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}		
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}
	
	// update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
//	@GetMapping("/cpu")
//    public String increaseCpuUsage() {
//        // Create multiple threads that keep the CPU busy
//        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
//            new Thread(() -> {
//                while (true) {
//                    Math.sqrt(Math.random()); // Perform CPU-intensive operations
//                }
//            }).start();
//        }
//        return "CPU usage increased!";
//    }
//
//    @GetMapping("/memory")
//    public String increaseMemoryUsage() {
//        // Allocate large amounts of memory
//        List<byte[]> memoryConsumers = new ArrayList<>();
//        try {
//            while (true) {
//                memoryConsumers.add(new byte[10 * 1024 * 1024]); // Allocate 10 MB chunks
//            }
//        } catch (OutOfMemoryError e) {
//            return "Memory exhausted!";
//        }
//    }
	@GetMapping("/crash")
    public String crashApplication() {
        List<byte[]> memoryConsumers = new ArrayList<>();
        try {
            while (true) {
                // Allocate 10 MB chunks until the application runs out of memory
                memoryConsumers.add(new byte[10 * 1024 * 1024]);
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Application crashed due to memory exhaustion!");
            throw e; // Explicitly throw the error to crash the application
        }
    }

    // Endpoint to simulate high CPU usage
    @GetMapping("/high-cpu")
    public String highCpuLoad() {
        int numCores = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < numCores; i++) {
            new Thread(() -> {
                while (true) {
                    // Infinite loop with some computation to keep the CPU busy
                    Math.pow(Math.random(), Math.random());
                }
            }).start();
        }
        return "High CPU load triggered!";
    }
    
    @GetMapping("stop-server")
    public String shutdownApplication() {
        System.out.println("Shutting down the application...");
        SpringApplication.exit(context, () -> 0); // Exit with code 0 (normal termination)
        return "Application is shutting down...";
    }
}
