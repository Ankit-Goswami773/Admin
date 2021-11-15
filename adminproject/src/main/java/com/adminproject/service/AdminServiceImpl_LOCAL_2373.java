package com.adminproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adminproject.applications_exceptions.AdminCommonException;
import com.adminproject.common.ApplicationConstant;
import com.adminproject.common.CommonResponse;
import com.adminproject.entity.EmployeeDetails;
import com.adminproject.entity.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CommonResponse saveEmployee(EmployeeDetails details) {
		Login login = new Login();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(details);

			HttpEntity<String> http = new HttpEntity<>(jsonString, headers);

			String restResponse = restTemplate.postForObject(ApplicationConstant.URL_FOR_SAVE_EMPLOYEE, http,
					String.class);

			CommonResponse commonResponse = mapper.readValue(restResponse, CommonResponse.class);

			EmployeeDetails savedEmployee = mapper.convertValue(commonResponse.getData(), EmployeeDetails.class);

			if (savedEmployee == null) {
				throw new AdminCommonException("Employee details not save");
			}

			/////////// save login///////////

			login.setUserEmail(details.getEmailId());
			login.setPassword(details.getPassword());
			login.setRoles("Employee");
			login.setEmpId(savedEmployee.getEmpId());

			login = saveLogin(mapper, login);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new CommonResponse(200, "employee details saved sucessfully", login);
	}

	private Login saveLogin(ObjectMapper mapper, Login login) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			String jsonString;
			jsonString = mapper.writeValueAsString(login);

			HttpEntity<String> http = new HttpEntity<>(jsonString, headers);

			String restResponse = restTemplate.postForObject(ApplicationConstant.URL_FOR_SAVE_LOGIN, http,
					String.class);

			CommonResponse commonResponse = mapper.readValue(restResponse, CommonResponse.class);

			login = mapper.convertValue(commonResponse.getData(), Login.class);
			if (login == null) {
				throw new AdminCommonException("login details not save");
			}

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return login;

	}

}
