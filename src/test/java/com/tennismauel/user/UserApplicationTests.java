package com.tennismauel.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennismauel.user.repository.UserRepository;
import com.tennismauel.user.web.request.RegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@Tag("integration")
//webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT 이거 안 하면 에러난다. 기본값이 Mock이기 때문에 WebTestClient를 사용할 수 없다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApplicationTests {
	@Autowired
	WebTestClient webTestClient;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepository;

	@BeforeEach
	public void setup(){
		userRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void registerUserApiCreated() throws Exception{
		String email="foo@bar";
		String provider="naver";
		String role="ROLE_GUEST";

		RegistrationDto registrationDto = RegistrationDto.builder()
				.email(email)
				.provider(provider)
				.role(role).build();
		String body = objectMapper.writeValueAsString(registrationDto);

		webTestClient.post()
				.uri("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(body))
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void registerUserApiBadRequest() throws Exception{
		String email="foobar";
		String provider="naver";
		String role="ROLE_GUEST";

		RegistrationDto registrationDto = RegistrationDto.builder()
				.email(email)
				.provider(provider)
				.role(role).build();
		String body = objectMapper.writeValueAsString(registrationDto);

		webTestClient.post()
				.uri("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(body))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody().jsonPath("$.message").isEqualTo("validation failed");
	}

	@Test
	public void registerUserFailedConflict() throws Exception{
		String email="foo@bar";
		String provider="naver";
		String role="ROLE_GUEST";

		RegistrationDto registrationDto = RegistrationDto.builder()
				.email(email)
				.provider(provider)
				.role(role).build();
		String body = objectMapper.writeValueAsString(registrationDto);

		webTestClient.post()
				.uri("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(body))
				.exchange()
				.expectStatus().isCreated();

		webTestClient.post()
				.uri("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(body))
				.exchange()
				.expectStatus().is4xxClientError();
	}
}
