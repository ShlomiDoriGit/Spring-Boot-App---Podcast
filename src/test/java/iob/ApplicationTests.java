package iob;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	private RestTemplate restTemplate;
	private String url;
	private int port;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port + "/iob/users";
	}
	
	@AfterEach
	public void teardown() {
		// this.restTemplate.delete(this.url);
	}
	
	@Test
	public void contextLoads() throws Exception{
		String st = "x";
		assertThat(st).isEqualTo("x");
	}
}
