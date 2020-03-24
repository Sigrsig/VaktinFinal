package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;
import is.hi.hbv501.vaktin.Vaktin.Services.LastModifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LastModifiedController {

    private LastModifiedService lastModifiedService;

    @Autowired
    public LastModifiedController(LastModifiedService lastModifiedService) {
        this.lastModifiedService = lastModifiedService;
        init();
    }

    private void init() {
        LastModified lastModified = new LastModified();
        lastModifiedService.save(lastModified);
    }
}
