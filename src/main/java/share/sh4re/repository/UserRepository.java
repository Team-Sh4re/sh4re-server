package share.sh4re.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import share.sh4re.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User save(User user);
  Optional<User> findById(Long id);
  Optional<User> findByName(String name);
}
