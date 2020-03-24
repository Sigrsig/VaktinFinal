package is.hi.hbv501.vaktin.Vaktin.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class LastModified {

    @Id
    private long id;

    private LocalDateTime date;

    public LastModified() {
        this.date = LocalDateTime.now();
        this.id = 1;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
