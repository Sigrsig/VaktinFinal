package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class FooterController {

    FooterService footerService;
    HomeController homeController;
    EmployeeService employeeService;

    @Autowired
    public FooterController(FooterService footerService, HomeController homeController, EmployeeService employeeService) {
        this.footerService = footerService;
        this.homeController = homeController;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/setfooter", method = RequestMethod.POST)
    public String setFooter(@ModelAttribute Footer footer, BindingResult result, Model model, Employee employee, Comment comment, Workstation workstation, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        /*
        if (result.hasErrors()) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Símanúmer ekki rétt slegið inn");
            model.addAttribute("footerErrors", errors);
            return homeController.EditPage(model, null, null, null, null);
        }
        */

        System.out.println("footer.getHeadDoctorNumber() " + footer.getHeadDoctorNumber());

        boolean correctNumbers = footerService.validate(footer);
        ArrayList<String> errors = new ArrayList<>();
        if (!correctNumbers) {
            errors.add("Röng símanúmer");
            model.addAttribute("footerErrors", errors);
            return homeController.EditPage(model, null, null, null, null, session);
        }


        return homeController.Home(model, session);
    }

}
