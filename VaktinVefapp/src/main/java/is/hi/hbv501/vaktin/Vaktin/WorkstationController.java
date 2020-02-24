package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.AddWorkstationResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.GenericResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.HomeActivityResponse;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.WorkstationActivityResponse;
import org.apache.coyote.Response;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
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

/***
 * Controller for Workstation
 *
 * Only has one route at the moment for '/addworkstation'. Adds a workstation in '/edit' and
 * redirects to front page
 *
 * Needs WorkstationService and CommentService for rendering
 */
@RestController
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
    public ResponseEntity<WorkstationActivityResponse> Workstation(Model model, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            return new ResponseEntity<>(new WorkstationActivityResponse("Need to be logged in", null, null, null, null), HttpStatus.FORBIDDEN);
        }

        WorkstationActivityResponse constrRes = new WorkstationActivityResponse(
                workstationService.findAll(),
                employeeService.findAllTodayAndTomorrow(),
                footerService.findByDate(LocalDate.now())
        );

        return new ResponseEntity<>(constrRes, HttpStatus.OK);
    }


    @RequestMapping(value = "/clearworkstation/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> ClearFromWorkstation(@PathVariable("id") long id, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        Employee tempEmp = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        tempEmp.setWorkstation(null);
        employeeService.save(tempEmp);

        return ResponseEntity.noContent().build();
    }

    // Er betra að nota isPresent() eða virkar þetta?
    @RequestMapping(value = "addtoworkstation/{eid}/{wid}", method = RequestMethod.GET)
    public ResponseEntity<?> AddToWorkstation(@PathVariable("eid") long eid, @PathVariable("wid") long wid, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        Employee tempEmp = employeeService.findById(eid).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        Workstation tempWorkstation = workstationService.findById(wid).orElseThrow(() -> new IllegalArgumentException("Invalid workstation ID"));
        tempEmp.setWorkstation(tempWorkstation);
        employeeService.save(tempEmp);

        if (tempEmp == null || tempWorkstation == null) {
            return new ResponseEntity<GenericResponse>(new GenericResponse("Invalid employee or workstation", null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Employee>(tempEmp, HttpStatus.OK);
    }


    @RequestMapping(value = "addworkstation", method = RequestMethod.POST)
    public ResponseEntity<AddWorkstationResponse> AddWorkstation(@Valid @RequestBody Workstation workstation, Comment comment, BindingResult result, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(new AddWorkstationResponse("Invalid workstation", result.getFieldErrors(), workstation), HttpStatus.BAD_REQUEST);
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


        return new ResponseEntity<>(new AddWorkstationResponse(workstation), HttpStatus.OK);
    }




}