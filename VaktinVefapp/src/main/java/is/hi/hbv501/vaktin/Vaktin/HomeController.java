package is.hi.hbv501.vaktin.Vaktin;


import is.hi.hbv501.vaktin.Vaktin.Entities.*;
import is.hi.hbv501.vaktin.Vaktin.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/***
 * Controller for front page
 * Has no Service or Repository
 * Renders front page '/'. Looks up all entities of type Workstation and Comment
 * and adds to Model for rendering
 * Renders a test for login page '/login'
 * Renders edit page at '/edit'
 * Makes three instances of employee and one workstation /makedata
 *
 * Needs an instance of CommentService and WorkstationService for rendering
 *
 */
@Controller
public class HomeController {

    private CommentService commentService;
    private WorkstationService workstationService;
    private EmployeeService employeeService;
    private UserService userService;
    private FooterService footerService;

    @Autowired
    public HomeController(CommentService commentService, WorkstationService workstationService,
                          EmployeeService employeeService, UserService userService, FooterService footerService) {
        this.commentService = commentService;
        this.workstationService = workstationService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.footerService = footerService;
    }

   /* @RequestMapping("/")
    public String Home(Model model, Comment comment) {
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("footerValues", footerService.findByDate(LocalDate.now()));
        return "redirect:/login";
    }

    @RequestMapping("adalsida")
    public String Keyra() { return "Velkominn"; }*/

   public boolean loggedIn(HttpSession session) {
        User sessionUser = (User)session.getAttribute("loggedInUser");

        if (sessionUser != null) {
            return true;
        }

        return false;
   }

    @RequestMapping("/")
    public String Home(Model model, HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        model.addAttribute("footerValues", footerService.findByDate(LocalDate.now()));
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("today", LocalDate.now());
        return "Velkominn";
    }

    /*
    @RequestMapping("/")
    public String Keyra() { return "redirect:/login"; }
    */

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(@Valid User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "LoginPage";
        }

        User exists = userService.login(user);
        if (exists != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        }
        return "LoginPage";
    }



    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String LoginPage(User user) { return "LoginPage"; }


    @RequestMapping("edit")
    public String EditPage(Model model, Employee employee, Workstation workstation, Comment comment, Footer footer, HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("footerValues", footerService.findByDate(LocalDate.now()));
        return "Edit";
    }

    @RequestMapping("clearworkstations")
    public String ClearWorkstations(Model model, HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        List<Employee> tempEmp = employeeService.findAll();
        for (int i = 0; i < tempEmp.size(); i++) {
            tempEmp.get(i).setWorkstation(null);
            employeeService.save(tempEmp.get(i));
        }
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        return Home(model, session);

    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String DeleteWorkstation(@PathVariable("id") long id, Model model, HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        Workstation workstation = workstationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid workstation ID"));
        List<Employee> tempEmp = employeeService.findAll();
        for (int i = 0; i < tempEmp.size(); i++) {
            if (tempEmp.get(i).getWorkstation() == workstation) {
                tempEmp.get(i).setWorkstation(null);
            }
            employeeService.save(tempEmp.get(i));
        }
        workstationService.delete(workstation);
        model.addAttribute("workstations", workstationService.findAll());
        return Home(model, session);
    }

    private Workstation makeWorkstation(String name) {
        Workstation findWorkstation = workstationService.findByName(name);
        if (findWorkstation == null) {
            Workstation tempWorkstation = new Workstation(name);
            workstationService.save(tempWorkstation);
            return tempWorkstation;
        }
        else {
            return findWorkstation;
        }

    }

    private Employee makeEmployee(String name, LocalDateTime tFrom, LocalDateTime tTo, String role) {
        Employee findEmployee = employeeService.findByName(name);

        if (findEmployee != null) {
            return findEmployee;
        }
        else {
            Employee tempEmployee = new Employee(name, tFrom, tTo, role);
            employeeService.save(tempEmployee);
            return tempEmployee;

        }
    }

    /***
     * Make dummy data for employees and workstations
     * Data is made for surrounding days
     * @param model
     * @return
     */
    @RequestMapping("makedata")
    public String makeData(Model model, HttpSession session){
        User tempUser = new User("user", "123");
        userService.save(tempUser);



        LocalDate now = LocalDate.now().minusDays(1);
        for (int i = 0; i < 5; i++) {
            LocalDate nowPlusI = now.plusDays(i);
            LocalDate nowPlusIPlusOne = now.plusDays(i+1);
            LocalDateTime iTime = LocalDateTime.of(nowPlusI.getYear(), nowPlusI.getMonthValue(), nowPlusI.getDayOfMonth(), 16,0);
            LocalDateTime iTime2 = LocalDateTime.of(nowPlusI.getYear(), nowPlusI.getMonthValue(), nowPlusI.getDayOfMonth(), 0,0);
            LocalDateTime iTime3 = LocalDateTime.of(nowPlusI.getYear(), nowPlusI.getMonthValue(), nowPlusI.getDayOfMonth(), 8,0);
            LocalDateTime jTime = LocalDateTime.of(nowPlusIPlusOne.getYear(), nowPlusIPlusOne.getMonthValue(), nowPlusIPlusOne.getDayOfMonth(), 0,0);
            LocalDateTime jTime2 = LocalDateTime.of(nowPlusI.getYear(), nowPlusI.getMonthValue(), nowPlusI.getDayOfMonth(), 8,0);
            LocalDateTime jTime3 = LocalDateTime.of(nowPlusI.getYear(), nowPlusI.getMonthValue(), nowPlusI.getDayOfMonth(), 16,0);
            LocalDateTime[][] timeShifts = {
                    {iTime, jTime},
                    {iTime2, jTime2},
                    {iTime3, jTime3}
            };
            for (int j = 0; j < 20; j++) {
                Random r = new Random();
                int rnd = r.nextInt(3);
                makeEmployee("Employee nr. " + ((i*100)+j+1), timeShifts[rnd][0], timeShifts[rnd][1], "H");
            }
        }

        Workstation tempWork = makeWorkstation("Svæfing");
        Workstation tempWork2 = makeWorkstation("Tjillhornið");
        Workstation tempWork3 = makeWorkstation("Sjálfsalinn");
        Workstation tempWork4 = makeWorkstation("Sígóportið");

        List<Employee> tempEmp = this.employeeService.findAll();

        /*Ætlum ekki að hafa neinn á vinnustöð
        for (int i = 0; i < tempEmp.size(); i++) {
            if (i%4 == 1) {
                tempEmp.get(i).setWorkstation(tempWork);
            }
            else if (i%4 == 2) {
                tempEmp.get(i).setWorkstation(tempWork2);
            }
            else if (i%4 == 3) {
                tempEmp.get(i).setWorkstation(tempWork3);
            }
            else {
                tempEmp.get(i).setWorkstation(tempWork4);
            }

        }
        */


        String[] names = {"Loftur Unassigned", "Sigríður Unassigned", "Tobba Unassigned", "Manu Unassigned"};
        LocalDateTime tFrom = LocalDateTime.of(2019, 11, 20, 16, 0);
        LocalDateTime tTo = LocalDateTime.of(2019, 11, 20, 0, 0);
        for (int i = 0; i < names.length; i++) {
            makeEmployee(names[i], tFrom, tTo, "H");
        }

        makeEmployee("Einhver sem mætir fyrr", LocalDateTime.of(2019,11,20,15,0), LocalDateTime.of(2019, 11, 21, 0, 0), "S");
        Employee newEmployee = employeeService.findByName("Einhver sem mætir fyrr");
        newEmployee.setWorkstation(tempWork);
        employeeService.save(newEmployee);

        makeEmployee("Einhver sem mætir seinna", LocalDateTime.of(2019,11,20,17,0), LocalDateTime.of(2019, 11, 21, 0, 0), "S");
        Employee newEmployee2 = employeeService.findByName("Einhver sem mætir fyrr");
        newEmployee2.setWorkstation(tempWork);
        employeeService.save(newEmployee2);

        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        return Home(model, session);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpPOST(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "signup";
        }
        User exists = userService.findByUName(user.uName);
        if (exists == null){
            userService.save(user);
        }
        else {
            model.addAttribute("error", "Frátekinn notandi");
            return "signup";
        }

        //hér mætti vera með villuskilaboð og senda aftur á signup ef notandi ef notandi er þegar til
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        return "Velkominn";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model, HttpSession session){
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    /*
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user){
        return "LoginPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@Valid User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            return "LoginPage";
        }
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        User exists = userService.login(user);
        if(exists != null){
            session.setAttribute("LoggedInUser", user);
            return "redirect:/";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model){
        model.addAttribute("workstations", workstationService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("employeesToday", employeeService.findAllSortedToday());
        model.addAttribute("employeesTomorrow", employeeService.findAllSortedTomorrow());
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null){
            model.addAttribute("loggedinuser", sessionUser);
            return "loggedInUser";
        }
        return "redirect:/";
    }

     */
}
