package com.adminproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adminproject.applications_exceptions.AdminCommonException;
import com.adminproject.common.ApplicationConstant;
import com.adminproject.common.CommonResponse;
import com.adminproject.entity.EmployeeDetails;
import com.adminproject.entity.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest extends AdminServiceImpl {

	@InjectMocks
	private AdminServiceImpl adminServiceImpl;

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ObjectMapper mapper;

	@Test
	void testSaveEmployee() throws JsonProcessingException {

		EmployeeDetails employeeDetails = EmployeeDetails.builder().empId(1).firstName("Ankit").lastName("Goswami")
				.emailId("ankit.goswami70@5exceptions.com").designation("java Developer").department("IT")
				.basicSalary(50000.0).status("ACTIVE").password("123").build();

		Login login = new Login(0, "ankit.goswami70@5exceptions.com", "123", "Employee", 1l);
		String restResponse = "{\"statusCode\":200,\"message\":\"employee saved sucessfully\",\"data\":{\"empId\":1,\"firstName\":\"Ankit\",\"lastName\":\"Goswami\",\"emailId\":\"ankit.goswami73@5exceptions.com\",\"designation\":\"java Developer\",\"department\":\"it\",\"basicSalary\":5000.0,\"status\":\"ACTIVE\"}}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		ObjectMapper mapperObj = new ObjectMapper();

		String jsonString = mapperObj.writeValueAsString(employeeDetails);

		HttpEntity<String> http = new HttpEntity<>(jsonString, headers);

		when(restTemplate.postForObject(ApplicationConstant.URL_FOR_SAVE_EMPLOYEE, http, String.class))
				.thenReturn(restResponse);

		String restResForSaveLogin = "{\"statusCode\":201,\"message\":\"success\",\"data\":{\"loginId\":1,\"userEmail\":\"ankit.goswami70@5exceptions.com\",\"password\":\"123\",\"roles\":\"Employee\",\"empId\":1}}";

		HttpHeaders headers2 = new HttpHeaders();
		headers2.set("content-type", "application/json");
		headers2.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		String jsonString2 = mapperObj.writeValueAsString(login);

		HttpEntity<String> http2 = new HttpEntity<>(jsonString2, headers2);

		when(restTemplate.postForObject(ApplicationConstant.URL_FOR_SAVE_LOGIN, http2, String.class))
				.thenReturn(restResForSaveLogin);

		CommonResponse saveEmployee = adminServiceImpl.saveEmployee(employeeDetails, "Bearer ");
		assertEquals(200, saveEmployee.getStatusCode());
		assertEquals("employee details saved sucessfully", saveEmployee.getMessage());

		// test exceptions
		String restResOfException = "{\"statusCode\":200,\"message\":\"employee saved sucessfully\"}";

		when(restTemplate.postForObject(ApplicationConstant.URL_FOR_SAVE_EMPLOYEE, http, String.class))
				.thenReturn(restResOfException);

		assertThrows(AdminCommonException.class, () -> {
			adminServiceImpl.saveEmployee(employeeDetails, "Bearer ");
		});

	}

	@Test
	void testGetAllEmployee() {

		EmployeeDetails emp1 = EmployeeDetails.builder().empId(1).firstName("Ankit").lastName("Goswami")
				.emailId("ankit.goswami4@5exceptions.com").designation("java Developer").department("IT")
				.basicSalary(50000.0).status("ACTIVE").build();
		EmployeeDetails emp2 = EmployeeDetails.builder().empId(1).firstName("akshy").lastName("kag")
				.emailId("akshy.kag@5exceptions.com").designation("java Developer").department("IT")
				.basicSalary(10000.0).status("ACTIVE").build();

		List<EmployeeDetails> empList = List.of(emp1, emp2);

		CommonResponse commonResponse = new CommonResponse(200, "OK", empList);

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		ResponseEntity<CommonResponse> responseEntity = new ResponseEntity<CommonResponse>(commonResponse,
				HttpStatus.OK);

		when(restTemplate.exchange(ApplicationConstant.URL_FOR_GET_ALL_EMPLOYEE, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class))
						.thenReturn(responseEntity);

		CommonResponse commonResReturn = adminServiceImpl.getAllEmployee("Bearer ");

		assertEquals(200, commonResReturn.getStatusCode());
		assertEquals("OK", commonResReturn.getMessage());

		// test for exception
		CommonResponse commonResForNull = null;
		ResponseEntity<CommonResponse> exceptionResEntity = new ResponseEntity<CommonResponse>(commonResForNull,
				HttpStatus.OK);
		when(restTemplate.exchange(ApplicationConstant.URL_FOR_GET_ALL_EMPLOYEE, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class))
						.thenReturn(exceptionResEntity);
		assertThrows(AdminCommonException.class, () -> {
			adminServiceImpl.getAllEmployee("Bearer ");
		});

	}

	@Test
	void testDeleteEmployee() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
		when(restTemplate.exchange(ApplicationConstant.URL_FOR_DELETE_EMPLOYEE + 1, HttpMethod.DELETE,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), String.class)).thenReturn(responseEntity);

		CommonResponse commonResReturn = adminServiceImpl.deleteEmployee(1l, "Bearer ");
		assertEquals(200, commonResReturn.getStatusCode());
		assertEquals("succesfull Delete", commonResReturn.getMessage());
	}

	@Test
	void testUpdateEmployee() {

		EmployeeDetails emp1 = EmployeeDetails.builder().empId(1).firstName("Ankit").lastName("Goswami")
				.emailId("ankit.goswami4@5exceptions.com").designation("java Developer").department("IT")
				.basicSalary(50000.0).status("ACTIVE").build();

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");
		HttpEntity<EmployeeDetails> entity = new HttpEntity<>(emp1, headers);

		ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
		when(restTemplate.exchange(ApplicationConstant.URL_FOR_UPDATE_EMPLOYEE, HttpMethod.PUT, entity, String.class))
				.thenReturn(responseEntity);

		CommonResponse commonResReturn = adminServiceImpl.updateEmployee(emp1, "Bearer ");
		assertEquals(200, commonResReturn.getStatusCode());
		assertEquals("succesfull update", commonResReturn.getMessage());

	}

	@Test
	void testGetEmpById() {
		EmployeeDetails emp1 = EmployeeDetails.builder().empId(1).firstName("Ankit").lastName("Goswami")
				.emailId("ankit.goswami4@5exceptions.com").designation("java Developer").department("IT")
				.basicSalary(50000.0).status("ACTIVE").build();

		CommonResponse commonResponse = new CommonResponse(200, "OK", emp1);

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		ResponseEntity<CommonResponse> responseEntity = new ResponseEntity<CommonResponse>(commonResponse,
				HttpStatus.OK);

		when(restTemplate.exchange(ApplicationConstant.URL_FOR_GETEMPLOYEEBYID + 1, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class))
						.thenReturn(responseEntity);

		CommonResponse commonResReturn = adminServiceImpl.getEmpById(1l, "Bearer ");
		assertEquals(200, commonResReturn.getStatusCode());
		assertEquals("OK", commonResReturn.getMessage());

		// test throw exception
		CommonResponse commonResForNull = null;
		ResponseEntity<CommonResponse> exceptionResEntity = new ResponseEntity<CommonResponse>(commonResForNull,
				HttpStatus.OK);

		when(restTemplate.exchange(ApplicationConstant.URL_FOR_GETEMPLOYEEBYID + 1, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class))
						.thenReturn(exceptionResEntity);

		assertThrows(AdminCommonException.class, () -> {
			adminServiceImpl.getEmpById(1, "Bearer ");
		});

	}

	@Test
	void testGetAllRaisedRequest() {

		String url = ApplicationConstant.URL_FOR_GET_ALL_RAISED_REQUEST + "?requestStatus=" + "";

		CommonResponse commonResponse = new CommonResponse(200, "OK", "");

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, "Bearer ");

		ResponseEntity<CommonResponse> responseEntity = new ResponseEntity<CommonResponse>(commonResponse,
				HttpStatus.OK);

		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(ApplicationConstant.PARAMETERS, headers),
				CommonResponse.class)).thenReturn(responseEntity);

		CommonResponse commonResReturn = adminServiceImpl.getAllRaisedRequest("", "Bearer ");

		assertEquals(200, commonResReturn.getStatusCode());
		assertEquals("OK", commonResReturn.getMessage());

		// test for exception
		CommonResponse commonResForNull = null;
		ResponseEntity<CommonResponse> exceptionResponseEntity = new ResponseEntity<CommonResponse>(commonResForNull,
				HttpStatus.OK);

		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(ApplicationConstant.PARAMETERS, headers),
				CommonResponse.class)).thenReturn(exceptionResponseEntity);

		assertThrows(AdminCommonException.class, () -> {
			adminServiceImpl.getAllRaisedRequest("", "Bearer ");
		});

	}

}
