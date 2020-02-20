package is.hi.hbv501.vaktin.Vaktin;


import is.hi.hbv501.vaktin.Vaktin.Entities.*;
import is.hi.hbv501.vaktin.Vaktin.Services.*;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.GenericResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.HomeActivityResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.LoginResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.MakeDataResponse;
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
@RestController
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


   public boolean loggedIn(HttpSession session) {
        User sessionUser = (User)session.getAttribute("loggedInUser");

        if (sessionUser != null) {
            return true;
        }

        return false;
   }

   // Þarf að vera HttpSession session ??? fyrir login
    @RequestMapping("/")
    public ResponseEntity<HomeActivityResponse> Home(HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            return new ResponseEntity<>(new HomeActivityResponse("Need to be logged in", null), HttpStatus.FORBIDDEN);
        }

        HomeActivityResponse constrRes = new HomeActivityResponse(
                commentService.findAll(),
                workstationService.findAll(),
                employeeService.findAll(),
                employeeService.findAllSortedToday(),
                footerService.findByDate(LocalDate.now()),
                LocalDate.now()
        );

        return new ResponseEntity<>(constrRes, HttpStatus.OK);
    }

    /*
    VEIT EKKI HVORT VIÐ ÞURFUM LOGOUT Á REST. KANNSKI ER HTTPSESSION LOKAÐ ANNARS STAÐAR
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    */


    // TIL HVERS ÞURFUM VIÐ RESULT.GETFIELDERRORS() ??
    // Á að stilla session hér??
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> loginPost(@Valid @RequestBody User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new LoginResponse("Invalid user", result.getFieldErrors(), user), HttpStatus.BAD_REQUEST);
        }

        User exists = userService.login(user);
        if (exists != null) {
            session.setAttribute("loggedInUser", user);
            return new ResponseEntity<>(new LoginResponse(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(new LoginResponse("Login unsuccessful", null, user), HttpStatus.BAD_REQUEST);

    }


    // Veit ekki með þetta noContent.build(). Er það 204 No content?
    @RequestMapping("clearworkstations")
    public ResponseEntity<?> ClearWorkstations(HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        List<Employee> tempEmp = employeeService.findAll();
        for (int i = 0; i < tempEmp.size(); i++) {
            tempEmp.get(i).setWorkstation(null);
            employeeService.save(tempEmp.get(i));
        }

        return ResponseEntity.noContent().build();

    }


    // Er throw new ResponseStatusException sambærilegt return response?
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> DeleteWorkstation(@PathVariable("id") long id, HttpSession session) {
        boolean isLoggedIn = loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        // Virkar þetta throw?
        Workstation workstation = workstationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid workstation ID"));

        // Gá hvort id fannst
        if (workstation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        List<Employee> tempEmp = employeeService.findAll();
        for (int i = 0; i < tempEmp.size(); i++) {
            if (tempEmp.get(i).getWorkstation() == workstation) {
                tempEmp.get(i).setWorkstation(null);
            }
            employeeService.save(tempEmp.get(i));
        }
        workstationService.delete(workstation);

        return ResponseEntity.noContent().build();
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
     * @return
     */
    @RequestMapping("makedata")
    public ResponseEntity<MakeDataResponse> makeData(){
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

        MakeDataResponse constrRes = new MakeDataResponse(null, null, workstationService.findAll(), employeeService.findAll());
        return new ResponseEntity<>(constrRes, HttpStatus.CREATED);
    }


    // Viljum við hafa signup eða eigum við bara að bæta nýjum notendum í sql skjali?
    /*
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
    */



}
