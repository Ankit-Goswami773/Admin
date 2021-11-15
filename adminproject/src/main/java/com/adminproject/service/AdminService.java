package com.adminproject.service;

import com.adminproject.common.CommonResponse;

import com.adminproject.entity.EmployeeDetails;

public interface AdminService {

	public CommonResponse saveEmployee(EmployeeDetails details, String token);

	public CommonResponse getAllEmployee(String token);

	public CommonResponse deleteEmployee(long empId, String token);

	public CommonResponse updateEmployee(EmployeeDetails details, String token);

	public CommonResponse getEmpById(long empId, String token);

	public CommonResponse getAllRaisedRequest(String requestStatus, String token);

}
