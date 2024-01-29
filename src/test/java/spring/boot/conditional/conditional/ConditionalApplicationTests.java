package spring.boot.conditional.conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

    @Container
	private static final GenericContainer<?> myAppDev = new GenericContainer<>("devapp")
			.withExposedPorts(8080);

    @Container
	private static final GenericContainer<?> myAppProd = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

//	@BeforeAll
//	public static void setUp() {
//		myAppDev.start();
//		myAppProd.start();
//	}

	@Test
	void contextLoadsDev() {
		ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:"
				+ myAppDev.getMappedPort(8080) + "/profile", String.class);
		String devExcepted = forEntityDev.getBody();
		Assertions.assertEquals(devExcepted, "Current profile is dev");
	}

	@Test
	void contextLoadsProd() {
		ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:"
				+ myAppProd.getMappedPort(8081) + "/profile", String.class);
		String prodExcepted = forEntityProd.getBody();
		Assertions.assertEquals(prodExcepted, "Current profile is production");
	}

}
