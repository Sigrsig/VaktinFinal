package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;

import java.util.List;
import java.util.Optional;

/***
 * Service interface for Comment
 */
public interface CommentService {

    Comment save(Comment comment);
    void delete(Comment comment);
    List<Comment> findAll();
    Optional<Comment> findById(Long id);
    Comment findByString(String comment);


}
