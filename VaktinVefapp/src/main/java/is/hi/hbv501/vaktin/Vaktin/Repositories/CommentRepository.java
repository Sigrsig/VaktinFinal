package is.hi.hbv501.vaktin.Vaktin.Repositories;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/***
 * Comment repository
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);
    void delete(Comment comment);
    List<Comment> findAll();
    Optional<Comment> findById(Long id);

}
