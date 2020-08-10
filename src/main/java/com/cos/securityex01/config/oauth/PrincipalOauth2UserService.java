package com.cos.securityex01.config.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.oauth.provider.FacebookUserInfo;
import com.cos.securityex01.config.oauth.provider.GoogleUserInfo;
import com.cos.securityex01.config.oauth.provider.NaverUserInfo;
import com.cos.securityex01.config.oauth.provider.OAuth2UserInfo;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

// OAuth2 로그인할 때의 서비스.
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	// loaduser해야됨, 데이터 타입이 OAuth2User면 세션 두개 관리해야됨 데이터타입 두개라서
	// userRequest는 code를 받아서 access Token을 응답받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원프로필
		//oAuth2User 정보 리턴을 principalDetails로 리턴해준다
		// 1번: PrincipalDetails에 OAuth2User 정보를 집어 넣어준다.
		// 2번: 데이터타입 PrincipalDetails를 리턴한다.
		System.out.println("oAuth2User: " + oAuth2User); // 토큰을 통해 응답받은 회원정보.
		System.out.println("userRequest token : " + userRequest.getAccessToken().getTokenValue());
		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		//보면 토큰 이미 들어가 있다는걸 알 수 있다. 따라서 OAuth2UserRequest에 이미 여러 처리를 거쳐서 토큰을 받았다.
		
		return processOAuth2User(userRequest, oAuth2User);
	}
	//userRequest 회원정보받기전 access token 가지고있음, 그리고 oAuth2User에 회원정보 담김?
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		// Atrribute를 파싱해서 공통객체로 묶는다. 관리가 편함
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
			System.out.println("구글 로그인 요청");
			
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
			System.out.println("페이스북 로그인 요청");
			
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}
		
		
		else {
			System.out.println("우리는 구글과 페이스북, 네이버만ㅁ 지원해요");
			
			
			
		}
		
		
		
		
//		System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
//		System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
		Optional<User> userOptional =
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		
		User user;
		
		if(userOptional.isPresent()) {
			user = userOptional.get();
			System.out.println("if 문의 user 타나" + user);
			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
			System.out.println("save 함수 실행?");
		} else {
			
			
			user=  User.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId()) 
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
				userRepository.save(user);
				System.out.println("save 함수실행" + userRepository.save(user));
			
		}
		
	
	
		
		// 일반적으로 로그인할 때 유저 정보 User
		// 1. OAuth2로 로그인할 때 유저 정보 attributes <- 이거 구성해야함.
		// 2. DB에 이사람 있는지 확인
		
		// 있으면? -> 유저정보 업데이트 (구글에서 데이터 변경 일어날수 있으니까)
		
		
		// 없으면 ->  Insert해야함
		
		
// return PrincipalDetails(); 
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
	
	
}
