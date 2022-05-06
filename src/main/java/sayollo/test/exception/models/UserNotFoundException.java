package sayollo.test.exception.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 20359820478070L;

	public UserNotFoundException(String id) {
		super("User with id " + id + " not found");
	}

}
