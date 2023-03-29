package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로는 /auth/** 허용  ==> 인증이 필요없는 곳에 /auth/ 경로를 붙힘 
// 그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
    
	// 회원가입폼
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		// /WEB-INF/views/user/joinForm.jsp
		return "user/joinForm";
	}

	// 로그인폼
	@GetMapping("/loginForm")
	public String loginForm() {
		// /WEB-INF/views/user/loginForm.jsp
		return "user/loginForm";
	}
	
	// 카카오 로그인
	@GetMapping("auth/kakao/callback")
	public String kakaoCallback(String code) {
		
		// Post방식으로 key=value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate();
		
		// HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터임을 알림
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id","b353b3e01d0a3b17503c48af3fb63cf0");
		params.add("redirect_uri", "http://localhost:8070/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeaders와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, 
				kakaoTokenRequest, 
				String.class
		);
		
//		 https://kauth.kakao.com/oauth/token
//		 grant_type = authorization_code
//		 client_id = b353b3e01d0a3b17503c48af3fb63cf0
//		 redirect_uri = http://localhost:8070/auth/kakao/callback
//		 code = code=5xWMaqzkWYhtQhxFii3GkaH7Qh1wQUaVqzfEJjyPYSA87DaWnQB9P2TLegrRY-6ZfcVafgo9c-sAAAGHIJPgNw(동적임)

// response.getBody()		
//   {
//		  "access_token": "uCToPBYMXo4hFVIGDyKmsu5u5NoRXGZL2vgcXnAECj1z6wAAAYciKsob",
//		  "token_type": "bearer",
//		  "refresh_token": "xfe_T_c8PUO7x_CNSRi9AxCCA_Nyee22b1-EzMSoCj1z6wAAAYciKsoZ",
//		  "expires_in": 21599,
//		  "scope": "account_email profile_nickname",
//		  "refresh_token_expires_in": 5183999
//		}
		
		// Gson, Json Simple, ObjectMapper
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}			

		System.out.println("카카오 Access token : "+ oAuthToken.getAccess_token());
		// "카카오 로그인 토큰 요청 완료 - 토큰요청에 대한 응답 : "+response.getBody();
		
		// Post방식으로 key=value 데이터를 요청(카카오쪽으로)
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터임을 알림
		
		// HttpHeaders와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		// Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST, 
				kakaoProfileRequest, 
				String.class
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(kakaoProfile);
//		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
//		System.out.println("카카오 이메일 : "+kakaoProfile.kakao_account.getEmail());
		
		// User 오브젝트 : username, password, email, role
		String kakaoServerUserName = kakaoProfile.kakao_account.getEmail()+"_"+kakaoProfile.getId(); // username
		String kakaoEmail = kakaoProfile.kakao_account.getEmail(); // email
		
		System.out.println("카카오로그인 UserName : "+kakaoServerUserName);
		
		User kakaoUser = User.builder()
				.username(kakaoServerUserName)
				.password(cosKey)
				.email(kakaoEmail)
				.oauth("kakao")
				.build();
		// System.out.println("blogUser"+kakaoUser);
		
		// 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.findByUsername(kakaoUser.getUsername());
		if(originUser.getUsername()==null) {
			// 회원 등록
			userService.save(kakaoUser);
			//System.out.println("회원가입 완료");
		}
		
		System.out.println("----------------------------");
		// 세션값 변경 (로그인 처리)
        Authentication authentication = 
        		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
	// 회원정보
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}	
}
