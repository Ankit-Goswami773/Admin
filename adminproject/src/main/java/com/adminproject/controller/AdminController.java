package com.adminproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adminproject.common.CommonResponse;

import com.adminproject.entity.EmployeeDetails;
import com.adminproject.service.AdminService;

@RestController
@CrossOrigin(origins = "http://localhost")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/saveEmployee")
	public ResponseEntity<CommonResponse> saveEmployee(@RequestBody EmployeeDetails details,
			@RequestHeader("Authorization") String token) {

		return new ResponseEntity<>(adminService.saveEmployee(details, token), HttpStatus.OK);
	}

	@GetMapping("/AllEmployee")
	public ResponseEntity<CommonResponse> getAllEmployee(@RequestHeader("Authorization") String token) {

		return new ResponseEntity<>(adminService.getAllEmployee(token), HttpStatus.OK);
	}

	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<CommonResponse> deleteEmployee(@PathVariable("empId") long empId,
			@RequestHeader("Authorization") String token) {
		return new ResponseEntity<>(adminService.deleteEmployee(empId, token), HttpStatus.OK);
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<CommonResponse> updateEmployee(@RequestBody EmployeeDetails details,
			@RequestHeader("Authorization") String token) {
		return new ResponseEntity<>(adminService.updateEmployee(details, token), HttpStatus.OK);
	}

	@GetMapping("getEmpById/{empId}")
	public ResponseEntity<CommonResponse> getEmployeeByEmpId(@PathVariable("empId") long empId,
			@RequestHeader("Authorization") String token) {

		return new ResponseEntity<>(adminService.getEmpById(empId, token), HttpStatus.OK);
	}

	@GetMapping("getAllRequestFromAdmin")
	public ResponseEntity<CommonResponse> getAllRaisedRequest(@RequestParam("requestStatus") String requestStatus,
			@RequestHeader("Authorization") String token) {

		return new ResponseEntity<>(adminService.getAllRaisedRequest(requestStatus,token), HttpStatus.OK);
	}

}
