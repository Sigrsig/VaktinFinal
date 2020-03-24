package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.LastModified;
import is.hi.hbv501.vaktin.Vaktin.Services.LastModifiedService;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.LastModifiedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @RequestMapping("lastmodified")
    public ResponseEntity<LastModifiedResponse> lastModified() {
        LastModified lastModified = lastModifiedService.findById(1);

        LocalDateTime date = lastModified.getDate();

        return new ResponseEntity<>(new LastModifiedResponse(date), HttpStatus.OK);

    }
}
