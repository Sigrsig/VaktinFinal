package is.hi.hbv501.vaktin.Vaktin;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import is.hi.hbv501.vaktin.Vaktin.Services.FooterService;
import is.hi.hbv501.vaktin.Vaktin.Services.WorkstationService;
import is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses.AddCommentResponse;
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

/***
 * Controller for Comment routes
 * Currently only one route '/addcomment' which is a POST request for adding
 * a new comment in '/edit'
 *
 * Needs an instance of CommentService and WorkstationService for rendering
 */
@RestController
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

    // Aftur, virkar þetta throw í REST
    @RequestMapping(value = "removecomment/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> removeComment(@PathVariable("id") long id, Model model) {
        Comment comment = commentService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment not found");
        }

        commentService.delete(comment);

        return ResponseEntity.noContent().build();
    }

    /***
     * Route fyrir '/addcomment'
     * Adds comment and redirects to front page
     * @param comment Comment
     * 
     * @param result BindingResult
     *
     * @return
     */
    @RequestMapping(value = "addcomment", method = RequestMethod.POST)
    public ResponseEntity<AddCommentResponse> addComment(@Valid @RequestBody Comment comment, BindingResult result, HttpSession session) {
        boolean isLoggedIn = homeController.loggedIn(session);

        if (!isLoggedIn) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Need to be logged in");
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(new AddCommentResponse("Invalid comment", result.getFieldErrors(), comment), HttpStatus.BAD_REQUEST);
        }

        commentService.save(comment);

        return new ResponseEntity<>(new AddCommentResponse(comment), HttpStatus.OK);

    }
}
