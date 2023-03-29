package com.cos.blog.controller;

import lombok.Data;

@Data
public class OAuthToken {
	
	private String access_token;
	
	private String token_type;
	
	private String refresh_token;
	
	private String expires_in;
	
	private String scope;
	
	private String refresh_token_expires_in;
	
//	  "access_token": "uCToPBYMXo4hFVIGDyKmsu5u5NoRXGZL2vgcXnAECj1z6wAAAYciKsob",
//	  "token_type": "bearer",
//	  "refresh_token": "xfe_T_c8PUO7x_CNSRi9AxCCA_Nyee22b1-EzMSoCj1z6wAAAYciKsoZ",
//	  "expires_in": 21599,
//	  "scope": "account_email profile_nickname",
//	  "refresh_token_expires_in": 5183999

}
