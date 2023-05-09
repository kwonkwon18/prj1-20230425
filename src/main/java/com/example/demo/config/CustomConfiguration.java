package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableMethodSecurity
public class CustomConfiguration {

	@Value("${aws.accessKeyId}")
	private String accessKey;

	@Value("${aws.secretAccessKey}")
	private String secretKey;

	@Value("${aws.s3.bucketUrl}")
	private String bucketUrl;

	@Autowired
	private ServletContext application;

	// 빈이 만들어지자마자 바로 실행해라
	@PostConstruct
	public void init() {
		application.setAttribute("bucketUrl", bucketUrl);
	}

	@Bean
	public S3Client s3client() {

		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
		AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

		S3Client s3client = S3Client.builder()
				.credentialsProvider(provider)
				.region(Region.AP_NORTHEAST_2)
				.build();

		return s3client;

	}

	// 암호를 인코딩 할 때 사용하는 것 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 암호 필터할 때 사용하는 것 
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		
		// csrf 보안을 사용하지 않겠다는 것
		http.csrf().disable();
		
		// 로그인 페이지 설정
//		http.formLogin(Customizer.withDefaults()); // 기본 로그인 화면
		// 해당 페이지를 로그인 페이지로 쓰겠습니다. 
		// 이거 표시 안하면 그냥 처음부터 로그인하라고 뜸
		// 여기서 포스트를 받아서 리다이렉트함 
		http.formLogin().loginPage("/member/login"); // 페이지를 명시해서 사용
		

		
		// 로그아웃 페이지 설정
		http.logout().logoutUrl("/member/logout").logoutSuccessUrl("/");
		
//		// 로그인 한 사람만 글을 작성할 수 있게 하는 것
//		// 로그인 없이 접속 => 로그인 화면으로 리다이렉트 
//		http.authorizeHttpRequests().requestMatchers("/add").authenticated();
//		
//		// 로그인 하지 않은 사람만 들어올 수 있게 하려면
//		http.authorizeHttpRequests().requestMatchers("/member/signup").anonymous();
//		
//		// 그 외 다른 곳은 아무나 올 수 있게 하는 것
//		// /** 모든 경로와 그 하위 경로까지 지정
//		http.authorizeHttpRequests().requestMatchers("/**").permitAll();
		
////		http.authorizeHttpRequests().requestMatchers("/add").authenticated(); 이거와 같은 일
//		http.authorizeHttpRequests().requestMatchers("/add")
//									.access(new WebExpressionAuthorizationManager("isAuthenticated()")); // 로그인 되었다면을 가정한 식
//		
//		http.authorizeHttpRequests().requestMatchers("/member/signup")
//									.access(new WebExpressionAuthorizationManager("isAnonymous()"));
//		
//		http.authorizeHttpRequests().requestMatchers("/**")
//									.access(new WebExpressionAuthorizationManager("permitAll"));
		
		
		
		return http.build();
	}
	

}
