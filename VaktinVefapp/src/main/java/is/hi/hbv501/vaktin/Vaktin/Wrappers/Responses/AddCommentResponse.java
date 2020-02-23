package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;

import java.util.List;

public class AddCommentResponse extends GenericResponse {

    Comment comment;

    public AddCommentResponse(Comment comment) {
        this.comment = comment;
    }

    public AddCommentResponse(String message, List<?> errors, Comment comment) {
        super(message, errors);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
