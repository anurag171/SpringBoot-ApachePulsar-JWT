package com.anurag.iot.data.api;

import com.anurag.iot.data.api.security.JWTUtil;
import com.anurag.iot.data.api.handler.MetricsHandler;
import com.anurag.iot.data.api.security.PBKDF2Encoder;
import com.anurag.iot.data.api.security.UserService;
import com.anurag.iot.data.api.model.AuthRequest;
import com.anurag.iot.data.api.model.AuthResponse;
import com.anurag.iot.data.api.repository.MetricsDao;
import com.anurag.iot.data.api.router.MetricsRouter;
import com.anurag.iot.data.api.util.WebTestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.anurag.iot.data.api.constants.MetricsAPIConstants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Slf4j
@MockBeans({ })
class IotMetricsApplicationTests {

	@Autowired
	WebTestClientUtil webTestClientUtil;

	WebTestClient testUser;
	WebTestClient testAdmin;
	WebTestClient testInvalid;

	@Autowired
	UserService service;

	@Autowired
	PBKDF2Encoder pbkdf2Encoder;

	@Autowired
	JWTUtil jwtUtil;

	@Autowired
	MetricsHandler metricsHandler;

	@Autowired
	MetricsRouter metricsRouter;

	@Autowired
	MetricsDao metricsDao;


	@Test
	void contextLoads() {


	}

	String sensor = null;
	String startdate = null;
	String enddate = null;
	String sensorGrp = null;

	@BeforeEach
	public void setUp(){
		sensor = "HRM";
		startdate = "2021-01-01 00:00:00";
		enddate = "2021-12-31 23:59:59";
		sensorGrp = "HealthDevice";
	}

	@Test
	void testLogin()  {

		AuthRequest user = new AuthRequest();
		user.setUsername("user");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		testAdmin = webTestClientUtil.authenticateClient("admin","user");
		testInvalid = webTestClientUtil.authenticateClient("ramesh","ramesh");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
				});
		log.info("Token received [{}]" ,token.get());
		Objects.requireNonNull(token.get());
	}

	@Test
	void testGetMaxReadingForSensorBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("user");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MAX_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).queryParam("sensor",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetMaxReadingForSensorGroupBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HealthDevice";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MAX_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).queryParam("sensorgrp",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetMinReadingForSensorBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HRM";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MIN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).queryParam("sensor",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetMinReadingForSensorGroupBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HealthDevice";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MIN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).queryParam("sensorgrp",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetAverageReadingForSensorBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HRM";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(AVERAGE_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).queryParam("sensor",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetAverageReadingForSensorGroupBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HealthDevice";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(AVERAGE_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).queryParam("sensorgrp",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetMedianReadingForSensorBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HRM";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MEDIAN_END_POINT_BY_SENSORS_BETWEEN_DATE_TIME).queryParam("sensor",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void testGetMedianReadingForSensorGroupBetweenTimeSuccessfullConnection() throws URISyntaxException, UnsupportedEncodingException {

		AuthRequest user = new AuthRequest();
		user.setUsername("admin");
		user.setPassword("user");

		testUser = webTestClientUtil.authenticateClient("user","user");
		AtomicReference<String> token =  new AtomicReference<>();
		testUser.post().uri("/login").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.
						fromObject(user)).exchange().expectBody(AuthResponse.class).consumeWith(authResponseEntityExchangeResult -> {
			token.set(authResponseEntityExchangeResult.getResponseBody().getToken());
		});
		log.info("Token received [{}]" ,token.get());

		String sensor = "HealthDevice";
		String startdate = "2021-01-01 00:00:00";
		String enddate = "2021-12-31 23:59:59";

		log.info("Received Token [{}]",token);
		testUser.get().uri(uriBuilder1 -> {
			URI uri = uriBuilder1.path(MEDIAN_END_POINT_BY_SENSORS_GROUP_BETWEEN_DATE_TIME).queryParam("sensorgrp",sensor).queryParam("startdate",startdate).queryParam("enddate",enddate).build();
			log.info("Called Url [{}]",uri.toString());
			return uri;
		}).headers(h->h.setBearerAuth(token.get())).exchange().expectStatus().is2xxSuccessful();
	}


}
