package share.sh4re.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import share.sh4re.domain.Code;
import share.sh4re.domain.Like;
import share.sh4re.domain.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
  Optional<Like> findByCodeAndUser(Code code, User user);
}
