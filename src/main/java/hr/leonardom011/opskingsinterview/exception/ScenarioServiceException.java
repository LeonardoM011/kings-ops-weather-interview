package hr.leonardom011.opskingsinterview.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScenarioServiceException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public ScenarioServiceException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
