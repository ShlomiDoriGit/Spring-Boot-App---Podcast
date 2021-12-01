package iob;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping(
		path = "/hello",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello() {
		return new MessageBoundary("Hello World",
				true,
				new Date(),
				1L,
				1.23,
				StatusEnum.INFO);
	}
	
	@RequestMapping(
			path = "/hellos",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary[] hellos() {
		return new MessageBoundary[]{
			new MessageBoundary("Hello World",
				true,
				new Date(),
				1L,
				1.23,
				StatusEnum.INFO),
			
			new MessageBoundary("Hello REST API Demo",
					false,
					new Date(),
					2L,
					1.1,
					StatusEnum.SUCCESS),
		
		};
	}
	
	@RequestMapping(
			path = "/hello/{name}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello(@PathVariable("name") String name) {
		return new MessageBoundary(
			"Hello " + name,
			false,
			new Date(),
			-1L,
			2.5,
			StatusEnum.SUCCESS); 
	}
}
