package com.adminproject.common;

public class ApplicationConstant {

	private ApplicationConstant() {
		super();
	}

	public static final String URL_FOR_SAVE_EMPLOYEE = "http://localhost:8095/employee/addEmp";
	public static final String URL_FOR_SAVE_LOGIN = "http://localhost:8096/saveLoginCredentials";
	public static final String URL_FOR_GET_ALL_EMPLOYEE = "http://localhost:8095/employee/getAllEmployee";
	public static final String URL_FOR_DELETE_EMPLOYEE = "http://localhost:8095/employee/deleteEmployee/";
	public static final String URL_FOR_UPDATE_EMPLOYEE = "http://localhost:8095/employee/updateEmployee";
	public static final String URL_FOR_GETEMPLOYEEBYID = "http://localhost:8095/employee/getEmpById/";
	public static final String URL_FOR_GET_ALL_RAISED_REQUEST = "http://localhost:8095/getAllRequestFromAdmin";
	public static final String JWT_TOKEN_VALIDATION = "http://localhost:8096/validateToken?token=";

	public static final String AUTHORIZATION = "Authorization";
	public static final String PARAMETERS = "parameters";

}
