package share.sh4re.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import share.sh4re.domain.Code;
import share.sh4re.domain.User.Roles;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {
}
