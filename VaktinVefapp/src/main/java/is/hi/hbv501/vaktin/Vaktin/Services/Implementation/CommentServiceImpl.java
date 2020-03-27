package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Repositories.CommentRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * Service implementation for Comment
 * Implements CommentService
 * Calls all methods of CommentRepository
 */
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.repository = commentRepository;
    }


    @Override
    public Comment save(Comment comment) {
        return this.repository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        this.repository.delete(comment);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }
}
