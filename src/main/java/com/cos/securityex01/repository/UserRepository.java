package com.cos.securityex01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.securityex01.model.User;


// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// Jpa Naming 전략
	// 자동으로 SELECT * FROM user WHERE username = 1? (1: 첫번째 파라미터)
	User findByUsername(String username);
	
	// SELECT * FROM user WHERE username = 1? AND password = 2?
	User findByUsernameAndPassword(String username, String password);
	
//	@Query(value = "select * from user where email = ?1", nativeQuery =true)
	
	// SELECT * FROM user WHERE provider =?1 and providerId = ?2
	Optional<User> findByProviderAndProviderId(String provider, String providerId);
	
	
	
	
}
