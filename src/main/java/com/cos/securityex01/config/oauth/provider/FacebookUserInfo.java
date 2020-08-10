package com.cos.securityex01.config.oauth.provider;

import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attriubtes;

	public FacebookUserInfo(Map<String, Object> attriubtes) {
		this.attriubtes = attriubtes;
	}
	
	@Override
	public String getProviderId() {
		
		return (String) attriubtes.get("id");
	}
	
	@Override
	public String getProvider() {
		return (String) attriubtes.get("facebook");
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
