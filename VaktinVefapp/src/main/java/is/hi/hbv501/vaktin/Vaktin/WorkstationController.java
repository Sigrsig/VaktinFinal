package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

/***
 * Controller for Workstation
 *
 * Only has one route at the moment for '/addworkstation'. Adds a workstation in '/edit' and
 * redirects to front page
 *
 * Needs WorkstationService and CommentService for rendering
 */
@Controller
public class WorkstationController {

    private WorkstationService workstationService;
    private CommentService commentService;
    private EmployeeService employeeService;
    private FooterService footerService;
    private HomeController homeController;


    @Autowired
    public WorkstationController(WorkstationService workstationService, CommentService commentService, EmployeeService employeeService, FooterService footerService, HomeController homeController) {
        this.workstationService = workstationService;
        this.commentService = commentService;
        this.employeeService = employeeService;
        this.footerService = footerService;
        this.homeController = homeController;
    }

    @RequestMapping("workstations")
    public String Workstation(Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("footerValues", footerService.findByDate(LocalDate.now()));
        model.addAttribute("employees", employeeService.findAllTodayAndTomorrow());
        return "Workstations";
    }


    @RequestMapping(value = "/clearworkstation/{id}", method = RequestMethod.GET)
    public String ClearFromWorkstation(@PathVariable("id") long id, Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        Employee tempEmp = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        tempEmp.setWorkstation(null);
        employeeService.save(tempEmp);

        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        return "Velkominn";
    }

    @RequestMapping(value = "addtoworkstation/{eid}/{wid}", method = RequestMethod.GET)
    public String AddToWorkstation(@PathVariable("eid") long eid, @PathVariable("wid") long wid, Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        Employee tempEmp = employeeService.findById(eid).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        Workstation tempWorkstation = workstationService.findById(wid).orElseThrow(() -> new IllegalArgumentException("Invalid workstation ID"));
        tempEmp.setWorkstation(tempWorkstation);
        employeeService.save(tempEmp);

        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employees", employeeService.findAllTodayAndTomorrow());

        return "Workstations";
    }


    @RequestMapping(value = "addworkstation", method = RequestMethod.POST)
    public String AddWorkstation(@Valid Workstation workstation, Comment comment, BindingResult result, Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "Velkominn";
        }

        //workstation.setShift(1);
        workstationService.save(workstation);
        /*
        String tempName = workstation.getName();
        workstation = new Workstation(tempName);
        workstation.setDate(LocalDate.of(2019, 12, 24));
        workstation.setShift(2);
        workstationService.save(workstation);
        workstation = new Workstation(tempName);
        workstation.setDate(LocalDate.of(2019, 12, 24));
        workstation.setShift(3);
        workstationService.save(workstation);
         */

        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        return "Velkominn";
    }




}
