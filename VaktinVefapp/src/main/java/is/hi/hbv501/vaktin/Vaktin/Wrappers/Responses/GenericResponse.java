package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import java.util.List;

public class GenericResponse {

    private String message;
    private List<?> errors;

    GenericResponse() { }

    public GenericResponse(String message, List<?> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getErrors() {
        return errors;
    }

    public void setErrors(List<?> errors) {
        this.errors = errors;
    }
}
