

package is.hi.hbv501.vaktin.Vaktin;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import is.hi.hbv501.vaktin.Vaktin.Entities.*;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.LastModifiedService;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.AddEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * Controller for Employee
 * Has only one route for /addemployee
 */
@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private CommentService commentService;
    private WorkstationService workstationService;
    private HomeController homeController;
    private LastModifiedService lastModifiedService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CommentService commentService, WorkstationService workstationService, HomeController homeController, LastModifiedService lastModifiedService) {
        this.employeeService = employeeService;
        this.commentService = commentService;
        this.workstationService = workstationService;
        this.homeController = homeController;
        this.lastModifiedService = lastModifiedService;
    }

    /***
     * Runs with the path /addemployee
     * Adds an Employee entity and renders Edit.html
     *
     * @param employee Employee
     *
     * @param result BindingResult
     *
     * @return Edit.html
     */

    @RequestMapping(value = "/addemployee", method = RequestMethod.POST)
    public ResponseEntity<AddEmployeeResponse> addEmployee(@Valid @RequestBody Employee employee, BindingResult result) {

        LastModified tmpLastModified = lastModifiedService.findById(1);
        tmpLastModified.setDate(LocalDateTime.now());

        /***
         * Ef villur í formi
         */

        if (result.hasErrors()) {
            return new ResponseEntity<>(new AddEmployeeResponse("Invalid input", result.getFieldErrors(), employee), HttpStatus.BAD_REQUEST);
        }

        /***
         * Kannar hvort að tímar séu á réttu formi og birtir villu
         */

        boolean correctDate = true;
        boolean boolTimeFrom = true;
        boolean boolTimeTo = true;
        boolean boolName = true;
        //System.out.println("gildið á boolName " + boolName);
        ArrayList<String> errors = new ArrayList<>();
        if (!boolTimeFrom) {
           errors.add("Tími í upphafi vaktar vitlaus");

        }
        if (!boolTimeTo) {
            errors.add("Tími í lok vaktar vitlaus");

        }
        if (!boolName) {
            errors.add("Vinsamlegast skráðu nafn");
        }
        if (!correctDate) {
            errors.add("Dagsetning vitlaus");
        }
        if (!boolTimeFrom || !boolTimeTo || !boolName || !correctDate) {
            return new ResponseEntity<>(new AddEmployeeResponse("Invalid fields", errors, employee), HttpStatus.BAD_REQUEST);
        }

        if (employeeService.findByName(employee.getName()) == null) {
            employeeService.save(employee);
        }


        return new ResponseEntity<>(new AddEmployeeResponse(employee), HttpStatus.OK);


    }

}
