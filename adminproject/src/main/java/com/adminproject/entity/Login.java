package com.adminproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	private int loginId;
	private String userEmail;
	private String password;
	private String roles;
	private long empId;

}
