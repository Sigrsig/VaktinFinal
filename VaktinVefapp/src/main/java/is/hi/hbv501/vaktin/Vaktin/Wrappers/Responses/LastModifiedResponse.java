package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;

import java.time.LocalDateTime;
import java.util.List;

public class LastModifiedResponse extends GenericResponse {

    LocalDateTime date;

    public LastModifiedResponse(LocalDateTime date) {
        this.date = date;
    }

    public LastModifiedResponse(String message, List<?> errors, LocalDateTime date) {
        super(message, errors);
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
