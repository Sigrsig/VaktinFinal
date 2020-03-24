package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.*;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import is.hi.hbv501.vaktin.Vaktin.Services.LastModifiedService;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.SetFooterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@RestController
public class FooterController {

    FooterService footerService;
    HomeController homeController;
    EmployeeService employeeService;
    LastModifiedService lastModifiedService;

    @Autowired
    public FooterController(FooterService footerService, HomeController homeController, EmployeeService employeeService, LastModifiedService lastModifiedService) {
        this.footerService = footerService;
        this.homeController = homeController;
        this.employeeService = employeeService;
        this.lastModifiedService = lastModifiedService;
    }

    @RequestMapping(value = "/setfooter", method = RequestMethod.POST)
    public ResponseEntity<SetFooterResponse> setFooter(@Valid @RequestBody Footer footer, BindingResult result) {

        LastModified tmpLastModified = lastModifiedService.findById(1);
        tmpLastModified.setDate(LocalDateTime.now());

        /*
        if (result.hasErrors()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Símanúmer ekki rétt slegið inn");
            model.addAttribute("footerErrors", errors);
            return homeController.EditPage(model, null, null, null, null);
        }
        */

        //System.out.println("footer.getHeadDoctorNumber() " + footer.getHeadDoctorNumber());

        boolean correctNumbers = footerService.validate(footer);
        ArrayList<String> errors = new ArrayList<>();
        if (!correctNumbers) {
            errors.add("Röng símanúmer");
            return new ResponseEntity<>(new SetFooterResponse("Invalid number", errors, footer), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new SetFooterResponse(footer), HttpStatus.OK);
    }

}
