package iob.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -6339057311570167930L;

	public ForbiddenRequestException() {
	}

	public ForbiddenRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenRequestException(String message) {
		super(message);
	}

	public ForbiddenRequestException(Throwable cause) {
		super(cause);
	}
}