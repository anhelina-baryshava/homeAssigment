package org.core.builders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest{
	private String password;
	private String apiVersion;
	private Object extraParams;
	private int partnerId;
	private String username;
}