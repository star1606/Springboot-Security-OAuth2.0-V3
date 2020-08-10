package com.cos.securityex01.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attriubtes;

	public GoogleUserInfo(Map<String, Object> attriubtes) {
		this.attriubtes = attriubtes;
	}
	
	@Override
	public String getProviderId() {
		
		return (String) attriubtes.get("sub");
	}
	
	@Override
	public String getProvider() {
		return (String) attriubtes.get("google");
	}
	
	@Override
	public String getName() {
		return (String) attriubtes.get("name");
	}
	
	@Override
	public String getEmail() {
		return (String) attriubtes.get("email");
	}
	
	
}
