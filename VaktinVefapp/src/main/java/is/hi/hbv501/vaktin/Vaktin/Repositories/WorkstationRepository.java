package is.hi.hbv501.vaktin.Vaktin.Repositories;

import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Repository for Workstation
 *
 */
public interface WorkstationRepository extends JpaRepository<Workstation, Long> {

    Workstation save(Workstation workstation);
    void delete(Workstation workstation);
    List<Workstation> findAll();
    Optional<Workstation> findById(Long id);
    Workstation findByWorkstationName(String name);
    

}
