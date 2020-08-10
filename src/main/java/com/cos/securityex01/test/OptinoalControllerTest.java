package com.cos.securityex01.test;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@RestController
public class OptinoalControllerTest {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/test/user/{id}")
	public User 옵셔널_유저찾기(@PathVariable int id) {

// 		첫번째 방법
//		Optional<User> userOptional = userRepository.findById(id);  // JPA가 들고있는 함수
//		User user;
//		if(userOptional.isPresent()) {
//			user = userOptional.get();
//			
//		} else {
//			user = new User();
//		}
		
		
//		두번째 방법
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//
//			@Override
//			public User get() {
//
//				return User.builder().id(10).username("디폴트이름").email("디폴트@naver.com").build();
//			}
//		});

		User user = userRepository.findById(id)
				.orElseThrow(new Supplier<NullPointerException>() {
					@Override
					public NullPointerException get() {
					
						return new NullPointerException("널 포인터 오류");
					}
				});
		return user;
	}
}
