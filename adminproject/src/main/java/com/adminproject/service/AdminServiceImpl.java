package com.adminproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
	public CommonResponse saveEmployee(EmployeeDetails details, String token) {
		Login login = new Login();

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.add(ApplicationConstant.AUTHORIZATION, token);

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

			login = saveLogin(mapper, login, token);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new CommonResponse(200, "employee details saved sucessfully", login);
	}

	public Login saveLogin(ObjectMapper mapper, Login login, String token) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.add(ApplicationConstant.AUTHORIZATION, token);
			 
			 String jsonString = mapper.writeValueAsString(login);

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

	@Override
	public CommonResponse getAllEmployee(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(ApplicationConstant.AUTHORIZATION, token);

		ResponseEntity<CommonResponse> responseFromApi = restTemplate.exchange(
				ApplicationConstant.URL_FOR_GET_ALL_EMPLOYEE, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class);
		CommonResponse commonResponse = responseFromApi.getBody();

		if (commonResponse== null) {
			throw new AdminCommonException("No data");

		}
		return commonResponse;

	}

	@Override
	public CommonResponse deleteEmployee(long empId, String token) {

		String url = ApplicationConstant.URL_FOR_DELETE_EMPLOYEE + empId;
		CommonResponse response = new CommonResponse();
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add(ApplicationConstant.AUTHORIZATION, token);

			restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(ApplicationConstant.PARAMETERS, headers),
					String.class);
			response.setStatusCode(200);
			response.setMessage("succesfull Delete");
			response.setData("");
		} catch (Exception e) {
			throw new AdminCommonException("Data not delete");
		}

		return response;
	}

	@Override
	public CommonResponse updateEmployee(EmployeeDetails details, String token) {
		CommonResponse response = new CommonResponse();
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add(ApplicationConstant.AUTHORIZATION, token);
			HttpEntity<EmployeeDetails> entity = new HttpEntity<>(details, headers);

			restTemplate.exchange(ApplicationConstant.URL_FOR_UPDATE_EMPLOYEE, HttpMethod.PUT, entity, String.class);
			response.setStatusCode(200);
			response.setMessage("succesfull update");
			response.setData(details);

		} catch (Exception e) {
			throw new AdminCommonException("Not updated");
		}

		return response;
	}

	@Override
	public CommonResponse getEmpById(long empId, String token) {
		String url = ApplicationConstant.URL_FOR_GETEMPLOYEEBYID + empId; 

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		ResponseEntity<CommonResponse> responseFromApi = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class);
		CommonResponse commonResponse = responseFromApi.getBody();

		if (commonResponse != null) {
			return commonResponse;
		}

		throw new AdminCommonException("No Employee for this id" + empId);

	}

	@Override
	public CommonResponse getAllRaisedRequest(String requestStatus, String token) {
		String url = ApplicationConstant.URL_FOR_GET_ALL_RAISED_REQUEST + "?requestStatus=" + requestStatus;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);

		ResponseEntity<CommonResponse> responseFromApi = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<>(ApplicationConstant.PARAMETERS, headers), CommonResponse.class);
		CommonResponse commonResponse = responseFromApi.getBody();

		if (commonResponse == null)
			throw new AdminCommonException("No request");
		return commonResponse;
	}

}
