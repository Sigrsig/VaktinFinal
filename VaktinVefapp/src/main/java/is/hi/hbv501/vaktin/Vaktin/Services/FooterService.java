package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;

import java.time.LocalDate;
import java.util.Optional;

public interface FooterService {

    Footer save(Footer footer);
    void delete(Footer footer);
    Footer findByDate(LocalDate date);

    boolean validate(Footer footer);
}
