package is.hi.hbv501.vaktin.Vaktin.Repositories;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastModifiedRepository extends JpaRepository<LastModified, Long> {

    LastModified save(LastModified lastModified);
    LastModified findById(long id);
}
