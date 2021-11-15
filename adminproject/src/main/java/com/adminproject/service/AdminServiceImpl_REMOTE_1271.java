package com.adminproject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adminproject.common.CommonResponse;
import com.adminproject.entity.EmployeeDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminServiceImpl implements AdminService {

	private static final String url = "http://localhost:8095/employee/addEmp";


	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CommonResponse saveEmployee(EmployeeDetails details) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");

		ObjectMapper mapper = new ObjectMapper();

		// Converting the Object to JSONString
		try {
			String jsonString = mapper.writeValueAsString(details);

			HttpEntity<String> http = new HttpEntity<>(jsonString, headers);
			System.out.println("Calling to rest response and payload is  " + jsonString);

			String restResponse = restTemplate.postForObject(url, http, String.class);

			CommonResponse commonResponse = mapper.readValue(restResponse, CommonResponse.class);

			EmployeeDetails convertValue = mapper.convertValue(commonResponse.getData(), EmployeeDetails.class);

			System.err.println("nashisishpioh   details -->" + convertValue.getEmailId());

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}


		return null;
	}

}
