package share.sh4re.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import share.sh4re.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
