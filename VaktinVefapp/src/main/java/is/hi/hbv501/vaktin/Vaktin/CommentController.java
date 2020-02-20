package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
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

/***
 * Controller for Comment routes
 * Currently only one route '/addcomment' which is a POST request for adding
 * a new comment in '/edit'
 *
 * Needs an instance of CommentService and WorkstationService for rendering
 */
@Controller
public class CommentController {

    private CommentService commentService;
    private WorkstationService workstationService;
    private EmployeeService employeeService;
    private FooterService footerService;
    private HomeController homeController;


    @Autowired
    public CommentController(CommentService commentService, WorkstationService workstationService, EmployeeService employeeService, FooterService footerService, HomeController homeController) {
        this.commentService = commentService;
        this.workstationService = workstationService;
        this.employeeService = employeeService;
        this.footerService = footerService;
        this.homeController = homeController;
    }

    @RequestMapping(value = "removecomment/{id}", method = RequestMethod.GET)
    public String removeComment(@PathVariable("id") long id, Model model) {
        Comment comment = commentService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid workstation ID"));
        commentService.delete(comment);

        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        model.addAttribute("footerValues", footerService.findByDate(LocalDate.now()));
        return "Velkominn";
    }

    /***
     * Route fyrir '/addcomment'
     * Adds comment and redirects to front page
     * @param comment Comment
     * @param workstation Workstation
     * @param result BindingResult
     * @param model Model
     * @return
     */
    @RequestMapping(value = "addcomment", method = RequestMethod.POST)
    public String addComment(@Valid Comment comment, Workstation workstation, BindingResult result, Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "Velkominn";
        }
        commentService.save(comment);
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        return "Velkominn";

    }
}
