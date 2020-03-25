package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;

public interface LastModifiedService {

    LastModified save(LastModified lastModified);
    LastModified findById(long id);
}
