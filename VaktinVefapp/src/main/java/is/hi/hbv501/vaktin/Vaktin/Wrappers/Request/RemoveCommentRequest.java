package is.hi.hbv501.vaktin.Vaktin.Wrappers.Request;

import java.io.Serializable;

public class RemoveCommentRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String comment;

    public RemoveCommentRequest() { }

    public RemoveCommentRequest(String comment) {
        this.comment = comment;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
