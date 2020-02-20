package is.hi.hbv501.vaktin.Vaktin;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@Controller
public class EmployeeController {

    private EmployeeService employeeService;
    private CommentService commentService;
    private WorkstationService workstationService;
    private HomeController homeController;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CommentService commentService, WorkstationService workstationService, HomeController homeController) {
        this.employeeService = employeeService;
        this.commentService = commentService;
        this.workstationService = workstationService;
        this.homeController = homeController;
    }

    /***
     * Runs with the path /addemployee
     * Adds an Employee entity and renders Edit.html
     *
     * @param employee Employee
     * @param workstation Workstation
     * @param comment Comment
     * @param result BindingResult
     * @param model Model
     * @return Edit.html
     */
    @RequestMapping(value = "/addemployee", method = RequestMethod.GET)
    public String addEmployee(@Valid Employee employee, Workstation workstation, Comment comment, Footer footer, BindingResult result, Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        /***
         * Ef villur í formi
         */
        if (result.hasErrors()) {
            return homeController.EditPage(model, null, null, null, null, session);
        }

        /***
         * Kannar hvort að tímar séu á réttu formi og birtir villu
         */
        boolean correctDate = employeeService.validateDate(employee.getDateString());
        boolean boolTimeFrom = employeeService.validateTimeFrom(employee.gettFromString());
        boolean boolTimeTo = employeeService.validateTimeTo(employee.gettToString());
        boolean boolName = employeeService.validateName(employee.getName());
        System.out.println("gildið á boolName " + boolName);
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
            model.addAttribute("errors", errors);
            return homeController.EditPage(model, null, null, null, null, session);
        }

        /***
         * Breytir inntaki notanda úr streng í LocalDateTime
         */
        employeeService.parseToLocalDateTimeWithDate(employee);

        return homeController.EditPage(model, null, null, null, null, session);


    }

}
