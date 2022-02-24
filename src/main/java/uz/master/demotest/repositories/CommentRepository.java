package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
