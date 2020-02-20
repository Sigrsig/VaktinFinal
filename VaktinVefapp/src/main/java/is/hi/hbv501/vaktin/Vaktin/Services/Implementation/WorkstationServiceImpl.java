package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Repositories.WorkstationRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * Service implementation for Workstation
 *
 */
@Service
public class WorkstationServiceImpl implements WorkstationService {

    WorkstationRepository workstationRepository;

    @Autowired
    public WorkstationServiceImpl(WorkstationRepository workstationRepository) {
        this.workstationRepository = workstationRepository;
    }

    @Override
    public Workstation save(Workstation workstation) {
        return workstationRepository.save(workstation);
    }

    @Override
    public void delete(Workstation workstation) {
        workstationRepository.delete(workstation);
    }

    @Override
    public List<Workstation> findAll() {
        return workstationRepository.findAll();
    }

    @Override
    public Optional<Workstation> findById(Long id) {
        return workstationRepository.findById(id);
    }

    @Override
    public Workstation findByName(String name) {
        return workstationRepository.findByWorkstationName(name);
    }


}
