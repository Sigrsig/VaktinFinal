package is.hi.hbv501.vaktin.Vaktin.Repositories;


import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/***
 * Repository for Footer
 */
public interface FooterRepository extends JpaRepository<Footer, Long> {

    Footer save(Footer footer);
    void delete(Footer footer);
    Footer findByDate(LocalDate date);
}
