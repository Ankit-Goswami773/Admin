package com.adminproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.adminproject.common.ApplicationConstant;

@Component
public final class AdminUtility {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @apiNote use this for validate token from login module
	 * @date 9-NOV-2021
	 */
	public Boolean validateToken(String token) {

		String finalUrl = ApplicationConstant.JWT_TOKEN_VALIDATION + token;

		return restTemplate.getForObject(finalUrl, Boolean.class);

	}

}
