package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.comment.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByDeletedFalse();

    Comment findByIdAndDeletedFalse(Long id);

    @Transactional
    @Modifying
    @Query(value = "Update Comment t SET t.deleted = true WHERE t.id=:id")
    void delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "Update Comment t SET t.text =:text WHERE t.id=:id")
    void update(@Param("id") Long id, @Param("text") String text);
}
