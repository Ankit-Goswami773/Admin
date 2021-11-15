package com.adminproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeDetails {

	private long empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String designation;
	private String department;
	private double basicSalary;

	private String status;

	private String password;

}