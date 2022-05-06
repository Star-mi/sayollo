package sayollo.test.exception.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FilterTypeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1234562568881478070L;

    public FilterTypeException(String filterType) {
        super("Filter type '" + filterType + "' uncorrected");
    }
}
