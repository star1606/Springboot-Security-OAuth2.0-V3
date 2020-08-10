# 스프링 시큐리티 OAuth2.0 V2

- 페이스북, 구글 로그인 및 기본 시큐리티 연동

### 스프링 시큐리티 기본 V1 참고

- https://github.com/star1606/Springboot-Security-V1


### 스프링 시큐리티 Oauth2.0 로그인 구성 V2 참고

- https://github.com/star1606/Springboot-Security-OAuth2.0-V2


### JPA method names 참고

![blog](https://postfiles.pstatic.net/MjAyMDA4MDRfMTU1/MDAxNTk2NTA2ODAyMTgx.Qoff6FQ1RJyGw83meuDXT5J5e-Ac1WwSJMH2wf1l1Swg.KinVePXqdUOeyDYYRp4aguwTsxF0OBQB64LNUYTJRRgg.PNG.getinthere/Screenshot_26.png?type=w773)

### application.yml 설정

```yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true



spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/security?serverTimezone=Asia/Seoul
    username: cos
    password: cos1234
    
  mvc:
    view:
      prefix: /templates/
      suffix: .mustache
      
      
  jpa:
    hibernate:
      ddl-auto: update #create, update, none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
    

  security:
    oauth2:
      client:
        registration:
          google: # /oauth2/authorization/google 이 주소를 동작하게 한다.
            client-id: 223189027751-4k904kk9sr38fafuq97q4o5l5ocfs10.apps.googleusercontent.com
            client-secret: Rl8E1Missz4iJ1ZKV62TQ8w
            scope:
            - email
            - profile 
            
          facebook:
            client-id: 71578932581080
            client-secret: 4b5b6a742153118aa06f4a06017f774        
            scope:
            - email
            - public_profile
            
            
          # 네이버는 OAuth2.0 공식 지원대상이 아니라서 provider 설정이 필요하다.
          # 요청주소도 다르고, 응답 데이터도 다르기 때문이다.
          naver:
            client-id: 
            client-secret: 
            scope:
            - name
            - email
            - profile_image
            client-name: Naver # 클라이언트 네임은 구글 페이스북도 대문자로 시작하더라.
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/naver

        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response # 회원정보를 json의 response 키값으로 리턴해줌.
```