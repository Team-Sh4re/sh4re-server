package share.sh4re.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import share.sh4re.domain.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
