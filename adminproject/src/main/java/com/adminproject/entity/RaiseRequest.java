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
public class RaiseRequest {

	private long raiseReqId;
	private String requestMessage;
	private String requestStatus;
	private EmployeeDetails employeeDetails;
}
