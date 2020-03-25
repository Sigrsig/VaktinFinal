package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;
import is.hi.hbv501.vaktin.Vaktin.Repositories.LastModifiedRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.LastModifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LastModifiedImpl implements LastModifiedService {

    private LastModifiedRepository lastModifiedRepository;

    @Autowired
    public LastModifiedImpl(LastModifiedRepository lastModifiedRepository) {
        this.lastModifiedRepository = lastModifiedRepository;
    }


    @Override
    public LastModified save(LastModified lastModified) {
        return lastModifiedRepository.save(lastModified);

    }

    @Override
    public LastModified findById(long id) {
        return lastModifiedRepository.findById(id);
    }

}
