package pl.bluemedia.funds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Please, provide allowed strategy type")
public class UnknownStrategyTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}
