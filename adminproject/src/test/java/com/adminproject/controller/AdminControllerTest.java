package com.adminproject.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.adminproject.common.CommonResponse;
import com.adminproject.entity.EmployeeDetails;
import com.adminproject.service.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private AdminService adminService;

	@InjectMocks
	private AdminController adminController;

	@BeforeEach
	void setup() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

	}

	@Test
	void testgetAllEmployee() throws Exception {

		String token = "Bearer ";

		List<EmployeeDetails> empList = new ArrayList<EmployeeDetails>();

		EmployeeDetails emp1 = EmployeeDetails.builder().empId(1).firstName("Ankit").lastName("Goswami")
				.emailId("ankit.goswami4@5exceptions.com").designation("java Developer").designation("IT")
				.basicSalary(50000.0).status("ACTIVE").build();

		EmployeeDetails emp2 = EmployeeDetails.builder().empId(1).firstName("akshy").lastName("kag")
				.emailId("akshy.kag@5exceptions.com").designation("java Developer").designation("IT")
				.basicSalary(10000.0).status("ACTIVE").build();

		empList.add(emp1);
		empList.add(emp2);

		CommonResponse commonResponse = new CommonResponse(200, "OK", empList);

		when(adminService.getAllEmployee(token)).thenReturn(commonResponse);
		
		ResponseEntity<CommonResponse> allEmployee = adminController.getAllEmployee(token);
		
	
		
		System.err.println(allEmployee);
		

	}

}
