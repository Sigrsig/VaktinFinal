package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;

import java.util.List;
import java.util.Optional;

/***
 * Service interface for Workstation
 * Same methods as in WorkstationRepository
 */
public interface WorkstationService {

    Workstation save(Workstation workstation);
    void delete(Workstation workstation);
    List<Workstation> findAll();
    Optional<Workstation> findById(Long id);
    Workstation findByName(String name);

    //void clearAllWorkstations();

}
